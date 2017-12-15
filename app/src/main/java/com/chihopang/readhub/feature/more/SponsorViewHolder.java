package com.chihopang.readhub.feature.more;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.model.Sponsor;
import com.facebook.drawee.view.SimpleDraweeView;
import me.yokeyword.fragmentation.SupportActivity;

public class SponsorViewHolder extends BaseViewHolder<Sponsor> {
  private SimpleDraweeView mImgSponsor;

  public SponsorViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_sponsor);
    mImgSponsor = (SimpleDraweeView) itemView.findViewById(R.id.img_sponsor);
  }

  @Override public void bindTo(final Sponsor value) {
    mImgSponsor.setImageURI(Uri.parse(value.getImgUrl()));
    mImgSponsor.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        SponsorDetailFragment.newInstance(value)
            .show(((SupportActivity) view.getContext()).getSupportFragmentManager(),
                SponsorDetailFragment.TAG);
      }
    });
  }
}
