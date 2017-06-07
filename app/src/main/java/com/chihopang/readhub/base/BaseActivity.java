package com.chihopang.readhub.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;

public abstract class BaseActivity extends AppCompatActivity {
  public DynamicBox mBox;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }
}
