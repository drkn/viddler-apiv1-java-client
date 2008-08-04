/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * API error response wrapper
 * 
 * @author Maciej Dragan
 * 
 */
public class Error implements ApiResponse {

  private static final long serialVersionUID = 6006487565434047792L;

  private int code;
  private String description;
  private String details;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return String.format(getClass().getSimpleName() + " [code=%d, details=%s, description=%s]", code, details,
        description);
  }

}
