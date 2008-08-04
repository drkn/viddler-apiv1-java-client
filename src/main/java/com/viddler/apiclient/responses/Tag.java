/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Global and Timed tags base class
 * 
 * @author Maciej Dragan
 * 
 */
public abstract class Tag implements ApiResponse {

  private static final long serialVersionUID = 725081736010461488L;
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
