package com.chihopang.readhub.base.mvp;

import io.reactivex.Observable;

public interface INetworkPresenter<V extends INetworkView> extends IPresenter<V> {
  void start();

  void startRequestMore();

  Observable request();

  Observable requestMore();
}
