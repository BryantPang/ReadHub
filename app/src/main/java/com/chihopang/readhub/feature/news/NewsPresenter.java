package com.chihopang.readhub.feature.news;

import android.util.Log;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.TopicData;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsPresenter {
  public static void getData(final NewsFragment fragment) {
    OkHttpClient client = new OkHttpClient.Builder().build();
    Request request = new Request.Builder()
        .url(Navigator.NEWS_API)
        .build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        Gson gson = new Gson();
        String jsonStr = response.body().string();
        TopicData data = gson.fromJson(jsonStr, TopicData.class);
        fragment.onSuccess(data);
        Log.d("HotTopicPresenter", data.toString());
      }
    });
  }
}
