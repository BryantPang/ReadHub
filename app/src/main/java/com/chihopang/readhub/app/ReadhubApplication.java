package com.chihopang.readhub.app;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;

public class ReadhubApplication extends Application {
  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    mContext = getApplicationContext();
  }
}
