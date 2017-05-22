package com.chihopang.readhub.feature.more;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Sponsor;
import com.facebook.drawee.view.SimpleDraweeView;

public class SponsorViewHolder extends BaseViewHolder<Sponsor> {
  private TextView mTxtSponsorName;
  private SimpleDraweeView mImgSponsor;

  public SponsorViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_sponsor);
    mTxtSponsorName = (TextView) itemView.findViewById(R.id.txt_sponsor_name);
    mImgSponsor = (SimpleDraweeView) itemView.findViewById(R.id.img_sponsor);
  }

  @Override public void bindTo(final Sponsor value) {
    mTxtSponsorName.setText(value.getSponsor());
    mImgSponsor.setImageURI(Uri.parse(value.getImgUrl()));
    mImgSponsor.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(mImgSponsor.getContext(), value.getSlogan(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
