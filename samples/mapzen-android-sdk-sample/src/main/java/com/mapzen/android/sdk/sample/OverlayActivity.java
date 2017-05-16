package com.mapzen.android.sdk.sample;

/**
 * Example activity for toggling transit, bike, and path overlays.
 */
public class OverlayActivity extends SwitchStyleActivity {

  @Override void configureMap() {
    super.configureMap();
    mapzenMap.setTransitOverlayEnabled(true);
  }
}
