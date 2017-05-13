package com.chihopang.readhub.app;

import android.app.Application;
import android.content.Context;

public class ReadhubApplication extends Application {
  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
  }
}
