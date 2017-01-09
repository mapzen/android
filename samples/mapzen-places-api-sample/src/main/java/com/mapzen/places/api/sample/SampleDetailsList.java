package com.mapzen.places.api.sample;

/**
 * List of all available SDK demos.
 */
public final class SampleDetailsList {

  private SampleDetailsList() {
  }

  public static final SampleDetails[] DEMOS = {
      new SampleDetails(R.string.sample_place_picker_label,
          R.string.sample_place_picker_description,
          PlacePickerDemoActivity.class),
      new SampleDetails(R.string.sample_autocomplete_label,
          R.string.sample_autocomplete_description,
          AutocompleteDemoActivity.class)
  };
}
