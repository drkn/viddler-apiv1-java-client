/**
 * 
 */
package com.viddler.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Comments list holder
 * 
 * @author Maciej Dragan
 * 
 */
public class Comments implements ApiResponse {

  private static final long serialVersionUID = -8499483519595880677L;

  private int total;

  private List<Comment> list = new ArrayList<Comment>();

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<Comment> getList() {
    return list;
  }

  public void setList(List<Comment> comments) {
    this.list = comments;
  }

  public void addList(Comment comment) {
    list.add(comment);
  }

}
