package com.chihopang.readhub.feature.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.chihopang.readhub.R;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    new Thread(new Runnable() {
      @Override public void run() {
        try {
          TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
      }
    }).start();
  }

  @Override public void onBackPressed() {

  }
}
