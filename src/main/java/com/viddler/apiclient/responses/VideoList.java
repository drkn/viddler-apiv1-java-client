/**
 * 
 */
package com.viddler.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Videos list holder
 * 
 * @author Maciej Dragan
 * 
 */
public class VideoList implements ApiResponse {

  private static final long serialVersionUID = 6893305431251496378L;

  private int total;
  private String sort;
  private String type;
  private List<Video> videos = new ArrayList<Video>();

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public void addVideos(Video video) {
    this.videos.add(video);
  }

  public void setVideos(List<Video> videos) {
    this.videos = videos;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
