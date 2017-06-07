package com.chihopang.readhub.base;

import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public abstract class BaseListPresenter<T> {
  private BaseListFragment<T> mFragment;
  private long lastCursor;
  public BaseListPresenter(BaseListFragment<T> fragment) {
    mFragment = fragment;
  }

  public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            getFragment().onSuccess((List<T>) apiData.getData());
            lastCursor = apiData.getData().get(apiData.getData().size() - 1).getOrder();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            getFragment().onError();
          }
        });
  }

  public void startRequstMore() {
    requestMore().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            getFragment().onSuccess((List<T>) apiData.getData());
            lastCursor = apiData.getData().get(apiData.getData().size() - 1).getOrder();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            getFragment().onError();
          }
        });
  }

  public abstract Observable<ApiData> request();

  public abstract Observable<ApiData> requestMore();

  public BaseListFragment<T> getFragment() {
    return mFragment;
  }

  public long getLastCursor() {
    return lastCursor;
  }
}
