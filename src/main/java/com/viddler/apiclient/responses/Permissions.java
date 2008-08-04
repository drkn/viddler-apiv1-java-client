/**
 * 
 */
package com.viddler.apiclient.responses;

/**
 * Permissions response holder
 * 
 * @author Maciej Dragan
 * 
 */
public class Permissions implements ApiResponse {

  private static final long serialVersionUID = -8766809182915907137L;

  private Permission view;
  private Permission embed;
  private Permission tagging;
  private Permission commenting;
  private Permission download;

  public Permission getView() {
    return view;
  }

  public void setView(Permission view) {
    this.view = view;
  }

  public Permission getEmbed() {
    return embed;
  }

  public void setEmbed(Permission embed) {
    this.embed = embed;
  }

  public Permission getTagging() {
    return tagging;
  }

  public void setTagging(Permission tagging) {
    this.tagging = tagging;
  }

  public Permission getCommenting() {
    return commenting;
  }

  public void setCommenting(Permission commenting) {
    this.commenting = commenting;
  }

  public Permission getDownload() {
    return download;
  }

  public void setDownload(Permission download) {
    this.download = download;
  }

}
