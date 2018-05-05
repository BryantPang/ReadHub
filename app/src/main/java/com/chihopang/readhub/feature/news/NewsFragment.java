package com.chihopang.readhub.feature.news;

import android.view.ViewGroup;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.News;

public class NewsFragment extends BaseListFragment<News> {
  public static final String TAG = "NewsFragment";

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  @Override public BaseViewHolder<News> provideViewHolder(ViewGroup parent, int viewType) {
    return new NewsViewHolder(getActivity(), parent);
  }

  @Override public BaseListPresenter<News> createPresenter() {
    return new NewsPresenter();
  }
}
