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

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<ArticlePage> getNewsArray() {
    return newsArray;
  }

  public void setNewsArray(List<ArticlePage> newsArray) {
    this.newsArray = newsArray;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }
}
