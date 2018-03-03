package com.chihopang.readhub.base;

import android.support.annotation.NonNull;
import butterknife.ButterKnife;
import com.chihopang.readhub.base.mvp.IPresenter;
import com.chihopang.readhub.base.mvp.IView;
import me.yokeyword.fragmentation.SupportActivity;

public class BaseActivity<P extends IPresenter> extends SupportActivity implements IView<P> {
  private P mPresenter;
  private boolean mIsPresenterNeeded = false;

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    detachPresenter();
  }

  @Override public P getPresenter() {
    if (!mIsPresenterNeeded) {
      throw new RuntimeException(
          "This activity hasn't attached any presenters,if the presenter is needed,please invoke attachPresenter() first.");
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
}
