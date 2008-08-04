/**
 * 
 */
package com.viddler.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

import com.viddler.apiclient.PermissionType;

/**
 * Permission response
 * 
 * @author Maciej Dragan
 * 
 */
public class Permission implements ApiResponse {

  private static final long serialVersionUID = 606299785256404879L;

  private String level;
  private String secreturl;
  private List<String> lists = new ArrayList<String>();
  private List<String> users = new ArrayList<String>();

  public PermissionType getType() {
    return PermissionType.forValue(level);
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getSecreturl() {
    return secreturl;
  }

  public void setSecreturl(String secreturl) {
    this.secreturl = secreturl;
  }

  public List<String> getLists() {
    return lists;
  }

  public void addLists(String list) {
    lists.add(list);
  }

  public List<String> getUsers() {
    return users;
  }

  public void addUsers(String user) {
    users.add(user);
  }

}
