package com.mapzen.android.graphics.model;

import com.mapzen.tangram.LngLat;

import android.support.annotation.NonNull;

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
     */
    @NonNull public Builder add(@NonNull LngLat c) {
      coordinates.add(c);
      return this;
    }

    /**
     * Builds the {@link Polygon} and configures it's properties.
     *
     * @return the configured {@link Polygon}
     */
    @NonNull public Polygon build() {
      return new Polygon(coordinates);
    }
  }
}
