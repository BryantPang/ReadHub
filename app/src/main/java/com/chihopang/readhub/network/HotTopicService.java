package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.InstantReadData;
import com.chihopang.readhub.model.Topic;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotTopicService {
  @GET("topic") Observable<ApiData> getHotTopic();

  @GET("topic") Observable<ApiData> getMoreHotTopic(@Query("lastCursor") String lastCursor,
      @Query("pageSize") int pageSize);

  @GET("topic/{topic_id}") Observable<Topic> getHotTopicDetail(@Path("topic_id") String topicId);

  @GET("/topic/instantview") Observable<InstantReadData> getInstantRead(
      @Query("topicId") String topicId);
}
