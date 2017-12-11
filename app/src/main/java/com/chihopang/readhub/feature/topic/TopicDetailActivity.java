package com.chihopang.readhub.feature.topic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseActivity;
import com.chihopang.readhub.feature.news.ArticlePageActivity;
import com.chihopang.readhub.model.Topic;
import org.parceler.Parcels;

public class TopicDetailActivity extends BaseActivity {
  private static final String EXTRA_TOPIC = "EXTRA_TOPIC";

  @BindView(R.id.txt_topic_title) TextView mTxtTopicTitle;
  @BindView(R.id.txt_topic_description) TextView mTxtTopicDescription;
  @BindView(R.id.linear_web_title_container) LinearLayout mLinearTitleContainer;

  private Topic mTopic;

  public static void start(Context context, Topic topic) {
    Intent intent = new Intent(context, TopicDetailActivity.class);
    intent.putExtra(EXTRA_TOPIC, Parcels.wrap(topic));
    context.startActivity(intent);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_topic_detail);
    mTopic = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_TOPIC));
    setupView();
  }

  private void setupView() {
    mTxtTopicTitle.setText(mTopic.getTitle());
    mTxtTopicDescription.setText(mTopic.getSummary());
    for (final Topic topic : mTopic.getNewsArray()) {
      TextView textView = new TextView(this);
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      params.setMargins(10, 10, 10, 10);
      textView.setLayoutParams(params);
      textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cycle, 0, 0, 0);
      textView.setCompoundDrawablePadding(15);
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
      textView.setTextColor(Color.parseColor("#000000"));
      textView.setText(topic.getTitle());
      textView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ArticlePageActivity.start(TopicDetailActivity.this, topic);
        }
      });
      mLinearTitleContainer.addView(textView);
    }
  }
}
