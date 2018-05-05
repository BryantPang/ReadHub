package com.chihopang.readhub.feature.more.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseSwipeBackFragment;

public class FavoritesFragment extends BaseSwipeBackFragment {
  public static FavoritesFragment newInstance() {
    FavoritesFragment fragment = new FavoritesFragment();
    return fragment;
  }

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tab_layout) TabLayout mTabLayout;
  @BindView(R.id.view_pager) ViewPager mViewPager;

  @Override public int getFragmentLayout() {
    return R.layout.fragment_favorites;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewPager.setAdapter(new FavoritesFragmentPagerAdapter(getChildFragmentManager()));
    mTabLayout.setupWithViewPager(mViewPager);
    //设置导航图标
    mToolbar.setNavigationIcon(R.drawable.ic_back);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pop();
      }
    });
  }

  private static class FavoritesFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = new String[] {"话题", "资讯"};

    public FavoritesFragmentPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return FavoritesListFragment.newInstance(FavoritesListFragment.TYPE_TOPIC);
        case 1:
          return FavoritesListFragment.newInstance(FavoritesListFragment.TYPE_NEWS);
      }
      return null;
    }

    @Override public int getCount() {
      return TITLE.length;
    }

    @Override public CharSequence getPageTitle(int position) {
      return TITLE[position];
    }
  }
}
