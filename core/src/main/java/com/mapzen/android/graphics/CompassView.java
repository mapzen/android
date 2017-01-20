package com.mapzen.android.graphics;

import com.mapzen.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Compass button for MapView.
 */
public class CompassView extends RelativeLayout {

  /**
   * Durations in milliseconds for rotation and fade out animations.
   */
  static final int ROTATION_ANIMATION_DURATION_MILLIS = 1000;
  static final int FADE_OUT_ANIMATION_DURATION_MILLIS = 1000;

  /**
   * Create a new {@link CompassView} object for showing current rotation of map.
   */
  public CompassView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (inflater != null) {
      inflater.inflate(getLayoutId(), this, true);
    }
  }

  @Override public void setRotation(float rotation) {
    final ImageView image = (ImageView) findViewById(getImageId());
    if (image != null) {
      image.setRotation(rotation);
      if (image.getAlpha() == 0f) {
        image.setAlpha(1f);
      }
    }
  }

  /**
   * Resets compass to point to North.
   */
  public void reset() {
    final ImageView image = (ImageView) findViewById(getImageId());
    if (image != null) {
      float newRotation = (image.getRotation() < 180) ? 0 : 360;
      image.animate().setDuration(ROTATION_ANIMATION_DURATION_MILLIS).rotation(newRotation);
      animate().setDuration(FADE_OUT_ANIMATION_DURATION_MILLIS).alpha(0f)
        .setStartDelay(ROTATION_ANIMATION_DURATION_MILLIS);
    }
  }

  private int getLayoutId() {
    return R.layout.mz_view_compass;
  }

  private int getImageId() {
    return R.id.mz_compass_image;
  }
}
