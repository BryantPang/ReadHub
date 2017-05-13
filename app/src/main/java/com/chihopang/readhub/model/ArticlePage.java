package com.chihopang.readhub.model;

public class ArticlePage {
  private long id;
  private String url;
  private String mobileUrl;
  private String title;
  private String siteName;
  private String authorName;

  @Override public String toString() {
    return "ArticlePage{" +
        "id=" + id +
        ", url='" + url + '\'' +
        ", mobileUrl='" + mobileUrl + '\'' +
        ", title='" + title + '\'' +
        ", siteName='" + siteName + '\'' +
        ", authorName='" + authorName + '\'' +
        '}';
  }

  public long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getMobileUrl() {
    return mobileUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSiteName() {
    return siteName;
  }

  public String getAuthorName() {
    return authorName;
  }
}
