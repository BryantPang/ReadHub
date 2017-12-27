package com.chihopang.readhub.model;

import org.parceler.Parcel;

@Parcel
public class ArticlePage {
  long id;
  String url;
  String mobileUrl;
  String title;
  String siteName;
  String authorName;

  public ArticlePage() {
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
