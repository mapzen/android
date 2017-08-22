package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MarkerPickListener;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.MarkerOptions;
import com.mapzen.tangram.LngLat;

import android.view.View;
import android.widget.Toast;

/**
 * Custom marker demo.
 */
public class CustomMarkerActivity extends SwitchStyleActivity implements MarkerPickListener {

  private BitmapMarker bitmapMarker;

  int getLayoutId() {
    return R.layout.activity_custom_marker;
  }

  /**
   * Configure map position and zoom. Also setup buttons to add/rm custom marker.
   */
  void configureMap() {
    mapzenMap.setCompassButtonEnabled(true);
    mapzenMap.setPersistMapState(true);
    mapzenMap.setPosition(new LngLat(-73.985428, 40.748817));
    mapzenMap.setZoom(16);
    mapzenMap.setMarkerPickListener(this);

    findViewById(R.id.add_marker_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        final MarkerOptions markerOptions = new MarkerOptions()
            .position(new LngLat(-73.985428, 40.748817))
            .icon(R.drawable.mapzen);
        bitmapMarker = mapzenMap.addBitmapMarker(markerOptions);
      }
    });

    findViewById(R.id.remove_marker_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (bitmapMarker != null) {
          bitmapMarker.remove();
        }
      }
    });
  }

  @Override public void onMarkerPick(BitmapMarker marker) {
    Toast.makeText(this, marker.toString(), Toast.LENGTH_SHORT).show();
  }
}
