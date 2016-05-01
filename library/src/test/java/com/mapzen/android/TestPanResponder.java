package com.mapzen.android;

import com.mapzen.tangram.TouchInput;

/**
 *
 */
public class TestPanResponder implements TouchInput.PanResponder {
    @Override public boolean onPan(float startX, float startY, float endX, float endY) {
        return false;
    }

    @Override public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
        return false;
    }
}
