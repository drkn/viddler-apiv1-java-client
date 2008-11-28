/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * @author Maciej Dragan
 * 
 */
public class Upload implements ApiResponse {

  private static final long serialVersionUID = -8399739437332955029L;

  private String endpoint;

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

}
