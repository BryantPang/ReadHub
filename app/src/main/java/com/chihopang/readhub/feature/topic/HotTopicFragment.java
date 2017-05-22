package com.chihopang.readhub.feature.topic;

import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.model.Topic;
import java.util.List;

public class HotTopicFragment extends BaseListFragment<Topic> {
  public static final String TAG = "HotTopicFragment";

  public static HotTopicFragment newInstance() {
    return new HotTopicFragment();
  }

  @Override protected void requestData() {
    HotTopicPresenter.getData(this);
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
    return new HotTopicViewHolder(getActivity());
  }
}
