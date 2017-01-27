package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;

import android.location.Location;
import android.util.Log;

import retrofit2.Response;

/**
 * Place autocomplete presenter to handle non-Android logic.
 */
class PlaceAutocompletePresenter {
  private static final String TAG = "MapzenPlaces";
  private static final double LAT_DEFAULT = 0.0;
  private static final double LON_DEFAULT = 0.0;

  private final PlaceDetailFetcher detailFetcher;
  private final OnPlaceDetailsFetchedListener detailFetchListener;
  private final FilterMapper filterMapper;
  private final PointToBoundsConverter pointConverter;
  private LatLngBounds bounds;
  private AutocompleteFilter filter;
  private LostApiClient client;

  /**
   * Creates a new instance.
   */
  PlaceAutocompletePresenter(PlaceDetailFetcher detailFetcher,
      OnPlaceDetailsFetchedListener detailFetchListener, FilterMapper filterMapper) {
    this.detailFetcher = detailFetcher;
    this.detailFetchListener = detailFetchListener;
    this.filterMapper = filterMapper;
    this.pointConverter = new PointToBoundsConverter();
  }

  void setBounds(LatLngBounds bounds) {
    this.bounds = bounds;
  }

  void setFilter(AutocompleteFilter filter) {
    this.filter = filter;
  }

  /**
   * Set client to be used to retrieve device location if bounds is not set.
   * @param client
   */
  void setLostClient(LostApiClient client) {
    this.client = client;
    if (this.client != null) {
      client.connect();
    }
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
      Location location = null;
      if (client != null && client.isConnected()) {
        try {
          location = LocationServices.FusedLocationApi.getLastLocation(client);
        } catch (SecurityException e) {
          Log.e(TAG, "Please specify a bounding box or request "
              + "android.permission.ACCESS_COARSE_LOCATION in your manifest.");
        }
      }
      if (location != null) {
        return pointConverter.boundingBoxFromPoint(location.getLatitude(), location.getLongitude());
      }
      return new BoundingBox(LAT_DEFAULT, LON_DEFAULT, LAT_DEFAULT, LON_DEFAULT);
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
    BoundingBox boundingBox = null;
    try {
      boundingBox = getBoundingBox();
    } catch (SecurityException e) {
      Log.e(TAG, "Please specify a bounding box or "
          + "request android.permission.ACCESS_COARSE_LOCATION in your manifest.");
    }
    if (boundingBox == null) {
      return LAT_DEFAULT;
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
    BoundingBox boundingBox = null;
    try {
      boundingBox = getBoundingBox();
    } catch (SecurityException e) {
      Log.e(TAG, "Please specify a bounding box or "
          + "request android.permission.ACCESS_COARSE_LOCATION in your manifest.");
    }
    if (boundingBox == null) {
      return LON_DEFAULT;
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
