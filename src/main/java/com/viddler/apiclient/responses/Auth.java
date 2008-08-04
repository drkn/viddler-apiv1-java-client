/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Authorization response
 * 
 * @author Maciej Dragan
 * 
 */
public class Auth implements ApiResponse {

  private static final long serialVersionUID = 1264842454673169834L;

  private String sessionid;

  public String getSessionid() {
    return sessionid;
  }

  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }

}
