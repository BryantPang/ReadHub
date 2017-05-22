package com.chihopang.readhub.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
  public BaseViewHolder(View itemVIew) {
    super(itemVIew);
  }

  public abstract void bindTo(T value);
}
