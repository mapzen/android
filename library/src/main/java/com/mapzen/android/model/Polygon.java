package com.mapzen.android.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Polygon
 */
public class Polygon {

    private final List<LatLng> coordinates;

    public Polygon(List<LatLng> coordinates) {
        this.coordinates = coordinates;}

    public static class Builder {
        private List<LatLng> coordinates = new ArrayList<>();

        public Builder() {
        }

        public Builder add(LatLng c) {
            coordinates.add(c);
            return this;
        }

        public Polygon build() {
            return new Polygon(coordinates);
        }
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygon = (Polygon) o;

        return !(coordinates != null ? !coordinates.equals(polygon.coordinates)
                : polygon.coordinates != null);
    }

    @Override public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
