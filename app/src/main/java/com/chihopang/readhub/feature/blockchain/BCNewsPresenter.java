package com.chihopang.readhub.feature.blockchain;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.NewsService;
import io.reactivex.Observable;

public class BCNewsPresenter extends BaseListPresenter<Topic> {
  private NewsService mService = ApiService.createNewsService();

  @Override public Observable<ApiData> request() {
    return mService.getBCNews();
  }

  @Override public Observable<ApiData> requestMore() {
    return mService.getMoreBCNews(getLastCursor(), 10);
  }
}
