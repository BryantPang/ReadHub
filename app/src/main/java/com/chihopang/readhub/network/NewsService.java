package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.News;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
  @GET("news") Observable<ApiData<News>> getNews();

  @GET("news") Observable<ApiData<News>> getMoreNews(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);

  @GET("blockchain") Observable<ApiData<News>> getBCNews();

  @GET("blockchain") Observable<ApiData<News>> getMoreBCNews(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);
}
