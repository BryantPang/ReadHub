package com.chihopang.readhub.feature.topic;

import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.Topic;

public class HotTopicPresenter extends BaseListPresenter<Topic> {
  public HotTopicPresenter(BaseListFragment<Topic> fragment) {
    super(fragment);
  }

  @Override public String provideUrl() {
    return Navigator.HOT_TOPIC_API;
  }
}
