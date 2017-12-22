package com.chihopang.readhub.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.chihopang.readhub.R;

public class LoadingViewHolder extends BaseViewHolder {
  @BindView(R.id.txt_loading_item) TextView mTxtNoMore;
  @BindView(R.id.progress_bar_loading_item) ProgressBar mProgressBar;
  private boolean hasMore;

  public LoadingViewHolder(ViewGroup parent, boolean hasMore) {
    super(parent.getContext(), parent, R.layout.list_item_loading);
    this.hasMore = hasMore;
  }

  @Override public void bindTo(Object value) {
    mTxtNoMore.setVisibility(hasMore ? View.GONE : View.VISIBLE);
    mProgressBar.setVisibility(hasMore ? View.VISIBLE : View.GONE);
  }
}
