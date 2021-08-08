/**
 * 
 */
package com.abnamro.codechallenge.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.abnamro.codechallenge.ApplicationConfig;
import com.abnamro.codechallenge.api.BussinessException;
import com.abnamro.codechallenge.service.DailySummaryReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sari
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailySummaryReportServiceImpl implements DailySummaryReportService {


  @Value("classpath:Input.txt")
  private Resource resource;


  private Map<Integer, Map<String, String>> fullData;
  private static final String TOTAL_KEY = "Sum_Total";
  private static final String ADDITION = "ADD";
  private static final String SUBSTRACTION = "SUB";
  private final ApplicationConfig applicationConfig;
  private final CsvCreation csvCreation;



  private Integer count = 0;
  private Integer sum;
  private boolean isPresent;



  @Override
  public InputStreamResource getDailySummary(final String filePath) throws IOException {

    log.debug("Inside  getDailySummary ");
    fullData = new LinkedHashMap<>();

    try {
      new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8")).lines()
          .forEach(this::readData);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      log.info("File loading failed ");
      throw new BussinessException("File loading failed");
    }

    return new InputStreamResource(csvCreation.createCsv(fullData));
  }



  private void readData(String str) {
    count++;
    sum = 0;
    Map<String, String> inner1 = new LinkedHashMap<>();
    applicationConfig.getFilefields().entrySet().stream().forEach(e -> {

      String data = str.substring(Integer.parseInt(e.getValue().getStartIndex()),
          Integer.parseInt(e.getValue().getEndIndex())).trim().replace(" ", "");
      log.debug("Data getting parsed " + data);
      if (!e.getValue().isSumRequired()) {
        inner1.put(e.getKey(), data);
      } else if (e.getValue().isSumRequired()
          && e.getValue().getActionRequired().equals(ADDITION)) {
        sum = sum + Integer.parseInt(data);
      } else if (e.getValue().isSumRequired()
          && e.getValue().getActionRequired().equals(SUBSTRACTION)) {
        sum = sum - Integer.parseInt(data);
      }
    });
    inner1.put(TOTAL_KEY, sum.toString());
    calculateSum(inner1, count);
    log.debug("Data loaded till now " + fullData);
  }


  private void calculateSum(Map<String, String> data, Integer count2) {
    isPresent = false;
    fullData.entrySet().stream().forEach(v -> {
      if (v.getValue().equals(data)) {
        Integer value = Integer.parseInt(v.getValue().get(TOTAL_KEY)) + sum;
        v.getValue().replace(TOTAL_KEY, v.getValue().get(TOTAL_KEY), value.toString());
        log.debug("Data getting removed " + data);
        isPresent = true;
      }
    });

    if (!isPresent) {
      fullData.put(count2, data);

    }
  }
}
