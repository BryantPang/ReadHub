package com.chihopang.readhub.base;

import com.chihopang.readhub.base.mvp.IPresenter;
import com.chihopang.readhub.base.mvp.IView;

public class BasePresenter<V extends IView> implements IPresenter<V> {
  private V mView;

  @Override public V getView() {
    return mView;
  }

  @Override public void attachView(V view) {
    mView = view;
  }

  @Override public void detachView() {
    mView = null;
  }
}
