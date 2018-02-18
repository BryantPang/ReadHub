package com.chihopang.readhub.feature.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import org.parceler.Parcels;

public class WebViewFragment extends SwipeBackFragment implements Toolbar.OnMenuItemClickListener {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.txt_toolbar_title) TextView mTxtTitle;
  @BindView(R.id.progress_bar_loading_web) ProgressBar mProgressBar;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.web_view_article_page) WebView mWebView;
  private Topic mTopic;
  private String mUrl;
  private static final String APP_CACAHE_DIRNAME = "/webcache";

  public static WebViewFragment newInstance(Topic topic) {
    WebViewFragment fragment = new WebViewFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Constant.EXTRA_TOPIC, Parcels.wrap(topic));
    fragment.setArguments(bundle);
    return fragment;
  }

  public static WebViewFragment newInstance(String url) {
    WebViewFragment fragment = new WebViewFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Constant.EXTRA_URL, url);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_article_page, container, false);
    ButterKnife.bind(this, view);
    mTopic = Parcels.unwrap(getArguments().getParcelable(Constant.EXTRA_TOPIC));
    mUrl = getArguments().getString(Constant.EXTRA_URL);
    return attachToSwipeBack(view);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    initWebView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mWebView == null) return;
    mWebView.stopLoading();
    mWebView.clearHistory();
    mWebView.destroy();
    mWebView = null;
  }

  private void initView() {
    mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"), Color.BLACK, Color.BLUE);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.reload();
      }
    });

    //设置导航图标
    mToolbar.setNavigationIcon(R.drawable.ic_toolbar_close);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pop();
      }
    });
    //设置菜单
    mToolbar.inflateMenu(R.menu.menu_webview_page);
    mToolbar.setOnMenuItemClickListener(this);
  }

  private void initWebView() {
    initWebSettings();
    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (URLUtil.isNetworkUrl(url)) return super.shouldOverrideUrlLoading(view, url);
        return true;
      }
    });
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
        //更新进度
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
          mProgressBar.setVisibility(View.GONE);
          mSwipeRefreshLayout.setRefreshing(false);
          return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(newProgress);
      }

      @Override public void onReceivedTitle(WebView view, String title) {
        //更新标题
        super.onReceivedTitle(view, title);
        mTxtTitle.setText(title);
      }
    });
    mWebView.loadUrl(TextUtils.isEmpty(mUrl) ? mTopic.getUrl() : mUrl);
    mSwipeRefreshLayout.setRefreshing(true);
  }

  private void initWebSettings() {
    WebSettings mWebSetting = mWebView.getSettings();
    if (mWebSetting == null) return;
    mWebSetting.setJavaScriptEnabled(true);
    mWebSetting.setUseWideViewPort(true);
    mWebSetting.setLoadWithOverviewMode(true);
    mWebSetting.setDomStorageEnabled(true);
    mWebSetting.setDatabaseEnabled(true);
    mWebSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
    String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
    mWebSetting.setDatabasePath(cacheDirPath);
    mWebSetting.setAppCachePath(cacheDirPath);
    mWebSetting.setAppCacheEnabled(true);
    mWebSetting.setSupportZoom(true);
    mWebSetting.setBuiltInZoomControls(true);
    mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    mWebSetting.supportMultipleWindows();
    mWebSetting.setNeedInitialFocus(true);
    mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
    mWebSetting.setLoadsImagesAutomatically(true);
    mWebSetting.setDefaultTextEncodingName("utf-8");
  }

  @Override public boolean onBackPressedSupport() {
    if (mWebView != null && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return super.onBackPressedSupport();
  }

  @Override public boolean onMenuItemClick(MenuItem item) {
    String shareUrl;
    if (mWebView != null) {
      shareUrl = mWebView.getUrl();
    } else {
      shareUrl = mTopic == null ? mUrl : mTopic.getUrl();
    }
    switch (item.getItemId()) {
      case R.id.menu_item_open_by_browser:
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(shareUrl));
        startActivity(Intent.createChooser(intent, getString(R.string.open_by_browser)));
        return true;
      case R.id.menu_item_copy_link:
        ClipboardManager cmb =
            (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText("simple text", shareUrl));
        Toast.makeText(getContext(), R.string.copy_success, Toast.LENGTH_SHORT).show();
        return true;
      case R.id.menu_item_share:
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, shareUrl);
        startActivity(Intent.createChooser(share, getString(R.string.share)));
        return true;
    }
    return false;
  }
}
