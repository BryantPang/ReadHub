package com.chihopang.readhub.feature.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.chihopang.readhub.feature.developer.news.TechNewsFragment;
import com.chihopang.readhub.feature.more.MoreFragment;
import com.chihopang.readhub.feature.news.NewsFragment;
import com.chihopang.readhub.feature.topic.HotTopicFragment;
import java.lang.ref.WeakReference;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
  private static int ITEM_COUNT = 4;
  private WeakReference<Fragment> mFragmentWeakReference;

  public MainFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return HotTopicFragment.newInstance();
      case 1:
        return NewsFragment.newInstance();
      case 2:
        return TechNewsFragment.newInstance();
      case 3:
        return MoreFragment.newInstance();
      default:
        return null;
    }
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    mFragmentWeakReference = new WeakReference<>((Fragment) object);
  }

  @Override public int getCount() {
    return ITEM_COUNT;
  }

  public Fragment getCurrentFragment() {
    return mFragmentWeakReference.get();
  }
}
