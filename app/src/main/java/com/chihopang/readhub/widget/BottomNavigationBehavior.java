package com.chihopang.readhub.widget;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
  public BottomNavigationBehavior() {
    super();
  }

  public BottomNavigationBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child,
      View dependency) {
    return dependency instanceof RecyclerView;
  }

  @Override
  public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
      BottomNavigationView child, View directTargetChild, View target, int nestedScrollAxes) {
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override
  public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child,
      View target, int dx, int dy, int[] consumed) {
    if (dy < 0) {
      showBottomNavigationView(child);
    } else if (dy > 0) {
      hideBottomNavigationView(child);
    }
  }

  private void hideBottomNavigationView(BottomNavigationView view) {
    view.animate().translationY(view.getHeight());
  }

  private void showBottomNavigationView(BottomNavigationView view) {
    view.animate().translationY(0);
  }
}
