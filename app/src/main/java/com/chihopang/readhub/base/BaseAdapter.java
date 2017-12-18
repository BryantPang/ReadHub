package com.chihopang.readhub.base;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
  private List<T> mItemList = new ArrayList<>();

  @Override public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
    if (position < mItemList.size()) holder.bindTo(mItemList.get(position));
  }

  @Override public int getItemCount() {
    return mItemList.size();
  }

  public void addItem(T value) {
    mItemList.add(value);
    //notifyItemInserted(mItemList.size());
  }

  public void addItems(Collection<T> valueCollection) {
    mItemList.addAll(mItemList.size(), valueCollection);
    notifyDataSetChanged();//TODO 所有刷新方法都需要重新检查
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
