package com.chihopang.readhub.feature.topic.list;

import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import io.reactivex.Observable;

public class HotTopicPresenter extends BaseListPresenter<Topic> {
  public HotTopicPresenter(BaseListFragment<Topic> fragment) {
    super(fragment);
  }

  @Override public Observable<ApiData> request() {
    return ApiService.createHotTopicService().getHotTopic();
  }

  @Override public Observable<ApiData> requestMore() {
    return ApiService.createHotTopicService().getMoreHotTopic(getLastCursor(), 10);
  }
}
