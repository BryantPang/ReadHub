package com.chihopang.readhub.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.Bugly;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class ReadhubApplication extends Application {
  private static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    Bugly.init(getApplicationContext(), Constant.BUGGLY_APP_ID, false);
    Fragmentation.builder()
        .stackViewMode(Fragmentation.BUBBLE)
        .debug(BuildConfig.DEBUG)
        .handleException(new ExceptionHandler() {
          @Override public void onException(Exception e) {
            //TODO handle exception
          }
        }).install();
    mContext = getApplicationContext();
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  public static Context getContext() {
    return mContext;
  }
}
