package com.mapzen.android.graphics;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestShoveResponder implements TouchInput.ShoveResponder {
  @Override public boolean onShove(float distance) {
    return false;
  }
}
