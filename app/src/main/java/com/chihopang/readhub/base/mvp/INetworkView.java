package com.chihopang.readhub.base.mvp;

public interface INetworkView<P extends INetworkPresenter, D> extends IView<P> {
  void onSuccess(D data);

  void onError(Throwable e);
}