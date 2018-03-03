package com.chihopang.readhub.feature.topic.instant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.app.ReadhubApplication;
import com.chihopang.readhub.base.BaseDialogFragment;
import com.chihopang.readhub.base.mvp.INetworkView;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.InstantReadData;
import java.io.IOException;
import java.io.InputStream;

public class InstantReadFragment extends BaseDialogFragment<InstantReadPresenter>
    implements INetworkView<InstantReadPresenter, InstantReadData> {
  public static final String TAG = "InstantReadFragment";

  @BindView(R.id.txt_topic_instant_title) TextView mTxtTopicTitle;
  @BindView(R.id.web_view) WebView mWebView;
  @BindView(R.id.txt_instant_source) TextView mTxtSource;
  @BindView(R.id.txt_origin_site) TextView mTxtGoOrigin;

  private String mTopicId;

  public static InstantReadFragment newInstance(String topicId) {
    InstantReadFragment fragment = new InstantReadFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Constant.BUNDLE_TOPIC_ID, topicId);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AlertDialogStyle);
  }

  @Override public int getFragmentLayout() {
    return R.layout.fragment_topic_instant_read;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    attachPresenter(new InstantReadPresenter());
    mTopicId = getArguments().getString(Constant.BUNDLE_TOPIC_ID);
    getPresenter().getInstantRead(mTopicId);
    initWebSettings();
  }

  @Override public void onSuccess(final InstantReadData data) {
    if (data == null) return;
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
        + "<link rel=\"stylesheet\" href=\"https://unpkg.com/mobi.css/dist/mobi.min.css\">"
        + "<style>"
        + "img{max-width:100% !important; width:auto; height:auto;}"
        + "body {font-size: 110%;word-spacing:110%；}"
        + "</style>"
        + "</head>";
    String htmlContent = "<html>"
        + htmlHead
        + "<body style:'height:auto;max-width: 100%; width:auto;'>"
        + data.getContent()
        + "</body></html>";
    mWebView.loadData(htmlContent, "text/html; charset=UTF-8", null);
  }

  @Override public void onError(Throwable e) {
    Context context = getContext();
    if (context == null) return;
    Toast.makeText(context, "请求错误", Toast.LENGTH_LONG).show();
    dismiss();
  }

  private void initWebSettings() {
    WebSettings mWebSetting = mWebView.getSettings();
    mWebSetting.setJavaScriptEnabled(true);
    mWebSetting.setUseWideViewPort(true);
    mWebSetting.setLoadWithOverviewMode(true);
    mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
    mWebSetting.setLoadsImagesAutomatically(true);

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

      @Override
      public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.endsWith("mobi.min.css")) {
          //使用本地 css 优化阅读视图
          WebResourceResponse resourceResponse = null;
          try {
            InputStream in = ReadhubApplication.getContext().getAssets().open("css/mobi.css");
            resourceResponse = new WebResourceResponse("text/css", "UTF-8", in);
          } catch (IOException e) {
            e.printStackTrace();
          }
          if (resourceResponse != null) {
            return resourceResponse;
          }
        }
        return super.shouldInterceptRequest(view, url);
      }
    });
  }

  @OnClick(R.id.img_back) void onCloseClick() {
    dismiss();
  }
}
