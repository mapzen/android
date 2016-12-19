package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Implementation of {@link PlacePickerPresenter}.
 */
public class PlacePickerPresenterImpl implements PlacePickerPresenter {

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_NAME = "name";

  PlacePickerViewController controller;
  PlaceDetailFetcher detailFetcher;

  /**
   * Construct a new object.
   */
  public PlacePickerPresenterImpl() {
    detailFetcher = new PeliasPlaceDetailFetcher();
  }

  @Override public void setController(PlacePickerViewController controller) {
    this.controller = controller;
  }

  @Override public void onLabelPicked(LngLat coordinates, Map<String, String> properties) {
    final String id = properties.get(PROPERTY_ID);
    final String title = properties.get(PROPERTY_NAME);

    detailFetcher.fetchDetails(coordinates, properties,
        new OnPlaceDetailsFetchedListener() {
          @Override public void onPlaceDetailsFetched(String details) {
            controller.updateDialog(id, details);
          }
        }
    );

    controller.showDialog(id, title);
  }

  @Override public void onPlaceConfirmed() {
    Place place = detailFetcher.getFetchedPlace();
    controller.finishWithPlace(place);
  }
}
