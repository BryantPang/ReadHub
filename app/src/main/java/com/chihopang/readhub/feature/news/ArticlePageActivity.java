package com.chihopang.readhub.feature.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseActivity;
import com.chihopang.readhub.model.Topic;
import org.parceler.Parcels;

public class ArticlePageActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.progress_bar_loading_web) ProgressBar mProgressBar;
  @BindView(R.id.web_view_article_page) WebView mWebView;
  private Topic mTopic;

  public static void start(Activity startingActivity, Topic topic) {
    Intent i = new Intent(startingActivity, ArticlePageActivity.class);
    i.putExtra(Navigator.EXTRA_TOPIC, Parcels.wrap(Topic.class, topic));
    startingActivity.startActivity(i);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_page);
    mTopic = Parcels.unwrap(getIntent().getParcelableExtra(Navigator.EXTRA_TOPIC));
    initContent();
  }

  private void initContent() {
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_back);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    mToolbar.setTitle(mTopic.getTitle());
    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //防止跳转到浏览器
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
      }
    });
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
          mProgressBar.setVisibility(View.GONE);
          return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(newProgress);
      }

      @Override public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        mToolbar.setTitle(title);
      }
    });
    WebSettings mWebSetting = mWebView.getSettings();//TODO 设定支持js
    mWebView.loadUrl(mTopic.getUrl());
  }

  @Override public void onBackPressed() {
    if (mWebView.canGoBack()) {
      mWebView.goBack();
      return;
    }
    super.onBackPressed();
  }
}
