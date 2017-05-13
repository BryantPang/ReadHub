package com.chihopang.readhub.model;

import java.util.List;

public class Topic {
  private long id;
  private String title;
  private String siteName;
  private String authorName;
  private String url;
  private String summary;
  private List<ArticlePage> newsArray;
  private String publishDate;

  @Override public String toString() {
    return "Topic{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", siteName='" + siteName + '\'' +
        ", authorName='" + authorName + '\'' +
        ", url='" + url + '\'' +
        ", summary='" + summary + '\'' +
        ", newsArray=" + newsArray +
        ", publishDate='" + publishDate + '\'' +
        '}';
  }

  public String getSiteName() {
    return siteName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getUrl() {
    return url;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getSummary() {
    return summary;
  }

  public List<ArticlePage> getNewsArray() {
    return newsArray;
  }

  public String getPublishDate() {
    return publishDate;
  }
}
