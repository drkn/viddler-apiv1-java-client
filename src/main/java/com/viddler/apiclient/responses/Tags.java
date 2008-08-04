/**
 * 
 */
package com.viddler.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Tags holder
 * 
 * @author Maciej Dragan
 * 
 */
public class Tags implements ApiResponse {

  private List<GlobalTag> global = new ArrayList<GlobalTag>();
  private List<TimedTag> timed = new ArrayList<TimedTag>();

  /**
   * 
   */
  private static final long serialVersionUID = -2178824251954201802L;

  public Tags() {}

  public List<GlobalTag> getGlobal() {
    return global;
  }

  public List<TimedTag> getTimed() {
    return timed;
  }

  public void addTimed(TimedTag tag) {
    timed.add(tag);
  }

  public void addGlobal(GlobalTag tag) {
    global.add(tag);
  }

}
