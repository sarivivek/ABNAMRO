/**
 * 
 */
package com.abnamro.codechallenge.service;

import java.io.IOException;
import org.springframework.core.io.InputStreamResource;

/**
 * @author Sari
 *
 */
public interface DailySummaryReportService {

  InputStreamResource getDailySummary(final String filePath) throws IOException;


}
