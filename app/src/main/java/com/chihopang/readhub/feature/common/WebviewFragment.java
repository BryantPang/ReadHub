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
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation.SupportFragment;
import org.parceler.Parcels;

public class WebviewFragment extends SupportFragment {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.txt_toolbar_title) TextView mTxtTitle;
  @BindView(R.id.progress_bar_loading_web) ProgressBar mProgressBar;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.web_view_article_page) WebView mWebView;
  private Topic mTopic;
  private String mUrl;
  private static final String APP_CACAHE_DIRNAME = "/webcache";

  public static WebviewFragment newInstance(Topic topic) {
    WebviewFragment fragment = new WebviewFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Navigator.EXTRA_TOPIC, Parcels.wrap(topic));
    fragment.setArguments(bundle);
    return fragment;
  }

  public static WebviewFragment newInstance(String url) {
    WebviewFragment fragment = new WebviewFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Navigator.EXTRA_URL, url);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_article_page, container, false);
    ButterKnife.bind(this, view);
    mTopic = Parcels.unwrap(getArguments().getParcelable(Navigator.EXTRA_TOPIC));
    mUrl = getArguments().getString(Navigator.EXTRA_URL);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initContent();
    mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#607D8B"), Color.BLACK, Color.BLUE);
    mToolbar.inflateMenu(R.menu.menu_webview_page);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(MenuItem item) {
        String shareUrl = "";
        if (mWebView != null) {
          shareUrl = mWebView.getUrl();
        } else {
          shareUrl = mTopic == null ? mUrl : mTopic.getUrl();
        }
        switch (item.getItemId()) {
          case R.id.menu_item_open_by_browser:
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(shareUrl));
            startActivity(intent);
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
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_format, shareUrl));
            startActivity(Intent.createChooser(share, getString(R.string.share)));
            return true;
        }
        return false;
      }
    });
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mWebView.stopLoading();
    mWebView.clearHistory();
    mWebView.destroy();
    mWebView = null;
  }

  private void initContent() {
    initWebSettings();
    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //防止跳转到浏览器
        //view.loadUrl(url);
        if (URLUtil.isNetworkUrl(url)) return super.shouldOverrideUrlLoading(view, url);
        return true;
      }
    });
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
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
        super.onReceivedTitle(view, title);
        mTxtTitle.setText(title);
      }
    });

    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.reload();
      }
    });
  }

  private void initWebSettings() {
    WebSettings mWebSetting = mWebView.getSettings();
    mWebSetting.setJavaScriptEnabled(true);
    mWebSetting.setUseWideViewPort(true);  //将图片调整到适合webview的大小
    mWebSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

    // 开启 DOM storage API 功能
    mWebSetting.setDomStorageEnabled(true);
    //开启 database storage API 功能
    mWebSetting.setDatabaseEnabled(true);
    ;
    //设置缓存类型
    mWebSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    //设置缓存位置
    String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
    //设置数据库缓存路径
    mWebSetting.setDatabasePath(cacheDirPath);
    //设置  Application Caches 缓存目录
    mWebSetting.setAppCachePath(cacheDirPath);
    //开启 Application Caches 功能
    mWebSetting.setAppCacheEnabled(true);
    mWebSetting.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
    mWebSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。
    //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
    mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
    mWebSetting.supportMultipleWindows();  //多窗口
    mWebSetting.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
    mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
    mWebSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
    mWebSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
    mWebView.loadUrl(TextUtils.isEmpty(mUrl) ? mTopic.getUrl() : mUrl);
    mSwipeRefreshLayout.setRefreshing(true);
  }

  @Override public boolean onBackPressedSupport() {
    if (mWebView != null && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return super.onBackPressedSupport();
  }

  @OnClick(R.id.img_back) void onCloseClick() {
    pop();
  }
}
