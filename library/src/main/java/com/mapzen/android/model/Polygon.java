package com.mapzen.android.model;


import com.mapzen.tangram.LngLat;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon to be drawn on a map.
 */
public class Polygon {

    private final List<LngLat> coordinates;

    /**
     * Construct a new polygon.
     * @param coordinates
     */
    public Polygon(List<LngLat> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Polygon's builder class.
     */
    public static class Builder {
        private List<LngLat> coordinates = new ArrayList<>();

        /**
         * Construct a new builder.
         */
        public Builder() {
        }

        /**
         * Add a new coordinate pair to the {@link Polygon}.
         * @param c
         * @return
         */
        public Builder add(LngLat c) {
            coordinates.add(c);
            return this;
        }

        /**
         * Builds the {@link Polygon} and configures it's properties.
         *
         * @return the configured {@link Polygon}
         */
        public Polygon build() {
            return new Polygon(coordinates);
        }
    }

    /**
     * Get the coordinate pair.
     * @return
     */
    public List<LngLat> getCoordinates() {
        return coordinates;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Polygon polygon = (Polygon) o;

        return !(coordinates != null ? !coordinates.equals(polygon.coordinates)
                : polygon.coordinates != null);
    }

    @Override public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
