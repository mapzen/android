package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestScaleResponder implements TouchInput.ScaleResponder {
    @Override public boolean onScale(float x, float y, float scale, float velocity) {
        return false;
    }
}
