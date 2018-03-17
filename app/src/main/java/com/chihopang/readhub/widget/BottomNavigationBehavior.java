package com.chihopang.readhub.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
  private ObjectAnimator outAnimator, inAnimator;
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

  @Override public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
      @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target,
      int axes, int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
      @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy,
      @NonNull int[] consumed, int type) {
    if (target instanceof NestedScrollView) return;
    if (dy > 0) {// 上滑隐藏
      hideBottomNavigationView(child);
    } else if (dy < 0) {// 下滑显示
      showBottomNavigationView(child);
    }
  }

  private void hideBottomNavigationView(BottomNavigationView view) {
    if (outAnimator == null) {
      outAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
      outAnimator.setDuration(200);
    }
    if (!outAnimator.isRunning() && view.getTranslationY() <= 0) {
      outAnimator.start();
    }
  }

  private void showBottomNavigationView(BottomNavigationView view) {
    if (inAnimator == null) {
      inAnimator = ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0);
      inAnimator.setDuration(200);
    }
    if (!inAnimator.isRunning() && view.getTranslationY() >= view.getHeight()) {
      inAnimator.start();
    }
  }
}
