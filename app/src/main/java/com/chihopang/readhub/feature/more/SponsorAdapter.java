package com.chihopang.readhub.feature.more;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.OnItemClickListener;
import com.chihopang.readhub.model.Sponsor;
import java.util.ArrayList;
import java.util.List;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder> {
  private OnItemClickListener listener;
  private List<Sponsor> mItemList = new ArrayList<>();

  @Override
  public SponsorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sponsor, parent, false);
    return new SponsorViewHolder(v);
  }

  @Override public void onBindViewHolder(SponsorViewHolder holder, int position) {
    holder.bind(mItemList.get(position));
  }

  @Override public int getItemCount() {
    return mItemList.size();
  }

  public void addItem(Sponsor item) {
    mItemList.add(item);
  }

  public void addItems(List<Sponsor> itemList) {
    mItemList.addAll(itemList);
  }

  public void clear() {
    mItemList.clear();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  class SponsorViewHolder extends RecyclerView.ViewHolder {
    private TextView mTxtSponsorName;

    public SponsorViewHolder(View itemView) {
      super(itemView);
      mTxtSponsorName = (TextView) itemView.findViewById(R.id.txt_sponsor_name);
    }

    public void bind(Sponsor value) {
      mTxtSponsorName.setText(value.getSponsor());
    }
  }
}
