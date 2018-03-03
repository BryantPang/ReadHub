package com.chihopang.readhub.feature.topic.list;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.HotTopicService;
import io.reactivex.Observable;

public class HotTopicPresenter extends BaseListPresenter<Topic> {
  private HotTopicService mService = ApiService.createHotTopicService();

  @Override public Observable<ApiData> request() {
    return mService.getHotTopic();
  }

  @Override public Observable<ApiData> requestMore() {
    return mService.getMoreHotTopic(getLastCursor(), 10);
  }
}
