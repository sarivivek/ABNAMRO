/**
 * 
 */
package com.abnamro.codechallenge.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sari
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class BussinessException extends RuntimeException {

  /**
   * 
   */
  public BussinessException() {

  }

  public BussinessException(String message) {
    super(message);
  }

  public BussinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BussinessException(Throwable cause) {
    super(cause);
  }

  public BussinessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
