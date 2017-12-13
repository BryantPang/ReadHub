package com.chihopang.readhub.feature.topic;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation.SupportActivity;

public class HotTopicViewHolder extends BaseViewHolder<Topic> {
  private TextView mTxtTitle, mTxtSummary, mTxtTime;

  public HotTopicViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_news);
    mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
    mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
    mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
  }

  @Override public void bindTo(final Topic value) {
    mTxtTitle.setText(value.getTitle());
    mTxtSummary.setText(value.getSummary());
    mTxtTime.setText(value.getPublishDate());

    mTxtTitle.setVisibility(TextUtils.isEmpty(value.getPublishDate()) ? View.GONE : View.VISIBLE);
    mTxtTitle.setVisibility(TextUtils.isEmpty(value.getTitle()) ? View.GONE : View.VISIBLE);
    mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((SupportActivity) v.getContext()).findFragment(MainFragment.class)
            .start(TopicDetailFragment.newInstance(value));
      }
    });
  }
}
