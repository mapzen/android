package com.mapzen.android.model;

/**
 * Represents a coordinate pair.
 */
public class LatLng {

    private final double latitude;
    private final double longitude;

    /**
     * Creates a coordinate pair object.
     * @param latitude
     * @param longitude
     */
    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
