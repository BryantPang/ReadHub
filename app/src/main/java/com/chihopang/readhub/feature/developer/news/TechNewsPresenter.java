package com.chihopang.readhub.feature.developer.news;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.TechNewsService;
import io.reactivex.Observable;

public class TechNewsPresenter extends BaseListPresenter<Topic> {
  private TechNewsService mService = ApiService.createTechNewsService();

  @Override public Observable<ApiData> request() {
    return mService.getTechNews();
  }

  @Override public Observable<ApiData> requestMore() {
    return mService.getMoreTechNews(getLastCursor(), 10);
  }
}
