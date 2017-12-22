package com.chihopang.readhub.base.mvp;

public interface INetworkView<T> extends IView {
  void onSuccess(T t);

  void onError(Throwable e);

  @Override INetworkPresenter getPresenter();
}
