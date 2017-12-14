package com.chihopang.readhub.model;

import android.text.TextUtils;
import com.chihopang.readhub.util.TimeUtil;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

@Parcel public class Topic {
  String id;
  long order;
  String title;
  String siteName;
  String authorName;
  String url;
  String mobileUrl;
  String summary;
  ArrayList<Topic> newsArray;
  String publishDate;

  public Topic() {
  }

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
    if (!TextUtils.isEmpty(mobileUrl)) return mobileUrl;
    return url;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title.trim();
  }

  public String getSummary() {
    return summary.trim();
  }

  public List<Topic> getNewsArray() {
    return newsArray;
  }

  public String getFormatPublishDate() {
    return TimeUtil.countDown(publishDate);
  }

  public long getOrder() {
    return order;
  }
}
