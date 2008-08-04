package com.viddler.apiclient.responses;

public class ApiInfo implements ApiResponse {

  private static final long serialVersionUID = -8827651764953660613L;

  private String version;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return String.format(getClass().getName() + " [version=%s]", version);
  }

}
