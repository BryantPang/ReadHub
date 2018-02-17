package com.chihopang.readhub.feature.more;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.chihopang.readhub.R;
import com.chihopang.readhub.app.Constant;
import com.chihopang.readhub.base.BaseAdapter;
import com.chihopang.readhub.base.BaseViewHolder;
import com.chihopang.readhub.feature.common.WebViewFragment;
import com.chihopang.readhub.feature.main.MainActivity;
import com.chihopang.readhub.feature.main.MainFragment;
import com.chihopang.readhub.model.Sponsor;
import com.tencent.bugly.beta.Beta;
import me.yokeyword.fragmentation.SupportFragment;

public class MoreFragment extends SupportFragment {
  public static final String TAG = "MoreFragment";

  public static MoreFragment newInstance() {
    return new MoreFragment();
  }

  @BindView(R.id.scroll_view) NestedScrollView mScrollView;
  private AlertDialog mIssueDialog;

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
    return v;
  }

  @OnClick(R.id.btn_go_personal_page) void goPersonalPage() {
    ((MainActivity) getContext()).findFragment(MainFragment.class)
        .start(WebViewFragment.newInstance(Constant.PERSONAL_PAGE_URL));
  }

  @OnClick(R.id.btn_go_readhub_page) void goReadhubPage() {
    ((MainActivity) getContext()).findFragment(MainFragment.class)
        .start(WebViewFragment.newInstance(Constant.READHUB_PAGE_URL));
  }

  @OnClick(R.id.btn_check_update) void checkUpdate() {
    Beta.checkUpgrade();
  }

  @OnClick(R.id.btn_submit_issue) void submitIssue() {
    if (mIssueDialog == null) {
      mIssueDialog = new AlertDialog.Builder(getContext())
          .setTitle(R.string.dialog_submit_issue_message)
          .setItems(new String[] {"1.前往 Github 项目主页提交 issue", "2.发送至开发者个人邮箱"},
              new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  switch (which) {
                    case 0:
                      Intent intent = new Intent(Intent.ACTION_VIEW);
                      intent.setData(Uri.parse(Constant.GITHUB_PAGE_URL));
                      startActivity(Intent.createChooser(intent, getString(R.string.submit_issue)));
                      break;
                    case 1:
                      ClipboardManager cmb =
                          (ClipboardManager) getContext().getSystemService(
                              Context.CLIPBOARD_SERVICE);
                      cmb.setPrimaryClip(
                          ClipData.newPlainText("simple text", Constant.PERSONAL_EMAIL));
                      Toast.makeText(getContext(), R.string.copy_email_success, Toast.LENGTH_LONG)
                          .show();
                      break;
                    default:
                      break;
                  }
                }
              })
          .create();
    }
    mIssueDialog.show();
  }

  public void onTabClick() {
    mScrollView.smoothScrollTo(0, 0);
  }
}
