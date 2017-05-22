package com.chihopang.readhub.feature.topic;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Topic;

public class HotTopicViewHolder extends BaseViewHolder<Topic> {
  private TextView mTxtTitle, mTxtSummary, mTxtTime;
  private RelativeLayout mItemContainer;

  public HotTopicViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_news);
    mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
    mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
    mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
    mItemContainer = (RelativeLayout) itemView.findViewById(R.id.list_item_container);
  }

  @Override public void bindTo(Topic value) {
    String date = value.getPublishDate();
    SpannableString spannableString = new SpannableString(value.getTitle() + date);
    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
        value.getTitle().length(), value.getTitle().length() + date.length(),
        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    spannableString.setSpan(new AbsoluteSizeSpan(16, true), value.getTitle().length(),
        value.getTitle().length() + date.length(),
        Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    mTxtTitle.setText(spannableString);

    mTxtTime.setVisibility(View.GONE);
    mTxtSummary.setText(value.getSummary());
  }
}
