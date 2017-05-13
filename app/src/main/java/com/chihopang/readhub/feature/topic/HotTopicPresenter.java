package com.chihopang.readhub.feature.topic;

import android.util.Log;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.ApiData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotTopicPresenter {
  public static void getData(final HotTopicFragment fragment) {
    OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder()
        .url(Navigator.HOT_TOPIC_API)
        .build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        String jsonStr = response.body().string();
        Gson gson = new GsonBuilder().create();
        ApiData data = gson.fromJson(jsonStr, ApiData.class);
        fragment.onSuccess(data);
        Log.d("HotTopicPresenter", data.toString());
      }
    });
  }
}
