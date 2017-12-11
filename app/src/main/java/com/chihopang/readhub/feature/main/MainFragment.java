package com.chihopang.readhub.feature.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.feature.developer.news.TechNewsFragment;
import com.chihopang.readhub.feature.more.MoreFragment;
import com.chihopang.readhub.feature.news.NewsFragment;
import com.chihopang.readhub.feature.topic.HotTopicFragment;
import java.lang.reflect.Field;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends SupportFragment {
  @BindView(R.id.bottom_navigation_view) BottomNavigationView mBottomNavigationView;
  @BindView(R.id.view_pager_main) ViewPager mViewPager;

  public static MainFragment newInstance() {
    MainFragment fragment = new MainFragment();
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initContent();
    initListener();
  }

  private void initContent() {
    mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
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
        }
        return null;
      }

      @Override public int getCount() {
        return 4;
      }
    });
    BottomNavigationMenuView menuView =
        (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
    try {
      Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
      shiftingMode.setAccessible(true);
      shiftingMode.setBoolean(menuView, false);
      shiftingMode.setAccessible(false);
      for (int i = 0; i < menuView.getChildCount(); i++) {
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
        itemView.setShiftingMode(false);
        itemView.setChecked(itemView.getItemData().isChecked());
      }
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void initListener() {
    mBottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int currentItem;
            switch (item.getItemId()) {
              case R.id.menu_item_hot_topic:
                currentItem = 0;
                break;
              case R.id.menu_item_news:
                currentItem = 1;
                break;
              case R.id.menu_item_tech_news:
                currentItem = 2;
                break;
              case R.id.menu_item_more:
                currentItem = 3;
                break;
              default:
                currentItem = 0;
            }
            mViewPager.setCurrentItem(currentItem);
            return true;
          }
        });
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
  }
}
