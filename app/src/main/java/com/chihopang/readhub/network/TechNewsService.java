package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.News;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TechNewsService {
  @GET("technews") Observable<ApiData<News>> getTechNews();

  @GET("technews") Observable<ApiData<News>> getMoreTechNews(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);
}
