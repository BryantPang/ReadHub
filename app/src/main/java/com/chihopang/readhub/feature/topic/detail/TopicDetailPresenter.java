package com.chihopang.readhub.feature.topic.detail;

import android.os.AsyncTask;
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.base.BasePresenter;
import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.model.TopicTimeLine;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.HotTopicService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopicDetailPresenter extends BasePresenter<TopicDetailFragment>
    implements INetworkPresenter<TopicDetailFragment> {
  private HotTopicService mService = ApiService.createHotTopicService();
  private String mTopicId;

  @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<Topic>() {
          @Override public void accept(@NonNull Topic topic) throws Exception {
            if (getView() == null) return;
            getView().onSuccess(topic);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            if (getView() == null) return;
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

  public void getTopicDetail(String topicId) {
    mTopicId = topicId;
    start();
  }

  protected void getTimeLine(final String topicId) {
    new TimeLineAsyncTask(topicId, this).execute();
  }

  public static class TimeLineAsyncTask extends AsyncTask<Void, Void, Document> {
    private String mTopicId;
    private TopicDetailPresenter mPresenter;

    public TimeLineAsyncTask(String topicId, TopicDetailPresenter presenter) {
      mTopicId = topicId;
      mPresenter = presenter;
    }

    @Override protected Document doInBackground(Void... params) {
      Document document = null;
      try {
        document = Jsoup.connect(Constant.TOPIC_DETAIL_URL + mTopicId).get();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return document;
    }

    @Override protected void onPostExecute(Document document) {
      super.onPostExecute(document);
      if (document == null) return;
      Elements timelineContainer = document.getElementsByClass(
          "timeline__container___3jHS8 timeline__container--PC___1D1r7");
      if (timelineContainer == null || timelineContainer.select("li").isEmpty()) return;
      List<TopicTimeLine> timeLines = new ArrayList<>();
      for (Element liElement : timelineContainer.select("li")) {
        TopicTimeLine timeLine = new TopicTimeLine();
        timeLine.date = liElement.getElementsByClass("date-item___1io1R").text();
        Element contentElement = liElement.getElementsByClass("content-item___3KfMq").get(0);
        timeLine.content = contentElement.getElementsByTag("a").get(0).text();
        timeLine.url = contentElement.getElementsByTag("a").get(0).attr("href");
        timeLines.add(timeLine);
      }
      if (mPresenter.getView() != null) mPresenter.getView().onGetTimeLineSuccess(timeLines);
      mPresenter = null;
    }
  }

}
