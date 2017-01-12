package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;

import retrofit2.Response;

/**
 * Place autocomplete presenter to handle non-Android logic.
 */
class PlaceAutocompletePresenter {
  private final PlaceAutocompleteController controller;

  /**
   * Creates a new instance with reference to controller.
   * @param controller place autocomplete wrapper Activity.
   */
  PlaceAutocompletePresenter(PlaceAutocompleteController controller) {
    this.controller = controller;
  }

  /**
   * Invoked when the {@code PeliasSearchView} returns a successful response.
   * @param response parsed result returned by the service.
   */
  void onResponse(Response<Result> response) {
    // TODO: Fetch place details.
    Properties properties = response.body().getFeatures().get(0).properties;
    String name = properties.name;
    String address = properties.label;
    Place place = new PlaceImpl.Builder()
        .setName(name)
        .setAddress(address)
        .build();
    Status status = new Status(Status.SUCCESS);
    controller.setResult(place, status);
    controller.finish();
  }

  /**
   * Return the bounding box that should be used to limit autocomplete results. This value is either
   * set explicitly via {@link com.mapzen.places.api.ui.PlaceAutocomplete.IntentBuilder#
   * setBoundsBias(LatLngBounds)} or is retrieved from the device's last known location.
   * @return
   */
  BoundingBox getBoundingBox() {
    LatLngBounds bounds = controller.getBounds();
    if (bounds == null) {
      //TODO: retrieve device's last known location
      return null;
    }
    double minLat = bounds.getSouthwest().getLatitude();
    double minLon = bounds.getSouthwest().getLongitude();
    double maxLat = bounds.getNortheast().getLatitude();
    double maxLon = bounds.getNortheast().getLongitude();
    return new BoundingBox(minLat, minLon, maxLat, maxLon);
  }

  /**
   * Returns the latitude value that should be used to limit autocomplete results. It will be the
   * center of the {@link BoundingBox} returned from
   * {@link PlaceAutocompletePresenter#getBoundingBox()}.
   * @return
   */
  double getLat() {
    BoundingBox boundingBox = getBoundingBox();
    if (boundingBox == null) {
      return 40.7443;
    }
    double midLat = (boundingBox.getMaxLat() - boundingBox.getMinLat()) / 2;
    return midLat;
  }

  /**
   * Returns the longitude value that should be used to limit autocomplete results. It will be the
   * center of the {@link BoundingBox} returned from
   * {@link PlaceAutocompletePresenter#getBoundingBox()}.
   * @return
   */
  double getLon() {
    BoundingBox boundingBox = getBoundingBox();
    if (boundingBox == null) {
      return -73.9903;
    }
    double midLon = (boundingBox.getMaxLon() - boundingBox.getMinLon()) / 2;
    return midLon;
  }
}
