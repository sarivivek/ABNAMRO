package com.abnamro.codechallenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailySummaryReportServiceImplTest extends AbnamroApplicationTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ApplicationConfig applicationConfig;


  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;


  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }


  /**
   * Test method for
   * 
   */


  @Test
  public void testdailySummaryReport() throws Exception {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
        .withHeader(applicationConfig.getCsvFields());
    List<String> stringList = new ArrayList<>();
    InputStreamResource fileInputStream = null;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      csvPrinter.printRecord(stringList);
      fileInputStream = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    }

    log.info("Inside  testcase ", fileInputStream);

    this.mockMvc.perform(get("/abnamro/getDailyReport")).andExpect(status().isOk());

  }


  @Test
  public void testError() throws Exception {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
        .withHeader(applicationConfig.getCsvFields());
    List<String> stringList = new ArrayList<>();
    InputStreamResource fileInputStream = null;

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      csvPrinter.printRecord(stringList);
      fileInputStream = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
    }

    log.info("Inside  testcase ", fileInputStream);

    this.mockMvc.perform(get("/abnamro/getDailyRepo")).andExpect(status().is4xxClientError());

  }


}
