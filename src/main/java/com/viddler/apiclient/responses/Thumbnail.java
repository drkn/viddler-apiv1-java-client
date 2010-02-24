/**
 *
 */
package com.viddler.apiclient.responses;

/**
 * @author Maciej Dragan
 *
 */
public class Thumbnail implements ApiResponse {

  private static final long serialVersionUID = -9006081230898516133L;

  private String small;
  private String medium;
  private String snapshot;

  public String getSmall() {
    return small;
  }

  public void setSmall(String small) {
    this.small = small;
  }

  public String getMedium() {
    return medium;
  }

  public void setMedium(String medium) {
    this.medium = medium;
  }

  public String getSnapshot() {
    return snapshot;
  }

  public void setSnapshot(String snapshot) {
    this.snapshot = snapshot;
  }

}
