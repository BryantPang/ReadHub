package com.chihopang.readhub.base;

import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.model.ApiData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseListPresenter<T> implements INetworkPresenter {
  private BaseListFragment<T> mFragment;
  private String lastCursor;
  public BaseListPresenter(BaseListFragment<T> fragment) {
    mFragment = fragment;
  }

  @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            if (apiData == null || apiData.getData() == null) {
              getView().onError(new Throwable("请求失败"));
              return;
            }
            getView().onSuccess(apiData.getData());
            lastCursor = apiData.getData().get(apiData.getData().size() - 1).getLastCursor();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            getView().onError(throwable);
          }
        });
  }

  @Override public void startRequestMore() {
    requestMore().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<ApiData>() {
          @Override public void accept(@NonNull ApiData apiData) throws Exception {
            if (apiData == null || apiData.getData() == null) {
              getView().onError(new Throwable("请求失败"));
              return;
            }
            getView().onSuccess(apiData.getData());
            if (apiData.getData().isEmpty()) {
              getView().hasMore = false;
              return;
            }
            lastCursor = apiData.getData().get(apiData.getData().size() - 1).getLastCursor();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            getView().onError(throwable);
          }
        });
  }

  @Override public abstract Observable<ApiData> request();

  @Override public abstract Observable<ApiData> requestMore();

  @Override public BaseListFragment<T> getView() {
    return mFragment;
  }

  public String getLastCursor() {
    return lastCursor;
  }
}
