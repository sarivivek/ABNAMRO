/**
 * 
 */
package com.abnamro.codechallenge.api;

import java.io.IOException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abnamro.codechallenge.ApplicationConfig;
import com.abnamro.codechallenge.service.DailySummaryReportService;
import lombok.RequiredArgsConstructor;

/**
 * @author Sari Rest Api for the Daily summary Report
 *
 */
@RestController
@RequestMapping("/abnamro")
@RequiredArgsConstructor
public class DailySummaryReport {

  private final DailySummaryReportService summaryReportService;

  private final ApplicationConfig applicationConfig;

  /*
   * * RestAPI end point for the daily Summary report
   * 
   */

  @GetMapping(value = "/getDailyReport")
  public ResponseEntity<InputStreamResource> getDailySummary(
      @RequestParam(value = "filePath", required = false) String filePath) {

    try {
      InputStreamResource fileInputStream = summaryReportService.getDailySummary(filePath);

      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=" + applicationConfig.getOutputFileName());
      headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
      return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);

    } catch (IOException e) {
      throw new BussinessException("File Uploading failed");
    }
  }
}
