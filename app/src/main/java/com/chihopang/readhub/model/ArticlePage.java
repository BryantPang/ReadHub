package com.chihopang.readhub.model;

public class ArticlePage {
  private long id;
  private String url;
  private String mobileUrl;

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

  public void setId(long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMobileUrl() {
    return mobileUrl;
  }

  public void setMobileUrl(String mobileUrl) {
    this.mobileUrl = mobileUrl;
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

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  private String title;
  private String siteName;
  private String authorName;
}
