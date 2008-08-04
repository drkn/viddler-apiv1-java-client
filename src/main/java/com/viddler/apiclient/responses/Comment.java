/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Comment response
 * 
 * @author Maciej Dragan
 * 
 */
public class Comment implements ApiResponse {

  private static final long serialVersionUID = 7540497580078082253L;

  private int id;
  private String type;
  private String author;
  private int rating;
  private String text;
  private long addTime;
  private long time;
  private int parent;
  private VideoComment video;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public long getAddTime() {
    return addTime;
  }

  public void setAddTime(long addTime) {
    this.addTime = addTime;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  public VideoComment getVideo() {
    return video;
  }

  public void setVideo(VideoComment video) {
    this.video = video;
  }

}
