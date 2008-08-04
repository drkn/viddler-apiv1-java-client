/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Video comment response
 * 
 * @author Maciej Dragan
 * 
 */
public class VideoComment implements ApiResponse {

  private static final long serialVersionUID = -194819953473562945L;

  private String source;
  private String thumbnail;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

}
