package com.chihopang.readhub.base.mvp;

import android.support.annotation.Nullable;

public interface IPresenter<V extends IView> {
  @Nullable V getView();

  void attachView(V view);

  void detachView();
}
