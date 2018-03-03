package com.chihopang.readhub.base.mvp;

import android.support.annotation.NonNull;

public interface IView<P extends IPresenter> {
  P getPresenter();

  void attachPresenter(@NonNull P presenter);

  void detachPresenter();
}
