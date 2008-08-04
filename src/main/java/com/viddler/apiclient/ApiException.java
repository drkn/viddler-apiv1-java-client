/**
 * 
 */
package com.viddler.apiclient;

import com.viddler.apiclient.responses.Error;

/**
 * Exception thrown when API returns error response.
 * 
 * @author Maciej Dragan
 * 
 */
public class ApiException extends Exception {

  private static final long serialVersionUID = -6540976162212767333L;

  private Error error;

  /**
   * 
   * @param error
   */
  public ApiException(Error error) {
    this.error = error;
  }

  /**
   * Get an error returned by Viddler API
   * 
   * @return Error response
   */
  public Error getError() {
    return error;
  }

  @Override
  public String toString() {
    return String.format(getClass().getSimpleName() + " %s", error);
  }

}
