package com.chihopang.readhub.base;

import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;

public class LoadingViewHolder extends BaseViewHolder {
  public LoadingViewHolder(ViewGroup parent, boolean hasMore) {
    super(parent.getContext(), parent, R.layout.list_item_loading);
    itemView.findViewById(R.id.txt_loading_item).setVisibility(hasMore ? View.GONE : View.VISIBLE);
    itemView.findViewById(R.id.progress_bar_loading_item)
        .setVisibility(hasMore ? View.VISIBLE : View.GONE);
  }

  @Override public void bindTo(Object value) {

  }
}
