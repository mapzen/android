package com.mapzen.android.graphics;


import com.mapzen.tangram.LabelPickResult;

public interface LabelPickListener {

    /**
     * Receive information about labels found in a call to {@link
     * com.mapzen.tangram.MapController#pickLabel(float, float)}.
     *
     * @param result
     * @param positionX The horizontal screen coordinate of the center of the feature
     * @param positionY The vertical screen coordinate of the center of the feature
     */
    void onLabelPicked(LabelPickResult result, float positionX, float positionY);
}
