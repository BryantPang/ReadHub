package com.chihopang.readhub.feature.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseAdapter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.base.mvp.INetworkView;
import com.chihopang.readhub.base.mvp.IPresenter;
import com.chihopang.readhub.model.Sponsor;
import java.util.List;
import me.yokeyword.fragmentation.SupportFragment;

public class MoreFragment extends SupportFragment implements INetworkView {
  public static final String TAG = "MoreFragment";

  public static MoreFragment newInstance() {
    return new MoreFragment();
  }

  @BindView(R.id.recycler_view_sponsors) RecyclerView mRecyclerSponsors;
  private LinearLayoutManager mLayoutManager;
  private BaseAdapter<Sponsor> mAdapter = new BaseAdapter<Sponsor>() {
    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new SponsorViewHolder(getActivity(), parent);
    }
  };

  private INetworkPresenter mPresenter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);
    ButterKnife.bind(this, v);
    initRecycler();
    mPresenter = new MorePresenter(this);
    mPresenter.start();
    return v;
  }

  private void initRecycler() {
    mRecyclerSponsors.setAdapter(mAdapter);
    mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    mRecyclerSponsors.setLayoutManager(mLayoutManager);
  }

  @Override public IPresenter getPresenter() {
    return mPresenter;
  }

  @Override public void onSuccess(Object t) {
    List<Sponsor> sponsors = (List<Sponsor>) t;
    mAdapter.addItems(sponsors);
    mAdapter.notifyDataSetChanged();
  }

  @Override public void onError(Exception e) {
    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
  }
}
