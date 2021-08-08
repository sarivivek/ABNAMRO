package com.abnamro.codechallenge.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;
import com.abnamro.codechallenge.ApplicationConfig;
import com.abnamro.codechallenge.api.BussinessException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@Builder
@RequiredArgsConstructor
public class CsvCreation {

  private final ApplicationConfig applicationConfig;

  public ByteArrayInputStream createCsv(Map<Integer, Map<String, String>> fullData)
      throws IOException {
    // TODO Auto-generated method stub

    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
        .withHeader(applicationConfig.getCsvFields());

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      fullData.entrySet().stream().forEach(v -> {
        List<String> data = new ArrayList<>();
        v.getValue().entrySet().forEach(k -> {
          data.add(k.getValue());
        });
        try {
          csvPrinter.printRecord(data);
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
