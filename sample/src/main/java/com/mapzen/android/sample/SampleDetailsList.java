package com.mapzen.android.sample;

/**
 * List of all available SDK demos.
 */
public final class SampleDetailsList {

    private SampleDetailsList() {
    }

    public static final SampleDetails[] DEMOS = {
            new SampleDetails(R.string.sample_map_demo_label,
                    R.string.sample_map_demo_description,
                    BasicMapzenActivity.class),
            new SampleDetails(R.string.marker_map_demo_label,
                    R.string.marker_map_demo_description,
                    MarkerMapzenActivity.class),
            new SampleDetails(R.string.polyline_map_demo_label,
                    R.string.polyline_map_demo_description,
                    PolylineMapzenActivity.class),
            new SampleDetails(R.string.polygon_map_demo_label,
                    R.string.polygon_map_demo_description,
                    PolygonMapzenActivity.class),
    };
}
