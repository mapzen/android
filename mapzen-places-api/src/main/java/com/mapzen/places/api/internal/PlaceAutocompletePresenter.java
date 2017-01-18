package com.mapzen.places.api.internal;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;

import retrofit2.Response;

/**
 * Place autocomplete presenter to handle non-Android logic.
 */
class PlaceAutocompletePresenter {
  private final PlaceDetailFetcher detailFetcher;
  private final OnPlaceDetailsFetchedListener detailFetchListener;
  private final FilterMapper filterMapper;
  private LatLngBounds bounds;
  private AutocompleteFilter filter;

  /**
   * Creates a new instance.
   */
  PlaceAutocompletePresenter(PlaceDetailFetcher detailFetcher,
      OnPlaceDetailsFetchedListener detailFetchListener, FilterMapper filterMapper) {
    this.detailFetcher = detailFetcher;
    this.detailFetchListener = detailFetchListener;
    this.filterMapper = filterMapper;
  }

  void setBounds(LatLngBounds bounds) {
    this.bounds = bounds;
  }

  void setFilter(AutocompleteFilter filter) {
    this.filter = filter;
  }

  /**
   * Invoked when the {@code PeliasSearchView} returns a successful response.
   * @param response parsed result returned by the service.
   */
  void onResponse(Response<Result> response) {
    Properties properties = response.body().getFeatures().get(0).properties;
    String gid = properties.gid;
    detailFetcher.fetchDetails(gid, detailFetchListener);
  }

  /**
   * Return the bounding box that should be used to limit autocomplete results. This value is either
   * set explicitly via {@link com.mapzen.places.api.ui.PlaceAutocomplete.IntentBuilder#
   * setBoundsBias(LatLngBounds)} or is retrieved from the device's last known location.
   * @return
   */
  BoundingBox getBoundingBox() {
    if (bounds == null) {
      //TODO: retrieve device's last known location
      return new BoundingBox(40.7011375427, -74.0193099976, 40.8774528503, -73.9104537964);
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
    double diff = (boundingBox.getMaxLat() - boundingBox.getMinLat()) / 2;
    double midLat = boundingBox.getMinLat() + diff;
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
    double diff = (boundingBox.getMaxLon() - boundingBox.getMinLon()) / 2;
    double midLon = boundingBox.getMinLon() + diff;
    return midLon;
  }

  /**
   * Return the ISO 3166-1 Alpha-2 country code that should be used to limit autocomplete results.
   * @return
   */
  String getCountryFilter() {
    if (filter == null) {
      return null;
    }
    return filter.getCountry();
  }

  /**
   * Return the layers that should be used to limit autocomplete results.
   * @return
   */
  String getLayersFilter() {
    if (filter == null) {
      return null;
    }
    int typeFilter = filter.getTypeFilter();
    return filterMapper.getInternalFilter(typeFilter);
  }
}
