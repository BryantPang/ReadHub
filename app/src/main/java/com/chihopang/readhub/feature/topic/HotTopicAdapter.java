package com.chihopang.readhub.feature.topic;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.OnItemClickListener;
import com.chihopang.readhub.model.Topic;
import java.util.ArrayList;
import java.util.List;

public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.HotTopicViewHolder> {
  private List<Topic> mItemList = new ArrayList<>();
  private OnItemClickListener listener;

  @Override
  public HotTopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
    return new HotTopicViewHolder(v);
  }

  @Override public void onBindViewHolder(HotTopicViewHolder holder, int position) {
    final Topic value = mItemList.get(position);
    holder.bind(value);
  }

  @Override public int getItemCount() {
    return mItemList.size();
  }

  public void addItem(Topic topic) {
    mItemList.add(topic);
  }

  public void addItems(List<Topic> topicList) {
    mItemList.addAll(topicList);
  }

  public void clear() {
    mItemList.clear();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  class HotTopicViewHolder extends RecyclerView.ViewHolder {
    private TextView mTxtTitle, mTxtSummary, mTxtTime;
    private RelativeLayout mItemContainer;

    public HotTopicViewHolder(View itemView) {
      super(itemView);
      mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
      mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
      mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
      mItemContainer = (RelativeLayout) itemView.findViewById(R.id.list_item_container);
    }

    public void bind(final Topic value) {
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

      if (listener == null) return;
      mItemContainer.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          listener.onClick(value);
        }
      });
    }
  }
}
