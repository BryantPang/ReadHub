package com.chihopang.readhub.feature.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.OnItemClickListener;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.model.Topic;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
  private List<Topic> mItemList = new ArrayList<>();
  private OnItemClickListener listener;

  @Override public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
    return new NewsViewHolder(v);
  }

  @Override public void onBindViewHolder(NewsViewHolder holder, int position) {
    holder.bind(mItemList.get(position));
  }

  @Override public int getItemCount() {
    return mItemList.size();
  }

  public void addItem(Topic item) {
    mItemList.add(item);
  }

  public void addItems(List<Topic> itemList) {
    mItemList.addAll(itemList);
  }

  public void clear() {
    mItemList.clear();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView mTxtTitle, mTxtSummary, mTxtTime;
    RelativeLayout mItemContainer;

    NewsViewHolder(View itemView) {
      super(itemView);
      mTxtTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
      mTxtSummary = (TextView) itemView.findViewById(R.id.txt_news_summary);
      mTxtTime = (TextView) itemView.findViewById(R.id.txt_news_time);
      mItemContainer = (RelativeLayout) itemView.findViewById(R.id.list_item_container);
    }

    void bind(final Topic value) {
      mTxtTitle.setText(value.getTitle());
      mTxtSummary.setText(value.getSummary());
      mTxtTime.setText(
          ReadhubApplication.mContext.getString(R.string.author_time_format, value.getAuthorName(),
              value.getPublishDate()));

      if (listener == null) return;
      mItemContainer.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          listener.onClick(value);
        }
      });
    }
  }
}
