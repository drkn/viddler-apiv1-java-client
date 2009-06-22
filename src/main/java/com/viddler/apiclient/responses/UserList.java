/**
 * 
 */
package com.viddler.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Users list holder
 * 
 * @author Maciej Dragan
 * 
 */
public class UserList implements ApiResponse {

  private static final long serialVersionUID = 6813305431251496378L;

  private String type;
  private List<User> users = new ArrayList<User>();

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

}
