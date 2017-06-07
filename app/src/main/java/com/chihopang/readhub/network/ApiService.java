package com.chihopang.readhub.network;

import com.chihopang.readhub.app.Navigator;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
  private static Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(Navigator.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build();

  public static HotTopicService createHotTopicService() {
    return retrofit.create(HotTopicService.class);
  }

  public static NewsService createNewsService() {
    return retrofit.create(NewsService.class);
  }
}
