package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestRotateResponder implements TouchInput.RotateResponder {
    @Override public boolean onRotate(float x, float y, float rotation) {
        return false;
    }
}
