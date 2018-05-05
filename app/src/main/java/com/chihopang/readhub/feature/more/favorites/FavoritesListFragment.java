package com.chihopang.readhub.feature.more.favorites;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.base.BaseAdapter;
import com.chihopang.readhub.base.BaseFragment;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.database.DatabaseUtil;
import com.chihopang.readhub.feature.news.NewsViewHolder;
import com.chihopang.readhub.feature.topic.list.HotTopicViewHolder;
import com.chihopang.readhub.model.News;
import com.chihopang.readhub.model.Topic;
import com.chihopang.readhub.widget.MyDynamicBox;
import io.reactivex.functions.Consumer;
import java.util.List;
import me.yokeyword.fragmentation.SupportActivity;

public class FavoritesListFragment extends BaseFragment {
  public static final int TYPE_TOPIC = 1;
  public static final int TYPE_NEWS = 2;

  public static FavoritesListFragment newInstance(int type) {
    FavoritesListFragment fragment = new FavoritesListFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Constant.BUNDLE_TYPES, type);
    fragment.setArguments(bundle);
    return fragment;
  }

  private SupportActivity mActivity;
  private int type = -1;
  public MyDynamicBox mBox;
  @BindView(R.id.fab) FloatingActionButton mFAB;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.frame_list_container) FrameLayout mFrameContainer;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
  private LinearLayoutManager mManager;
  private BaseAdapter mAdapter = new BaseAdapter() {
    @Override public int getItemViewType(int position) {
      if (type != -1) return type;
      return super.getItemViewType(position);
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if (type == TYPE_TOPIC) return new HotTopicViewHolder(parent.getContext(), parent);
      return new NewsViewHolder(parent.getContext(), parent);
    }
  };

  @Override public int getFragmentLayout() {
    return R.layout.fragment_base_list;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mActivity = (SupportActivity) context;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    type = getArguments().getInt(Constant.BUNDLE_TYPES);
    initContent();
    requestData();
  }

  private void initContent() {
    mManager = new LinearLayoutManager(getContext());
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setLayoutManager(mManager);
    mFAB.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mManager.smoothScrollToPosition(mRecyclerView, null, 0);
      }
    });
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mFAB.setVisibility(
            (mManager.findFirstVisibleItemPosition() > 0) ? View.VISIBLE : View.GONE);
      }
    });
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mAdapter.clear();
        requestData();
      }
    });
    mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"), Color.BLACK, Color.BLUE);
    mBox = new MyDynamicBox(mActivity, mFrameContainer);
    mBox.setClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //加载失败时的按钮点击事件
        mAdapter.clear();
        requestData();
      }
    });
  }

  private void requestData() {
    mBox.showLoadingLayout();
    if (type == TYPE_TOPIC) {
      DatabaseUtil.getAll(Topic.class, mActivity, new Consumer<List<Topic>>() {
        @Override public void accept(List<Topic> topics) throws Exception {
          onSuccess(topics);
        }
      });
    } else {
      DatabaseUtil.getAll(News.class, mActivity, new Consumer<List<News>>() {
        @Override public void accept(List<News> news) throws Exception {
          onSuccess(news);
        }
      });
    }
  }

  @SuppressWarnings("unchecked") private void onSuccess(List list) {
    if (list == null || list.isEmpty()) {
      mBox.showEmptyView();
      return;
    }
    mSwipeRefreshLayout.setRefreshing(false);
    mBox.hideAll();
    mAdapter.addItems(list);
  }
}
