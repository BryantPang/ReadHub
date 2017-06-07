package com.chihopang.readhub.feature.news;

import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.Topic;

public class NewsPresenter extends BaseListPresenter<Topic> {
  public NewsPresenter(BaseListFragment<Topic> fragment) {
    super(fragment);
  }

  @Override public String provideUrl() {
    return Navigator.NEWS_API;
  }
}
