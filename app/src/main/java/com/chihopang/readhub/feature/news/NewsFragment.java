package com.chihopang.readhub.feature.news;

import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.model.Topic;
import java.util.List;

public class NewsFragment extends BaseListFragment<Topic> {
  public static final String TAG = "NewsFragment";

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  @Override protected void requestData() {
    NewsPresenter.getData(this);
    ((MainActivity) getActivity()).mBox.showLoadingLayout();
  }

  @Override protected void onSuccess(final List<Topic> itemList) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        getAdapter().addItems(itemList);
        ((MainActivity) getActivity()).mBox.hideAll();
      }
    });
  }

  @Override public BaseViewHolder provideViewHolder() {
    return new NewsViewHolder(getActivity());
  }
}
