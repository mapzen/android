package com.mapzen.android.model;


import com.mapzen.tangram.LngLat;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon to be drawn on a map.
 */
public class Polygon extends Polyline {

    /**
     * Constructs a new {@link Polyline} object.
     */
    public Polygon(List<LngLat> coordinates) {
        super(coordinates);
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

}
