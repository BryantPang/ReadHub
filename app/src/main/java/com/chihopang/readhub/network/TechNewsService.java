package com.chihopang.readhub.network;

import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TechNewsService {
  @GET("technews") Observable<ApiData> getTechNews();
}
