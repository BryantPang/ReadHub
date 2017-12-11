package com.chihopang.readhub.feature.main;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.chihopang.readhub.R;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    if (findFragment(MainFragment.class) == null) {
      loadRootFragment(R.id.frame_main, MainFragment.newInstance(), true, true);
    }
  }
}
