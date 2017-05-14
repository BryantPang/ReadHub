package com.chihopang.readhub.feature.more;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.OnItemClickListener;
import com.chihopang.readhub.model.Sponsor;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private SimpleDraweeView mImgSponsor;

    public SponsorViewHolder(View itemView) {
      super(itemView);
      mTxtSponsorName = (TextView) itemView.findViewById(R.id.txt_sponsor_name);
      mImgSponsor = (SimpleDraweeView) itemView.findViewById(R.id.img_sponsor);
    }

    public void bind(final Sponsor value) {
      mTxtSponsorName.setText(value.getSponsor());
      mImgSponsor.setImageURI(Uri.parse(value.getImgUrl()));
      mImgSponsor.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Toast.makeText(mImgSponsor.getContext(), value.getSlogan(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}
