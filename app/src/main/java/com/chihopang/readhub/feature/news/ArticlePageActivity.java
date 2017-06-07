package com.chihopang.readhub.feature.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import butterknife.BindView;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseActivity;
import com.chihopang.readhub.model.Topic;
import org.parceler.Parcels;

public class ArticlePageActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
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
    mToolbar.setTitle(mTopic.getTitle());
    setSupportActionBar(mToolbar);
    mToolbar.setNavigationIcon(R.drawable.ic_back);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    mWebView.loadUrl(mTopic.getUrl());
  }
}
