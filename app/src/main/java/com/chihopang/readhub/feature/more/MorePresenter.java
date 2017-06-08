package com.chihopang.readhub.feature.more;

import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.base.mvp.INetworkView;
import com.chihopang.readhub.model.Sponsor;
import com.chihopang.readhub.network.ApiService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MorePresenter implements INetworkPresenter {
  private INetworkView view;

  public MorePresenter(INetworkView view) {
    this.view = view;
  }

  @Override public INetworkView getView() {
    return view;
  }

  @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Sponsor>>() {
          @Override public void accept(@NonNull List<Sponsor> sponsors) throws Exception {
            getView().onSuccess(sponsors);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            getView().onError(new Exception("请求错误"));
          }
        });
  }

  @Override public void startRequestMore() {

  }

  @Override public Observable request() {
    return ApiService.createSponsorService().getSponsors();
  }

  @Override public Observable requestMore() {
    return null;
  }
}
