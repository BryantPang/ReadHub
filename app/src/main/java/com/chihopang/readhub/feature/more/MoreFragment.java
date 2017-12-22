package com.chihopang.readhub.feature.more;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Navigator;
import com.chihopang.readhub.base.BaseAdapter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Sponsor;
import java.io.IOException;
import me.yokeyword.fragmentation.SupportFragment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MoreFragment extends SupportFragment {
  public static final String TAG = "MoreFragment";

  public static MoreFragment newInstance() {
    return new MoreFragment();
  }

  @BindView(R.id.recycler_view_sponsors) RecyclerView mRecyclerSponsors;
  @BindView(R.id.scroll_view) NestedScrollView mScrollView;
  @BindView(R.id.txt_sponsor_title) TextView mTxtSponsorTitle;

  private LinearLayoutManager mLayoutManager;
  private BaseAdapter<Sponsor> mAdapter = new BaseAdapter<Sponsor>() {
    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new SponsorViewHolder(getActivity(), parent);
    }
  };


  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);
    ButterKnife.bind(this, v);
    initRecycler();
    return v;
  }

  private void initRecycler() {
    mRecyclerSponsors.setAdapter(mAdapter);
    mLayoutManager = new GridLayoutManager(getContext(), 2);
    mRecyclerSponsors.setLayoutManager(mLayoutManager);
    mRecyclerSponsors.setNestedScrollingEnabled(false);
    mTxtSponsorTitle.setVisibility(mAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    if (mAdapter.getItemCount() != 0) return;
    new AsyncTask<Void, Void, Document>() {
      @Override protected Document doInBackground(Void... params) {
        Document document = null;
        try {
          document = Jsoup.connect(Navigator.READHUB_PAGE_URL).get();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return document;
      }

      @Override protected void onPostExecute(Document document) {
        if (document == null) return;
        Elements adsContainer3Oeii = document.getElementsByClass("adsContainer___3Oeii");
        if (adsContainer3Oeii == null || adsContainer3Oeii.isEmpty()) return;
        Elements ncClearfix = adsContainer3Oeii.get(0).getElementsByClass("nc_clearfix");
        if (ncClearfix == null || ncClearfix.select("a") == null) return;
        for (Element sponsorElement : ncClearfix.select("a")) {
          Sponsor sponsor = new Sponsor();
          sponsor.setPageUrl(sponsorElement.attr("href"));
          sponsor.setImgUrl(sponsorElement.select("img").attr("src"));
          sponsor.setSlogan(sponsorElement.select("img").attr("alt"));
          mAdapter.addItem(sponsor);
        }
        mTxtSponsorTitle.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
      }
    }.execute();
  }

  @OnClick(R.id.relative_go_personal_page) void goPersonalPage() {
    ((MainActivity) getContext()).findFragment(MainFragment.class)
        .start(WebViewFragment.newInstance(Navigator.PERSONAL_PAGE_URL));
  }

  @OnClick(R.id.relative_go_readhub_page) void goReadhubPage() {
    ((MainActivity) getContext()).findFragment(MainFragment.class)
        .start(WebViewFragment.newInstance(Navigator.READHUB_PAGE_URL));
  }

  public void onTabClick() {
    mScrollView.smoothScrollTo(0, 0);
  }
}
