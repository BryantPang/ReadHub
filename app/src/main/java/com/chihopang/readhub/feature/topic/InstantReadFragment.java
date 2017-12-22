package com.chihopang.readhub.feature.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.mvp.INetworkView;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.InstantReadData;

public class InstantReadFragment extends DialogFragment implements INetworkView {
  public static final String TAG = "InstantReadFragment";

  @BindView(R.id.txt_topic_title) TextView mTxtTopicTitle;
  @BindView(R.id.web_view) WebView mWebView;
  @BindView(R.id.txt_instant_source) TextView mTxtSource;
  @BindView(R.id.txt_origin_site) TextView mTxtGoOrigin;

  private InstantReadPresenter mPresenter = new InstantReadPresenter(this);

  private String mTopicId;

  public static InstantReadFragment newInstance(String topicId) {
    InstantReadFragment fragment = new InstantReadFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Navigator.BUNDLE_TOPIC_ID, topicId);
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
    View view = inflater.inflate(R.layout.fragment_topic_instant_read, container, false);
    ButterKnife.bind(this, view);
    mTopicId = getArguments().getString(Navigator.BUNDLE_TOPIC_ID);
    getPresenter().getInstantRead(mTopicId);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initWebSettings();
  }

  @Override public InstantReadPresenter getPresenter() {
    return mPresenter;
  }

  @Override public void onSuccess(Object t) {
    if (!(t instanceof InstantReadData)) return;
    final InstantReadData data = (InstantReadData) t;
    mTxtTopicTitle.setText(data.getTitle());
    mTxtSource.setText(getString(R.string.source_fromat, data.getSiteName()));
    if (!TextUtils.isEmpty(data.getUrl())) {
      mTxtGoOrigin.setVisibility(View.VISIBLE);
      mTxtGoOrigin.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismiss();
          ((MainActivity) getContext()).findFragment(MainFragment.class)
              .start(WebViewFragment.newInstance(data.getUrl()));
        }
      });
    }
    String htmlHead = "<head>"
        +
        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
        +
        "<style>img{max-width:100% !important; width:auto; height:auto;}</style>"
        +
        "</head>";
    String htmlContent = "<html>"
        + htmlHead
        + "<body style:'height:auto;max-width: 100%; width:auto;'>"
        + data.getContent()
        + "</body></html>";
    mWebView.loadData(htmlContent, "text/html; charset=UTF-8", null);
  }

  @Override public void onError(Throwable e) {
    Toast.makeText(getContext(), "请求错误", Toast.LENGTH_LONG).show();
    dismiss();
  }

  private void initWebSettings() {
    WebSettings mWebSetting = mWebView.getSettings();
    mWebSetting.setJavaScriptEnabled(true);
    mWebSetting.setUseWideViewPort(true);  //将图片调整到适合webview的大小
    mWebSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING); //支持内容重新布局
    mWebSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片

    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (URLUtil.isNetworkUrl(url)) {
          dismiss();
          ((MainActivity) getContext()).findFragment(MainFragment.class)
              .start(WebViewFragment.newInstance(url));
        }
        return true;
      }
    });
  }

  @OnClick(R.id.img_back) void onCloseClick() {
    dismiss();
  }
}
