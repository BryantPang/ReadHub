package com.chihopang.readhub.feature.developer.news;

import android.view.ViewGroup;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.news.NewsViewHolder;
import com.chihopang.readhub.model.Topic;

public class TechNewsFragment extends BaseListFragment<Topic> {
  public static String TAG = "TechNewsFragment";

  public static TechNewsFragment newInstance() {
    return new TechNewsFragment();
  }

  @Override public BaseViewHolder<Topic> provideViewHolder(ViewGroup parent, int viewType) {
    return new NewsViewHolder(getActivity(), parent);
  }

  @Override public BaseListPresenter<Topic> createPresenter() {
    return new TechNewsPresenter();
  }
}
