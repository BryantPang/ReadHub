package com.chihopang.readhub.feature.developer.news;

import com.chihopang.readhub.base.BaseListFragment;
import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import io.reactivex.Observable;

public class TechNewsPresenter extends BaseListPresenter<Topic> {
  public TechNewsPresenter(
      BaseListFragment<Topic> fragment) {
    super(fragment);
  }

  @Override public Observable<ApiData> request() {
    return ApiService.createTechNewsService().getTechNews();
  }

  @Override public Observable<ApiData> requestMore() {
    return null;
  }
}
