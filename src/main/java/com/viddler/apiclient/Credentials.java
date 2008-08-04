/**
 * 
 */
package com.viddler.apiclient;

/**
 * User credentials holder. This class is used by ViddlerApiClient for reauthentication mechanizm
 * 
 * @author Maciej Dragan
 * 
 */
public class Credentials {

  private String username;
  private String password;
  private String sessionid;

  /** Default constructor */
  public Credentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSessionid() {
    return sessionid;
  }

  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }

  @Override
  public String toString() {
    return String.format(Credentials.class.getName() + " [username=%s, sessionid=%s]", username, sessionid);
  }

}
