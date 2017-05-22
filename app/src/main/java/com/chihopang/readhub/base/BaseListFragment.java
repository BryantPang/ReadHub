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
import com.chihopang.readhub.feature.main.MainActivity;
import java.util.List;

public abstract class BaseListFragment<T> extends Fragment {
  private RecyclerView mRecyclerVIew;
  private BaseAdapter<T> mAdapter = new BaseAdapter<T>() {
    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return provideViewHolder(parent, viewType);
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
    ((MainActivity) getActivity()).mBox.showLoadingLayout();
    requestData();
    return view;
  }

  protected abstract void requestData();

  public void onSuccess(final List<T> itemList) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        mAdapter.addItems(itemList);
        ((MainActivity) getActivity()).mBox.hideAll();
      }
    });
  }

  public BaseAdapter getAdapter() {
    return mAdapter;
  }

  public abstract BaseViewHolder provideViewHolder(ViewGroup parent, int viewType);
}
