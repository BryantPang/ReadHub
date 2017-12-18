package com.chihopang.readhub.base.mvp;

public interface INetworkView extends IView {
  void onSuccess(Object t);

  void onError(Throwable e);
}
