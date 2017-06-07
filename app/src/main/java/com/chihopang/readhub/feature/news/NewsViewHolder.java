package com.chihopang.readhub.feature.news;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Topic;

public class NewsViewHolder extends BaseViewHolder<Topic> {
  private Context mContext;
  private TextView mTxtTitle, mTxtSummary, mTxtTime;

  public NewsViewHolder(final Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_news);
    mContext = context;
    mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
    mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
    mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
  }

  @Override public void bindTo(final Topic value) {
    mTxtTitle.setText(value.getTitle());
    mTxtSummary.setText(value.getSummary());
    mTxtTime.setText(
        ReadhubApplication.mContext.getString(R.string.author_time_format, value.getAuthorName(),
            value.getPublishDate()));
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ArticlePageActivity.start((Activity) mContext, value);
      }
    });
  }
}
