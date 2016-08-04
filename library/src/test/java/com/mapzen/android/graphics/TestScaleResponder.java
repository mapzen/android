package com.mapzen.android.graphics;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestScaleResponder implements TouchInput.ScaleResponder {
  @Override public boolean onScale(float x, float y, float scale, float velocity) {
    return false;
  }
}
