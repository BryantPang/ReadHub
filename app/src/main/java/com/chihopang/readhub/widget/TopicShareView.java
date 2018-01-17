package com.chihopang.readhub.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.model.Topic;

public class TopicShareView extends ScrollView {
  @BindView(R.id.txt_topic_share_title) TextView mTxtTopicTitle;
  @BindView(R.id.txt_topic_summary) TextView mTxtTopicSummary;
  private Topic mTopic;

  public TopicShareView(Context context) {
    super(context);
    init(context);
  }

  public TopicShareView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public TopicShareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  public TopicShareView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.view_topic_share, this, true);
    ButterKnife.bind(this);
  }

  public void setup(Topic topic) {
    mTopic = topic;
    mTxtTopicTitle.setText(topic.getTitle());
    mTxtTopicSummary.setText(topic.getSummary());
  }

  public Topic getTopic() {
    return mTopic;
  }
}