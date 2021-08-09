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


  private Map<String, Integer> fullData;
  private static final String ADDITION = "ADD";
  private static final String SUBSTRACTION = "SUB";
  private final ApplicationConfig applicationConfig;
  private final CsvCreation csvCreation;
  private Integer value;
  private StringBuffer key;



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
    log.debug("final value " + fullData);

    return new InputStreamResource(csvCreation.createCsv(fullData));
  }



  private void readData(String str) {
    value = 0;
    key = null;
    applicationConfig.getFilefields().entrySet().stream().forEach(e -> {
      String data = str.substring(Integer.parseInt(e.getValue().getStartIndex()),
          Integer.parseInt(e.getValue().getEndIndex())).trim().replace(" ", "");
      log.debug("Data getting parsed " + data);
      if (!e.getValue().isSumRequired()) {
        if (key == null || key.length() == 0) {
          key = new StringBuffer(data);
        } else {
          key = key.append(applicationConfig.getDelimeter()).append(data);
        }
      } else if (e.getValue().isSumRequired()
          && e.getValue().getActionRequired().equals(ADDITION)) {
        value = value + Integer.parseInt(data);
      } else if (e.getValue().isSumRequired()
          && e.getValue().getActionRequired().equals(SUBSTRACTION)) {
        value = value - Integer.parseInt(data);
      }
    });

    fullData.put(key.toString(), fullData.getOrDefault(key.toString(), 0) + value);

    log.debug("Data loaded till now " + fullData);
  }


}
