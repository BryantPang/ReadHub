package com.chihopang.readhub.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;
import java.util.List;

public abstract class BaseListFragment<T> extends Fragment {
  private RecyclerView mRecyclerVIew;
  private BaseAdapter<T> mAdapter = new BaseAdapter<T>() {
    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return provideViewHolder();
    }
  };
  private LinearLayoutManager mManager = new LinearLayoutManager(getActivity());

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_base_list, container, false);
    mRecyclerVIew = (RecyclerView) view.findViewById(R.id.recycler_view);
    mRecyclerVIew.setAdapter(mAdapter);
    mRecyclerVIew.setLayoutManager(mManager);
    requestData();
    return view;
  }

  protected abstract void requestData();

  protected abstract void onSuccess(List<T> itemList);

  public BaseAdapter getAdapter() {
    return mAdapter;
  }

  public abstract BaseViewHolder provideViewHolder();
}
