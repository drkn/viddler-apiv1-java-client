/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Timed Tag response
 * 
 * @author Maciej Dragan
 * 
 */
public class TimedTag extends Tag {

  private static final long serialVersionUID = -2551644830646961515L;

  private int offset;

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

}
