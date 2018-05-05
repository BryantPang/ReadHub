package com.chihopang.readhub.feature.developer;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.News;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.TechNewsService;
import io.reactivex.Observable;

public class TechNewsPresenter extends BaseListPresenter<News> {
  private TechNewsService mService = ApiService.createTechNewsService();

  @Override public Observable<ApiData<News>> request() {
    return mService.getTechNews();
  }

  @Override public Observable<ApiData<News>> requestMore() {
    return mService.getMoreTechNews(getLastCursor(), 10);
  }
}
