package com.chihopang.readhub.feature.topic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.feature.common.WebviewFragment;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation.SupportActivity;
import org.parceler.Parcels;

public class TopicDetailFragment extends DialogFragment {
  public static final String TAG = "TopicDetailFragment";

  @BindView(R.id.txt_topic_title) TextView mTxtTopicTitle;
  @BindView(R.id.txt_topic_time) TextView mTxtTopicTime;
  @BindView(R.id.txt_topic_description) TextView mTxtTopicDescription;
  @BindView(R.id.linear_web_title_container) LinearLayout mLinearTitleContainer;

  private Topic mTopic;

  public static TopicDetailFragment newInstance(Topic topic) {
    TopicDetailFragment fragment = new TopicDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Navigator.EXTRA_TOPIC, Parcels.wrap(topic));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);
    ButterKnife.bind(this, view);
    mTopic = Parcels.unwrap(getArguments().getParcelable(Navigator.EXTRA_TOPIC));
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupView();
  }

  private void setupView() {
    mTxtTopicTitle.setText(mTopic.getTitle());
    mTxtTopicTime.setText(mTopic.getFormatPublishDate());
    mTxtTopicDescription.setText(mTopic.getSummary());
    for (final Topic topic : mTopic.getNewsArray()) {
      TextView textView = new TextView(getContext());
      LinearLayout.LayoutParams params =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      params.setMargins(10, 16, 10, 16);
      textView.setLayoutParams(params);
      textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_data, 0, 0, 0);
      textView.setCompoundDrawablePadding(15);
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
      textView.setTextColor(Color.parseColor("#607D8B"));
      textView.setText(topic.getTitle());
      textView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismiss();
          ((SupportActivity) v.getContext()).findFragment(MainFragment.class)
              .start(WebviewFragment.newInstance(topic));
        }
      });
      mLinearTitleContainer.addView(textView);
    }
  }

  @OnClick(R.id.img_back) void onCloseClick() {
    dismiss();
  }
}
