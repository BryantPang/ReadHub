package com.chihopang.readhub.feature.more;

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
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
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
    bundle.putParcelable(Constant.EXTRA_SPONSOR, Parcels.wrap(sponsor));
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
    mSponsor = Parcels.unwrap(getArguments().getParcelable(Constant.EXTRA_SPONSOR));
    setupView();
  }

  private void setupView() {
    mTxtSlogan.setText(mSponsor.getSlogan());
    mImgSponsor.setImageURI(mSponsor.getImgUrl());
  }

  @OnClick({R.id.txt_cancel, R.id.txt_go}) void onViewClick(View v) {
    switch (v.getId()) {
      case R.id.txt_cancel:
        dismiss();
        break;
      case R.id.txt_go:
        dismiss();
        ((MainActivity) getContext()).findFragment(MainFragment.class)
            .start(WebViewFragment.newInstance(mSponsor.getPageUrl()));
        break;
      default:
        break;
    }
  }
}
