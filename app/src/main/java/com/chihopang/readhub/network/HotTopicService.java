package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HotTopicService {
  @GET("topic") Observable<ApiData> getHotTopic();

  @GET("topic") Observable<ApiData> getMoreHotTopic(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);
}
