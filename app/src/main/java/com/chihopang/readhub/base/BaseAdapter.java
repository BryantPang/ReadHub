package com.chihopang.readhub.base;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
  private List<T> mItemList = new ArrayList<>();

  @Override public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
    if (position + 1 >= mItemList.size()) return;
    holder.bindTo(mItemList.get(position));
  }

  @Override public int getItemCount() {
    return mItemList.size();
  }

  public void addItem(T value) {
    mItemList.add(value);
    notifyItemInserted(mItemList.size() - 1);
  }

  public void addItems(Collection<T> valueCollection) {
    mItemList.addAll(valueCollection);
    notifyItemRangeChanged(mItemList.size() - valueCollection.size(), mItemList.size());
  }

  public void clear() {
    mItemList.clear();
    notifyDataSetChanged();
  }

  public void remove(T value) {
    if (mItemList.contains(value)) {
      mItemList.remove(value);
      notifyItemRemoved(mItemList.size());
    }
  }
}
