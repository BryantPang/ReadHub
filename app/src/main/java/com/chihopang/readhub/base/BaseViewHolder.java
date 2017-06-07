package com.chihopang.readhub.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
  public BaseViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutRes) {
    super(LayoutInflater.from(context).inflate(layoutRes, parent, false));
    ButterKnife.bind(this, itemView);
  }

  public abstract void bindTo(T value);
}
