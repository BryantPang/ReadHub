package com.chihopang.readhub.feature.more;

import android.util.Log;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.Sponsor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MorePresenter {
  public static void getSponsorData(final MoreFragment fragment) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(Navigator.SPONSOR_API)
        .build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        String jsonStr = response.body().string();
        Gson gson = new Gson();
        List<Sponsor> sponsorList = gson.fromJson(jsonStr, new TypeToken<List<Sponsor>>() {
        }.getType());
        Log.d("MorePresenter", sponsorList.toString());
        fragment.onSuccess(sponsorList);
      }
    });
  }
}
