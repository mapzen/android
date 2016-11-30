package com.mapzen.android.graphics;

import com.mapzen.tangram.LabelPickResult;
import com.mapzen.tangram.MapController;

/**
 * Takes care of calling an external label listener for a
 * {@link com.mapzen.android.graphics.MapzenMap}.
 */
public class LabelPickHandler implements MapController.LabelPickListener {

    private final MapView mapView;
    private LabelPickListener labelPickListener;

  /**
   * Construct a new handler and set it's {@link MapView}.
   * @param mapView
   */
  public LabelPickHandler(MapView mapView) {
        this.mapView = mapView;
    }

    public void setListener(LabelPickListener listener) {
        this.labelPickListener = listener;
    }

    @Override
    public void onLabelPick(LabelPickResult labelPickResult, float positionX, float positionY) {
        if (labelPickResult != null) {
            postLabelPickRunnable(labelPickResult, positionX, positionY, labelPickListener);
        }
    }

    private void postLabelPickRunnable(final LabelPickResult result, final float positionX,
                                       final float positionY, final LabelPickListener listener) {
        mapView.post(new Runnable() {
            @Override public void run() {
                listener.onLabelPicked(result, positionX, positionY);
            }
        });
    }
}
