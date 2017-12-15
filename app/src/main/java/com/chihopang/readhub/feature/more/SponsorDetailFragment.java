package com.chihopang.readhub.feature.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.Sponsor;
import com.facebook.drawee.view.SimpleDraweeView;
import org.parceler.Parcels;

public class SponsorDetailFragment extends DialogFragment {
  public static final String TAG = "SponsorDetailFragment";

  @BindView(R.id.txt_sponsor_slogan) TextView mTxtSlogan;
  @BindView(R.id.img_sponsor) SimpleDraweeView mImgSponsor;

  private Sponsor mSponsor;

  public static SponsorDetailFragment newInstance(Sponsor sponsor) {
    SponsorDetailFragment fragment = new SponsorDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Navigator.EXTRA_SPONSOR, Parcels.wrap(sponsor));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sponsor_detail, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mSponsor = Parcels.unwrap(getArguments().getParcelable(Navigator.EXTRA_SPONSOR));
    setupView();
  }

  private void setupView() {
    mTxtSlogan.setText(mSponsor.getSlogan());
    mImgSponsor.setImageURI(mSponsor.getImgUrl());
  }

  @OnClick({R.id.img_back, R.id.img_go}) void onViewClick(View v) {
    switch (v.getId()) {
      case R.id.img_back:
        dismiss();
        break;
      case R.id.img_go:
        Uri uri = Uri.parse(mSponsor.getPageUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        break;
      default:
        break;
    }
  }
}
