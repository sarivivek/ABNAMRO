/**
 * 
 */
package com.abnamro.codechallenge;

import java.util.LinkedHashMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Sari
 *
 */
@Component
@ConfigurationProperties(prefix = "data")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApplicationConfig {

  private LinkedHashMap<String, Indexes> filefields;

  private String rowlength;

  private String[] csvFields;

  private String outputFileName;

  private String delimeter;


  @Data
  @Builder
  public static class Indexes {

    private String startIndex;
    private String endIndex;
    private boolean sumRequired;
    private String actionRequired;



  }

}
