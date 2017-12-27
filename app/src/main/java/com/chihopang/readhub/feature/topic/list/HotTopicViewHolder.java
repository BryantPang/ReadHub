package com.chihopang.readhub.feature.topic.list;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.feature.topic.detail.TopicDetailFragment;
import com.chihopang.readhub.feature.topic.instant.InstantReadFragment;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation.SupportActivity;

public class HotTopicViewHolder extends BaseViewHolder<Topic> {
  @BindView(R.id.txt_topic_title) TextView mTxtTitle;
  @BindView(R.id.txt_topic_summary) TextView mTxtSummary;
  @BindView(R.id.txt_instant_read) TextView mTxtInstantRead;
  @BindView(R.id.divider_instant_read) View mDividerInstantRead;
  @BindView(R.id.frame_instant_read) FrameLayout mFrameInstantRead;

  private Topic mTopic;

  public HotTopicViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.list_item_topic);
  }

  @Override public void bindTo(final Topic value) {
    mTopic = value;
    SpannableString spannableString =
        new SpannableString(value.getTitle() + "  " + value.getPublishDateCountDown());
    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#AAACB4")),
        value.getTitle().length() + 2,
        value.getTitle().length() + value.getPublishDateCountDown().length() + 2,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new RelativeSizeSpan(0.8f), value.getTitle().length() + 2,
        value.getTitle().length() + value.getPublishDateCountDown().length() + 2,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    mTxtTitle.setText(spannableString);
    mTxtSummary.setText(value.getSummary());

    mTxtSummary.setVisibility(TextUtils.isEmpty(value.getSummary()) ? View.GONE : View.VISIBLE);
    mTxtInstantRead.setVisibility(mTopic.hasInstantView() ? View.VISIBLE : View.GONE);
    mDividerInstantRead.setVisibility(mTopic.hasInstantView() ? View.VISIBLE : View.GONE);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((MainActivity) v.getContext()).findFragment(MainFragment.class)
            .start(TopicDetailFragment.newInstance(value));
      }
    });

    mFrameInstantRead.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        InstantReadFragment.newInstance(value.getId())
            .show(((SupportActivity) v.getContext()).getSupportFragmentManager(),
                InstantReadFragment.TAG);
      }
    });
  }
}
