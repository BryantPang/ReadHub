package com.chihopang.readhub.base;

import android.util.Log;
import com.chihopang.readhub.model.ApiData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseListPresenter<T> implements Callback {
  private String requestMoreKey;
  private OkHttpClient mClient = new OkHttpClient();
  private Request mRequest = new Request.Builder()
      .url(provideUrl()).build();
  private BaseListFragment<T> mFragment;

  public BaseListPresenter(BaseListFragment<T> fragment) {
    mFragment = fragment;
  }

  public abstract String provideUrl();

  public void request() {
    Call mCall = mClient.newCall(mRequest);
    mCall.enqueue(this);
  }

  public void requestMore() {
    Request request = new Request.Builder()
        .url(provideUrl() + requestMoreKey)
        .build();
    Call mCall = mClient.newCall(request);
    mCall.enqueue(this);
  }

  @Override public void onFailure(Call call, IOException e) {
    getFragment().onError();
    e.printStackTrace();
  }

  @Override public void onResponse(Call call, Response response) throws IOException {
    Gson gson = new Gson();
    String jsonStr = response.body().string();
    ApiData data = gson.fromJson(jsonStr, ApiData.class);
    requestMoreKey = data.getNextPageUrl();
    getFragment().onSuccess((List<T>) data.getData());
    Log.d("BaseListPresenter", data.toString());
  }

  public BaseListFragment<T> getFragment() {
    return mFragment;
  }
}
