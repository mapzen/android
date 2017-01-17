package com.mapzen.places.api.sample;

import android.support.v7.app.AppCompatActivity;

/**
 * Data object for representing an SDK demo.
 */
public class SampleDetails {

  private final int titleId;

  private final int detailId;

  private final Class<? extends AppCompatActivity> activityClass;

  /**
   * Create object to represent a demoable component of the sdk.
   *
   * @param titleId title of demo
   * @param detailId details about demo
   * @param activityClass activity to launch when this list item is clicked
   */
  public SampleDetails(int titleId, int detailId, Class activityClass) {
    this.titleId = titleId;
    this.detailId = detailId;
    this.activityClass = activityClass;
  }

  /**
   * Resource id for title string.
   */
  public int getTitleId() {
    return titleId;
  }

  /**
   * Resource id for detail string.
   */
  public int getDetailId() {
    return detailId;
  }

  /**
   * Activity class to launch when this demo item is selected from list.
   */
  public Class<? extends AppCompatActivity> getActivityClass() {
    return activityClass;
  }
}
