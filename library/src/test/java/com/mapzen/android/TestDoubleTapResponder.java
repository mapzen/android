package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 * Implementation of interface for use in {@code MapzenMapTest}
 */
public class TestDoubleTapResponder implements TouchInput.DoubleTapResponder {
    @Override public boolean onDoubleTap(float x, float y) {
        return false;
    }
}

