package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestTapResponder implements TouchInput.TapResponder {

    @Override public boolean onSingleTapUp(float x, float y) {
        return false;
    }

    @Override public boolean onSingleTapConfirmed(float x, float y) {
        return false;
    }
}
