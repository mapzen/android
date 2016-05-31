package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestShoveResponder implements TouchInput.ShoveResponder {
  @Override public boolean onShove(float distance) {
    return false;
  }
}
