package com.chihopang.readhub.feature.news;

import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Topic;

public class NewsViewHolder extends BaseViewHolder<Topic> {
  private TextView mTxtTitle, mTxtSummary, mTxtTime;
  RelativeLayout mItemContainer;

  public NewsViewHolder(Activity activity) {
    super(activity.getLayoutInflater().inflate(R.layout.list_item_news, null));
    mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
    mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
    mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
    mItemContainer = (RelativeLayout) itemView.findViewById(R.id.list_item_container);
  }

  @Override public void bindTo(Topic value) {
    mTxtTitle.setText(value.getTitle());
    mTxtSummary.setText(value.getSummary());
    mTxtTime.setText(
        ReadhubApplication.mContext.getString(R.string.author_time_format, value.getAuthorName(),
            value.getPublishDate()));
  }
}
