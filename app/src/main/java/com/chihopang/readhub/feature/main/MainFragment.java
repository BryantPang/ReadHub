package com.chihopang.readhub.feature.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseFragment;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.feature.blockchain.BCNewsFragment;
import com.chihopang.readhub.feature.developer.news.TechNewsFragment;
import com.chihopang.readhub.feature.more.MoreFragment;
import com.chihopang.readhub.feature.news.NewsFragment;
import com.chihopang.readhub.feature.topic.list.HotTopicFragment;
import java.lang.reflect.Field;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends BaseFragment {
  @BindView(R.id.bottom_navigation_view) BottomNavigationView mBottomNavigationView;
  private SupportFragment[] mFragments = new SupportFragment[5];

  public static MainFragment newInstance() {
    MainFragment fragment = new MainFragment();
    return fragment;
  }

  @Override public int getFragmentLayout() {
    return R.layout.fragment_main;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initContent();
    initListener();
  }

  private void initContent() {
    if (findFragment(HotTopicFragment.class) == null) {
      mFragments[0] = HotTopicFragment.newInstance();
      mFragments[1] = NewsFragment.newInstance();
      mFragments[2] = TechNewsFragment.newInstance();
      mFragments[3] = BCNewsFragment.newInstance();
      mFragments[4] = MoreFragment.newInstance();
      loadMultipleRootFragment(R.id.frame_main_content, 0, mFragments[0], mFragments[1],
          mFragments[2], mFragments[3], mFragments[4]);
    } else {
      mFragments[0] = findFragment(HotTopicFragment.class);
      mFragments[1] = findFragment(NewsFragment.class);
      mFragments[2] = findFragment(TechNewsFragment.class);
      mFragments[3] = findFragment(BCNewsFragment.class);
      mFragments[4] = findFragment(MoreFragment.class);
    }
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
              case R.id.menu_item_block_chain:
                currentItem = 3;
                break;
              case R.id.menu_item_more:
                currentItem = 4;
                break;
              default:
                currentItem = 0;
            }
            if (mBottomNavigationView.getSelectedItemId() == item.getItemId()) {
              //当前页面已经为对应页面时，则回到顶部或刷新
              Fragment currentFragment = mFragments[currentItem];
              if (currentFragment instanceof BaseListFragment) {
                ((BaseListFragment) currentFragment).onTabClick();
              }
              if (currentFragment instanceof MoreFragment) {
                ((MoreFragment) currentFragment).onTabClick();
              }
              return true;
            }
            showHideFragment(mFragments[currentItem]);
            return true;
          }
        });
  }
}
