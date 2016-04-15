package com.mapzen.android.model;


/**
 * Represents a pin marker on a map.
 */
public class Marker {

    private final LatLng latLng;

    /**
     * Constructs a new object.
     * @param latitude
     * @param longitude
     */
    public Marker(double latitude, double longitude) {
        this.latLng = new LatLng(latitude, longitude);
    }

    /**
     * Return the marker's coordinate location.
     * @return
     */
    public LatLng getLocation() {
        return latLng;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Marker marker = (Marker) o;

        return !(latLng != null ? !latLng.equals(marker.latLng) : marker.latLng != null);
    }

    @Override public int hashCode() {
        return latLng != null ? latLng.hashCode() : 0;
    }
}
