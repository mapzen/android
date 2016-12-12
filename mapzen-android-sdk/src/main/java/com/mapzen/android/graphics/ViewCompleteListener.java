package com.mapzen.android.graphics;

/**
 * Listener for when view is fully loaded and no ease or label animation running.
 */
public interface ViewCompleteListener {
  /**
   * Called on the render-thread at the end of whenever the view is fully loaded and
   * no ease- or label-animation is running.
   */
  public void onViewComplete();
}
