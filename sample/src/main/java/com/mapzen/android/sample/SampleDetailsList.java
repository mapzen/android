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
            new SampleDetails(R.string.switch_style_map_demo_label,
                    R.string.switch_style_map_demo_description,
                    SwitchStyleActivity.class),
            new SampleDetails(R.string.custom_style_map_demo_label,
                    R.string.custom_style_map_demo_description,
                    CustomStylesheetActivity.class),
            new SampleDetails(R.string.touch_responder_map_demo_label,
                    R.string.touch_responder_map_demo_description,
                    TouchResponderActivity.class),
            new SampleDetails(R.string.route_pins_map_demo_label,
                    R.string.route_pins_map_demo_description,
                    RoutePinsActivity.class),
            new SampleDetails(R.string.dropped_pin_map_demo_label,
                    R.string.dropped_pin_map_demo_description,
                    DroppedPinActivity.class),
            new SampleDetails(R.string.route_line_map_demo_label,
                    R.string.route_line_map_demo_description,
                    RouteLineActivity.class),
            new SampleDetails(R.string.search_results_map_demo_label,
                    R.string.search_results_map_demo_description,
                    SearchResultsActivity.class),
            new SampleDetails(R.string.feature_listener_map_demo_label,
                    R.string.feature_listener_map_demo_description,
                    FeaturePickListenerActivity.class),
            new SampleDetails(R.string.queue_event_map_demo_label,
                    R.string.queue_event_map_demo_description,
                    QueueEventActivity.class)

    };
}
