package com.chihopang.readhub.base.mvp;

import io.reactivex.Observable;

public interface INetworkPresenter extends IPresenter {
  void start();

  void startRequestMore();

  Observable request();

  Observable requestMore();
}
