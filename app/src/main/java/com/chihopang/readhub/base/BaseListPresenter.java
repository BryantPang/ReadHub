package com.chihopang.readhub.base;

import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseListPresenter<T> extends BasePresenter<BaseListFragment> implements
    INetworkPresenter<BaseListFragment> {
  private String lastCursor;

  @SuppressWarnings("unchecked") @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            if (getView() == null) return;
            if (apiData == null || apiData.getData() == null) {
              getView().onError(new Throwable("请求失败"));
              return;
            }
            getView().onSuccess(apiData.getData());
            lastCursor = apiData.getLastCursor();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            if (getView() == null) return;
            getView().onError(throwable);
          }
        });
  }

  @SuppressWarnings("unchecked") @Override public void startRequestMore() {
    requestMore().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            if (getView() == null) return;
            if (apiData == null || apiData.getData() == null) {
              getView().onError(new Throwable("请求失败"));
              return;
            }
            getView().onSuccess(apiData.getData());
            if (apiData.getData().isEmpty()) {
              getView().hasMore = false;
              return;
            }
            lastCursor = apiData.getLastCursor();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            if (getView() == null) return;
            getView().onError(throwable);
          }
        });
  }

  @Override public abstract Observable request();

  @Override public abstract Observable requestMore();

  public String getLastCursor() {
    return lastCursor;
  }
}
