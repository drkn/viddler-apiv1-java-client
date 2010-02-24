/**
 *
 */
package com.viddler.apiclient;

/**
 * @author Maciej Dragan
 *
 */
public enum VideosListSort {

  UPLOADED_DESC, UPLOADED_ASC;

  public String value() {
    return name().toLowerCase().replaceAll("[_]", "-");
  }

}
