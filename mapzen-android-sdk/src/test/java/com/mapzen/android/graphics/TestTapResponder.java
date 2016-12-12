package com.mapzen.android.graphics;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestTapResponder implements TouchInput.TapResponder {

  boolean singleTapUp = false;

  boolean singleTapConfirmed = false;

  @Override public boolean onSingleTapUp(float x, float y) {
    singleTapUp = true;
    return false;
  }

  @Override public boolean onSingleTapConfirmed(float x, float y) {
    singleTapConfirmed = true;
    return false;
  }
}
