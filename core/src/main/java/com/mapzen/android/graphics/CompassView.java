package com.mapzen.android.graphics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mapzen.R;

/**
 * Compass button for MapView.
 */
public class CompassView extends RelativeLayout {

<<<<<<< HEAD
<<<<<<< HEAD
  /**
   * Durations in milliseconds for rotation and fade out animations.
   */
  static final int ROTATION_ANIMATION_DURATION_MILLIS = 1000;
  static final int FADE_OUT_ANIMATION_DURATION_MILLIS = 1000;

  /**
   * Create a new {@link CompassView} object for showing current rotation of map.
   */
<<<<<<< HEAD
=======
  static final int ROTATION_ANIMATION_DURATION_MILLIS = 1000;
  static final int FADE_OUT_ANIMATION_DURATION_MILLIS = 1000;

>>>>>>> 1dd12b3... Constructor and static final fields of CompassView class change to package private.
=======
  /**
   * Durations in milliseconds for rotation and fade out animations.
   */
  static final int ROTATION_ANIMATION_DURATION_MILLIS = 1000;
  static final int FADE_OUT_ANIMATION_DURATION_MILLIS = 1000;

  /**
   * Create a new {@link CompassView} object for showing current rotation of map.
   */
>>>>>>> e74c036... Add javadoc to for constructor and static final fields of CompassView class.
  CompassView(Context context, AttributeSet attrs) {
=======
  public CompassView(Context context, AttributeSet attrs) {
>>>>>>> 445a608... Change CompassView constructor to public.
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
