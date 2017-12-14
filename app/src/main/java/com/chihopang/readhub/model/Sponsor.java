package com.chihopang.readhub.model;

public class Sponsor {
  private String slogan;
  private String pageUrl;
  private String imgUrl;

  @Override public String toString() {
    return "Sponsor{" +
        "slogan='" + slogan + '\'' +
        ", pageUrl='" + pageUrl + '\'' +
        ", imgUrl='" + imgUrl + '\'' +
        '}';
  }

  public String getSlogan() {
    return slogan;
  }

  public String getPageUrl() {
    return pageUrl;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
