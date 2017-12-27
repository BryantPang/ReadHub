package com.chihopang.readhub.feature.topic.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.TopicTimeLine;

public class TopicTimeLineViewHolder extends BaseViewHolder<TopicTimeLine> {
  @BindView(R.id.txt_date) TextView mTxtDate;
  @BindView(R.id.txt_time_line_content) TextView mTxtContent;
  @BindView(R.id.view_top_line) View mDividerTop;
  @BindView(R.id.view_bottom_line) View mDividerBottom;

  private TopicTimeLine mTimeLine;

  public TopicTimeLineViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_time_line);
  }

  @Override public void bindTo(TopicTimeLine value) {
    mTimeLine = value;
    mTxtDate.setText(value.date);
    mTxtContent.setText(value.content);
    mDividerTop.setVisibility(
        getItemViewType() == TopicDetailFragment.VIEW_TYPE_TOP ? View.INVISIBLE : View.VISIBLE);
    mDividerBottom.setVisibility(
        getItemViewType() == TopicDetailFragment.VIEW_TYPE_BOTTOM ? View.INVISIBLE : View.VISIBLE);
  }

  @OnClick(R.id.txt_time_line_content) void onClickContent(View view) {
    ((MainActivity) view.getContext()).findFragment(MainFragment.class)
        .start(TopicDetailFragment.newInstance(mTimeLine.url));
  }
}
