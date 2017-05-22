package com.chihopang.readhub.feature.news;

import android.view.ViewGroup;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Topic;

public class NewsFragment extends BaseListFragment<Topic> {
  public static final String TAG = "NewsFragment";

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  @Override protected void requestData() {
    NewsPresenter.getData(this);
  }

  @Override public BaseViewHolder provideViewHolder(ViewGroup parent, int viewType) {
    return new NewsViewHolder(getActivity(), parent);
  }
}
