package com.chihopang.readhub.feature.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseActivity;
import com.chihopang.readhub.feature.more.MoreFragment;
import com.chihopang.readhub.feature.news.NewsFragment;
import com.chihopang.readhub.feature.topic.HotTopicFragment;
import mehdi.sakout.dynamicbox.DynamicBox;

public class MainActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView mBottomNavigationView;
  @BindView(R.id.frame_main) FrameLayout mFrameContainer;

  private FragmentManager mFragmentManager = getSupportFragmentManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initContent();
    initListener();
  }

  private void initContent() {
    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);
    mBox = new DynamicBox(this, mFrameContainer);
    if (mFragmentManager.findFragmentById(R.id.frame_main) == null) {
      mFragmentManager.beginTransaction()
          .replace(R.id.frame_main, HotTopicFragment.newInstance(), HotTopicFragment.TAG)
          .commit();
    }
  }

  private void initListener() {
    mBottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.menu_item_hot_topic:
                mFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, HotTopicFragment.newInstance(), HotTopicFragment.TAG)
                    .commit();
                return true;
              case R.id.menu_item_news:
                mFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, NewsFragment.newInstance(), NewsFragment.TAG)
                    .commit();
                return true;
              case R.id.menu_item_more:
                mFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, MoreFragment.newInstance(), MoreFragment.TAG)
                    .commit();
                return true;
            }
            return false;
          }
        });
  }
}
