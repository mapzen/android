package com.mapzen.android.model;

import com.mapzen.tangram.LngLat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents polyline to be drawn on a map by {@link com.mapzen.tangram.MapController}.
 */
public class Polyline {

  private final List<LngLat> coordinates;

  /**
   * Constructs a new {@link Polyline} object.
   */
  public Polyline(List<LngLat> coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * {@link Polyline} builder class.
   */
  public static class Builder {
    private List<LngLat> coordinates = new ArrayList<>();

    /**
     * Constructs a new {@link Builder}.
     */
    public Builder() {
    }

    /**
     * Add a coordinate pair to the {@link Polyline}.
     */
    public Builder add(LngLat c) {
      coordinates.add(c);
      return this;
    }

    /**
     * Build the {@link Polyline} and configure it's values.
     *
     * @return the newly created {@link Polyline}
     */
    public Polyline build() {
      return new Polyline(coordinates);
    }
  }

  /**
   * The {@link Polyline}'s coordinates.
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

    Polyline polyline = (Polyline) o;

    return !(coordinates != null ? !coordinates.equals(polyline.coordinates)
        : polyline.coordinates != null);
  }

  @Override public int hashCode() {
    return coordinates != null ? coordinates.hashCode() : 0;
  }
}
