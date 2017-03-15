package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

/**
 * In-memory store used to persist data for currently selected place.
 */
public class PlaceStore {
  private static PlaceStore instance = new PlaceStore();

  /**
   * Return place store singleton instance.
   *
   * @return the place store instance.
   */
  public static PlaceStore instance() {
    return instance;
  }

  private PlaceStore() {
  }

  private Place currentSelectedPlace;

  public Place getCurrentSelectedPlace() {
    return currentSelectedPlace;
  }

  public void setCurrentSelectedPlace(Place currentSelectedPlace) {
    this.currentSelectedPlace = currentSelectedPlace;
  }
}
