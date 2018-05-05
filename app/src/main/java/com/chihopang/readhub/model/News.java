package com.chihopang.readhub.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.chihopang.readhub.util.TimeUtil;
import org.parceler.Parcel;

@Entity @Parcel public class News {
  @PrimaryKey @NonNull public String id;
  public String title;
  public String summary;
  public String summaryAuto;
  public String url;
  public String mobileUrl;
  public String siteName;
  public String siteSlug;
  public String language;
  public String authorName;
  public String publishDate;

  //以下为 Topic.newsArray 多余内容
  public int groupId;
  public int duplicateId;

  public News() {
  }

  @Ignore public String getUrl() {
    if (!TextUtils.isEmpty(mobileUrl)) return mobileUrl;
    return url;
  }

  @Ignore public String getTitle() {
    return TextUtils.isEmpty(title) ? null : title.trim();
  }

  @Ignore public String getSummary() {
    return TextUtils.isEmpty(summary) ? null : summary.trim();
  }

  @Ignore public String getPublishDateCountDown() {
    return TimeUtil.countDown(publishDate);
  }

  @Ignore public String getFormatPublishDate() {
    return TimeUtil.getFormatDate(publishDate);
  }

  @Ignore public String getLastCursor() {
    return String.valueOf(TimeUtil.getTimeStamp(publishDate));
  }
}