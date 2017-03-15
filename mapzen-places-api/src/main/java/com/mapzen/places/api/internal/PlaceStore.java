package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

public class PlaceStore {
  private static PlaceStore instance = new PlaceStore();

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
