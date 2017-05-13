package com.chihopang.readhub.feature.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.OnItemClickListener;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.model.ApiData;
import com.chihopang.readhub.model.Topic;

public class HotTopicFragment extends Fragment {
  public static final String TAG = "HotTopicFragment";

  public static HotTopicFragment newInstance() {
    return new HotTopicFragment();
  }

  private RecyclerView mRecyclerView;
  private HotTopicAdapter mAdapter;
  private LinearLayoutManager mManager = new LinearLayoutManager(getActivity());

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_hot_topic, container, false);
    mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
    initRecycler();
    HotTopicPresenter.getData(this);
    ((MainActivity) getActivity()).mBox.showLoadingLayout();
    return v;
  }

  private void initRecycler() {
    mAdapter = new HotTopicAdapter();
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override public void onClick(Object value) {
        Toast.makeText(getActivity(), ((Topic) value).getTitle(), Toast.LENGTH_SHORT).show();
      }
    });
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setLayoutManager(mManager);
  }

  public void onSuccess(final ApiData data) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mAdapter.addItems(data.getData());
        mAdapter.notifyDataSetChanged();
        ((MainActivity) getActivity()).mBox.hideAll();
      }
    });
  }
}
