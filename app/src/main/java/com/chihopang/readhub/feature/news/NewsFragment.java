package com.chihopang.readhub.feature.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;
import com.chihopang.readhub.model.TopicData;

public class NewsFragment extends Fragment {
  public static final String TAG = "NewsFragment";

  public static NewsFragment newInstance() {
    return new NewsFragment();
  }

  private RecyclerView mRecyclerView;
  private LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
  private NewsAdapter mAdapter = new NewsAdapter();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_news, container, false);
    mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
    initRecycler();
    NewsPresenter.getData(this);
    return v;
  }

  private void initRecycler() {
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setAdapter(mAdapter);
  }

  public void onSuccess(final TopicData data) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mAdapter.addItems(data.getData());
        mAdapter.notifyDataSetChanged();
      }
    });
  }
}
