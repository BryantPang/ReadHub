package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
  @GET("news") Observable<ApiData> getNews();

  @GET("news") Observable<ApiData> getMoreNews(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);

  @GET("blockchain") Observable<ApiData> getBCNews();

  @GET("blockchain") Observable<ApiData> getMoreBCNews(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);
}
