package com.chihopang.readhub.feature.topic.detail;

import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.HotTopicService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TopicDetailPresenter implements INetworkPresenter {
  private HotTopicService mService = ApiService.createHotTopicService();
  private TopicDetailFragment mView;
  private String mTopicId;

  public TopicDetailPresenter(TopicDetailFragment mView) {
    this.mView = mView;
  }

  @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<Topic>() {
          @Override public void accept(@NonNull Topic topic) throws Exception {
            getView().onSuccess(topic);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            getView().onError(throwable);
          }
        });
  }

  @Override public void startRequestMore() {

  }

  @Override public Observable<Topic> request() {
    return mService.getHotTopicDetail(mTopicId);
  }

  @Override public Observable<Topic> requestMore() {
    return null;
  }

  @Override public TopicDetailFragment getView() {
    return mView;
  }

  public void getTopicDetail(String topicId) {
    mTopicId = topicId;
    start();
  }
}
