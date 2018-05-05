package com.chihopang.readhub.feature.blockchain;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.News;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.NewsService;
import io.reactivex.Observable;

public class BCNewsPresenter extends BaseListPresenter<News> {
  private NewsService mService = ApiService.createNewsService();

  @Override public Observable<ApiData<News>> request() {
    return mService.getBCNews();
  }

  @Override public Observable<ApiData<News>> requestMore() {
    return mService.getMoreBCNews(getLastCursor(), 10);
  }
}
