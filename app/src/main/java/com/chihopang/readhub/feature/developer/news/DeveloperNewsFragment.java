package com.chihopang.readhub.feature.developer.news;

import android.view.ViewGroup;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Topic;

public class DeveloperNewsFragment extends BaseListFragment<Topic> {
  @Override public boolean hasMore() {
    return false;
  }

  @Override public BaseViewHolder<Topic> provideViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public BaseListPresenter<Topic> createPresenter() {
    return null;
  }
}
