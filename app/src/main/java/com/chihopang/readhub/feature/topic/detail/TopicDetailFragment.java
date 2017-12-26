package com.chihopang.readhub.feature.topic.detail;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseAdapter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.model.TopicTimeLine;
import java.io.IOException;
import me.yokeyword.fragmentation.SupportActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.parceler.Parcels;

public class TopicDetailFragment extends DialogFragment {
  public static final String TAG = "TopicDetailFragment";
  public static final int VIEW_TYPE_TOP = 99, VIEW_TYPE_BOTTOM = 98;

  @BindView(R.id.txt_topic_title) TextView mTxtTopicTitle;
  @BindView(R.id.txt_topic_time) TextView mTxtTopicTime;
  @BindView(R.id.txt_topic_description) TextView mTxtTopicDescription;
  @BindView(R.id.linear_web_title_container) LinearLayout mLinearTitleContainer;
  @BindView(R.id.linear_time_line_container) LinearLayout mLinearTimelineContainer;
  @BindView(R.id.recycler_time_line) RecyclerView mRecyclerTimeline;
  private Topic mTopic;
  private BaseAdapter<TopicTimeLine> mTimelineAdapter = new BaseAdapter<TopicTimeLine>() {
    @Override
    public BaseViewHolder<TopicTimeLine> onCreateViewHolder(ViewGroup parent, int viewType) {
      return new TopicTimeLineViewHolder(getContext(), parent);
    }

    @Override public int getItemViewType(int position) {
      if (position == 0) return VIEW_TYPE_TOP;
      if (position == getItemCount() - 1) return VIEW_TYPE_BOTTOM;
      return super.getItemViewType(position);
    }
  };

  public static TopicDetailFragment newInstance(Topic topic) {
    TopicDetailFragment fragment = new TopicDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Navigator.EXTRA_TOPIC, Parcels.wrap(topic));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);
    ButterKnife.bind(this, view);
    mTopic = Parcels.unwrap(getArguments().getParcelable(Navigator.EXTRA_TOPIC));
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupView();
    getTimeLine();
  }

  private void getTimeLine() {
    new AsyncTask<Void, Void, Document>() {
      @Override protected Document doInBackground(Void... params) {
        Document document = null;
        try {
          document = Jsoup.connect(Navigator.TOPIC_DETAIL_URL + mTopic.getId()).get();
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
        for (Element liElement : timelineContainer.select("li")) {
          TopicTimeLine timeLine = new TopicTimeLine();
          timeLine.date = liElement.getElementsByClass("date-item___1io1R").text();
          Element contentElement = liElement.getElementsByClass("content-item___3KfMq").get(0);
          timeLine.content = contentElement.getElementsByTag("a").get(0).text();
          timeLine.url =
              Navigator.TOPIC_DETAIL_URL + contentElement.getElementsByTag("a").get(0).attr("href");
          mTimelineAdapter.addItem(timeLine);
        }
        mLinearTimelineContainer.setVisibility(
            mTimelineAdapter.getItemCount() != 0 ? View.VISIBLE : View.GONE);
      }
    }.execute();
  }

  private void setupView() {
    mTxtTopicTitle.setText(mTopic.getTitle());
    mTxtTopicTime.setText(mTopic.getFormatPublishDate());
    mTxtTopicDescription.setText(mTopic.getSummary());
    for (final Topic topic : mTopic.getNewsArray()) {
      TextView textView = new TextView(getContext());
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      textView.setLayoutParams(params);
      textView.setPadding(10, 16, 10, 16);
      textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_data, 0, 0, 0);
      textView.setCompoundDrawablePadding(15);
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
      textView.setTextColor(Color.parseColor("#607D8B"));
      textView.setBackgroundResource(R.drawable.selector_btn_background);
      if (TextUtils.isEmpty(topic.getSiteName())) {
        textView.setText(topic.getTitle());
      } else {
        SpannableString spannableTitle =
            SpannableString.valueOf(topic.getTitle() + " " + topic.getSiteName());
        spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
            topic.getTitle().length() + 1,
            topic.getTitle().length() + topic.getSiteName().length() + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableTitle);
      }
      textView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismiss();
          ((SupportActivity) v.getContext()).findFragment(MainFragment.class)
              .start(WebViewFragment.newInstance(topic));
        }
      });
      mLinearTitleContainer.addView(textView);
    }
    mRecyclerTimeline.setAdapter(mTimelineAdapter);
    mRecyclerTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerTimeline.setNestedScrollingEnabled(false);
  }

  @OnClick(R.id.img_back) void onCloseClick() {
    dismiss();
  }
}
