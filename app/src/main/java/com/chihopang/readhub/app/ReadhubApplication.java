package com.chihopang.readhub.app;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class ReadhubApplication extends Application {
  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    CrashReport.initCrashReport(getApplicationContext(), Navigator.BUGGLY_APP_ID, false);
    Fragmentation.builder()
        .stackViewMode(Fragmentation.BUBBLE)
        .debug(BuildConfig.DEBUG)
        .handleException(new ExceptionHandler() {
          @Override public void onException(Exception e) {

          }
        }).install();
    mContext = getApplicationContext();
  }
}
