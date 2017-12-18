package com.chihopang.readhub.feature.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.common.WebviewFragment;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation.SupportActivity;

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

    String time;
    if ((!TextUtils.isEmpty(value.getAuthorName())) && (!TextUtils.isEmpty(value.getSiteName()))) {
      time = ReadhubApplication.mContext.getString(R.string.site_author_time_format,
          value.getSiteName(), value.getAuthorName(),
          value.getPublishDateCountDown());
    } else if (TextUtils.isEmpty(value.getAuthorName()) && TextUtils.isEmpty(value.getSiteName())) {
      time = value.getPublishDateCountDown();
    } else if (TextUtils.isEmpty(value.getSiteName())) {
      time =
          ReadhubApplication.mContext.getString(R.string.author_time_format, value.getAuthorName(),
              value.getPublishDateCountDown());
    } else {
      time = ReadhubApplication.mContext.getString(R.string.author_time_format, value.getSiteName(),
          value.getPublishDateCountDown());
    }
    mTxtTime.setText(time);


    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((SupportActivity) v.getContext()).findFragment(MainFragment.class)
            .start(WebviewFragment.newInstance(value));
      }
    });

    mTxtTitle.setVisibility(TextUtils.isEmpty(value.getTitle()) ? View.GONE : View.VISIBLE);
    mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);
  }
}
