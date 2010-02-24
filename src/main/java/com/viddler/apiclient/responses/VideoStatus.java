/**
 * 
 */
package com.viddler.apiclient.responses;


/**
 * Video status response
 * @author Maciej Dragan
 *
 */
public class VideoStatus implements ApiResponse {

  private static final long serialVersionUID = -3395556289450730699L;

  private String id;
  private int failed;
  private int statuscode;
  private String statusmsg;

  public VideoStatus() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getFailed() {
    return failed;
  }

  public void setFailed(int failed) {
    this.failed = failed;
  }

  public int getStatuscode() {
    return statuscode;
  }

  public void setStatuscode(int statuscode) {
    this.statuscode = statuscode;
  }

  public String getStatusmsg() {
    return statusmsg;
  }

  public void setStatusmsg(String statusmsg) {
    this.statusmsg = statusmsg;
  }
  
  


}
