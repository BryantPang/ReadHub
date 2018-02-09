package com.chihopang.readhub.model;

import android.text.TextUtils;
import org.parceler.Parcel;

@Parcel public class Sponsor {
  String slogan;
  String pageUrl;
  String imgUrl;

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

  public boolean isCompleted() {
    return !TextUtils.isEmpty(slogan) && !TextUtils.isEmpty(pageUrl);
  }
}
