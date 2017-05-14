package com.chihopang.readhub.feature.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;
import com.chihopang.readhub.model.Sponsor;
import java.util.List;

public class MoreFragment extends Fragment {
  public static final String TAG = "MoreFragment";

  public static MoreFragment newInstance() {
    return new MoreFragment();
  }

  private RecyclerView mRecyclerSponsors;
  private LinearLayoutManager mLayoutManager =
      new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
  private SponsorAdapter mAdapter = new SponsorAdapter();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);
    findView(v);
    initRecycler();
    MorePresenter.getSponsorData(this);
    return v;
  }

  private void findView(View v) {
    mRecyclerSponsors = (RecyclerView) v.findViewById(R.id.recycler_view_sponsors);
  }

  private void initRecycler() {
    mRecyclerSponsors.setAdapter(mAdapter);
    mRecyclerSponsors.setLayoutManager(mLayoutManager);
  }

  public void onSuccess(final List<Sponsor> sponsors) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mAdapter.addItems(sponsors);
        mAdapter.notifyDataSetChanged();
      }
    });

  }
}
