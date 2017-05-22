package com.chihopang.readhub.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chihopang.readhub.R;
import com.chihopang.readhub.feature.main.MainActivity;
import java.util.List;

public abstract class BaseListFragment<T> extends Fragment {
  private Context mContext;
  private RecyclerView mRecyclerView;
  private SwipeRefreshLayout mSwipeRefreshLayout;
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
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
    initContent();
    if (mContext instanceof MainActivity) {
      ((MainActivity) mContext).mBox.showLoadingLayout();
    }
    requestData();
    return view;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  protected abstract void requestData();

  public abstract BaseViewHolder<T> provideViewHolder(ViewGroup parent, int viewType);

  private void initContent() {
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setLayoutManager(mManager);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        requestData();
      }
    });
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mRecyclerView.getChildCount();
      }
    });
  }

  public void onSuccess(final List<T> itemList) {
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.clear();
        mAdapter.addItems(itemList);
        ((MainActivity) getActivity()).mBox.hideAll();
      }
    });
  }

  public BaseAdapter getAdapter() {
    return mAdapter;
  }
}
