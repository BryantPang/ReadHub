package com.chihopang.readhub.feature.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.model.Topic;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import org.parceler.Parcels;

public class ArticlePageFragment extends SwipeBackFragment {
  @BindView(R.id.txt_toolbar_title) TextView mTxtTitle;
  @BindView(R.id.progress_bar_loading_web) ProgressBar mProgressBar;
  @BindView(R.id.web_view_article_page) WebView mWebView;
  private Topic mTopic;

  public static ArticlePageFragment newInstance(Topic topic) {
    ArticlePageFragment fragment = new ArticlePageFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Navigator.EXTRA_TOPIC, Parcels.wrap(topic));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_article_page, container, false);
    ButterKnife.bind(this, view);
    mTopic = Parcels.unwrap(getArguments().getParcelable(Navigator.EXTRA_TOPIC));
    return attachToSwipeBack(view);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initContent();
  }

  private void initContent() {
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
        mTxtTitle.setText(title);
      }
    });
    WebSettings mWebSetting = mWebView.getSettings();//TODO 设定支持js
    mWebView.loadUrl(mTopic.getUrl());
  }
}
