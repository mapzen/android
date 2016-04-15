package com.mapzen.android.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents polyline to be drawn on a map by {@link com.mapzen.tangram.MapController}
 */
public class Polyline {

    private final List<LatLng> coordinates;

    public Polyline(List<LatLng> coordinates) {
        this.coordinates = coordinates;}

    public static class Builder {
        private List<LatLng> coordinates = new ArrayList<>();

        public Builder() {
        }

        public Builder add(LatLng c) {
            coordinates.add(c);
            return this;
        }

        public Polyline build() {
            return new Polyline(coordinates);
        }
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polyline polyline = (Polyline) o;

        return !(coordinates != null ? !coordinates.equals(polyline.coordinates)
                : polyline.coordinates != null);
    }

    @Override public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}