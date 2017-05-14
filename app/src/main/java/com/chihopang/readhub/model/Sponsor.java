package com.chihopang.readhub.model;

public class Sponsor {
  private String sponsor;
  private String slogan;
  private String pageUrl;
  private String imgUrl;

  @Override public String toString() {
    return "Sponsor{" +
        "sponsor='" + sponsor + '\'' +
        ", slogan='" + slogan + '\'' +
        ", pageUrl='" + pageUrl + '\'' +
        ", imgUrl='" + imgUrl + '\'' +
        '}';
  }

  public String getSponsor() {
    return sponsor;
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
}
