package com.chihopang.readhub.app;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class ReadhubApplication extends Application {
  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
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
