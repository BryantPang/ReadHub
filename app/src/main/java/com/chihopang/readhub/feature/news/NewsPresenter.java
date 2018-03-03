package com.chihopang.readhub.feature.news;

import com.chihopang.readhub.base.BaseListPresenter;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.NewsService;
import io.reactivex.Observable;

public class NewsPresenter extends BaseListPresenter<Topic> {
  private NewsService mService = ApiService.createNewsService();

  @Override public Observable<ApiData> request() {
    return mService.getNews();
  }

  @Override public Observable<ApiData> requestMore() {
    return mService.getMoreNews(getLastCursor(), 10);
  }
}
