/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * User response
 * 
 * @author Maciej Dragan
 * 
 */
public class User implements ApiResponse {

  private static final long serialVersionUID = -1315260984059901321L;

  private String username;
  private String firstName;
  private String lastName;
  private String aboutMe;
  private String avatar;
  private int age;
  private int videoUploadCount;
  private int videoWatchCount;
  private String homepage;
  private String gender;
  private String company;
  private String city;
  private int friendCount;
  private int favoriteVideoCount;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getVideoUploadCount() {
    return videoUploadCount;
  }

  public void setVideoUploadCount(int videoUploadCount) {
    this.videoUploadCount = videoUploadCount;
  }

  public int getVideoWatchCount() {
    return videoWatchCount;
  }

  public void setVideoWatchCount(int videoWatchCount) {
    this.videoWatchCount = videoWatchCount;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getFriendCount() {
    return friendCount;
  }

  public void setFriendCount(int friendCount) {
    this.friendCount = friendCount;
  }

  public int getFavoriteVideoCount() {
    return favoriteVideoCount;
  }

  public void setFavoriteVideoCount(int favoriteVideoCount) {
    this.favoriteVideoCount = favoriteVideoCount;
  }

  @Override
  public String toString() {
    return String.format(getClass().getSimpleName() + " [username=%s]", username);
  }

}
