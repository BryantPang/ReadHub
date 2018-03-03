package com.chihopang.readhub.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.chihopang.readhub.base.mvp.IPresenter;
import com.chihopang.readhub.base.mvp.IView;

public abstract class BaseDialogFragment<P extends IPresenter> extends DialogFragment implements
    IView<P> {
  private P mPresenter;
  private boolean mIsPresenterNeeded = false;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getFragmentLayout(), container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    detachPresenter();
  }

  @Override public P getPresenter() {
    if (!mIsPresenterNeeded) {
      throw new RuntimeException(
          "This fragment hasn't attached any presenters,if the presenter is needed,please invoke attachPresenter() first.");
    }
    return mPresenter;
  }

  @SuppressWarnings("unchecked") @Override public void attachPresenter(@NonNull P presenter) {
    mIsPresenterNeeded = true;
    mPresenter = presenter;
    presenter.attachView(this);
  }

  @Override public void detachPresenter() {
    if (mPresenter == null) return;
    mPresenter.detachView();
    mPresenter = null;
  }

  @LayoutRes public abstract int getFragmentLayout();
}
