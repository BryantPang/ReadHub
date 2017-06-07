package com.chihopang.readhub.feature.news;

import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import io.reactivex.Observable;

public class NewsPresenter extends BaseListPresenter<Topic> {
  public NewsPresenter(BaseListFragment<Topic> fragment) {
    super(fragment);
  }

  @Override public Observable<ApiData> request() {
    return ApiService.createNewsService().getNews();
  }

  @Override public Observable<ApiData> requestMore() {
    return null;
  }
}
