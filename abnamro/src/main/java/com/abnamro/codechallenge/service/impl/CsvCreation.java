package com.abnamro.codechallenge.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;
import com.abnamro.codechallenge.ApplicationConfig;
import com.abnamro.codechallenge.api.BussinessException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
@Builder
@RequiredArgsConstructor
public class CsvCreation {

  private final ApplicationConfig applicationConfig;

  public ByteArrayInputStream createCsv(Map<String, Integer> fullData) throws IOException {
    // TODO Auto-generated method stub

    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
        .withHeader(applicationConfig.getCsvFields());

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      fullData.entrySet().stream().forEach(v -> {
        List<String> stringList = Pattern.compile(applicationConfig.getDelimeter())
            .splitAsStream(v.getKey()).collect(Collectors.toList());
        log.debug("Inside  csvcreation " + stringList);
        stringList.add(v.getValue().toString());
        try {
          csvPrinter.printRecord(stringList);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          throw new BussinessException("fail to import data to CSV file: " + e.getMessage());
        }
      });
      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new BussinessException("fail to import data to CSV file: " + e.getMessage());
    }
  }
}
