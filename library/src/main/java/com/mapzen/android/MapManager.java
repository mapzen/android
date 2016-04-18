package com.mapzen.android;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import android.content.Context;
import android.location.Location;

/**
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}.
 */
public class MapManager {

    private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
    private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
    private static final int ANIMATION_DURATION_MILLIS = 300;
    private static final String NAME_CURRENT_LOCATION = "find_me";

    /**
     * For interaction with the map.
     */
    private MapController mapController;
    /**
     * For interaction with location services.
     */
    private LostApiClient lostApiClient;
    /**
     * Object to hold current location information to be displayed on map.
     */
    private MapData currentLocationMapData;
    /**
     * Should we track the user's current location on the map.
     */
    private boolean myLocationEnabled;

    /**
     * Receives location updates for {@link LostApiClient}.
     */
    LocationListener locationListener = new LocationListener() {
        @Override public void onLocationChanged(Location location) {
            handleLocationChange(location);
        }
    };

    /**
     * Create a new {@link MapController} object for handling functionality between map and location
     * services using the {@link LocationFactory}'s shared {@link LostApiClient}.
     * @param context
     * @param mapController
     */
    public MapManager(Context context, MapController mapController) {
        this.mapController = mapController;
        this.lostApiClient = LocationFactory.sharedClient(context);
    }

    /**
     * Create a new {@link MapController} object for handling functionality between map and location
     * services.
     * @param mapController
     * @param lostApiClient
     */
    public MapManager(MapController mapController, LostApiClient lostApiClient) {
        this.mapController = mapController;
        this.lostApiClient = lostApiClient;
    }

    /**
     * Track the user's current location by displaying an icon on the map and centering the map.
     * @param enabled
     */
    public void setMyLocationEnabled(boolean enabled) {
        myLocationEnabled = enabled;
        if (currentLocationMapData == null) {
            addCurrentLocationMapDataToMap();
        }
        handleMyLocationEnabledChanged();
    }

    private void addCurrentLocationMapDataToMap() {
        currentLocationMapData = mapController.addDataLayer(NAME_CURRENT_LOCATION);
    }

    private void handleMyLocationEnabledChanged() {
        if (myLocationEnabled) {
            lostApiClient.connect();
            showLastKnownLocation();
            requestLocationUpdates();
        } else {
            removeLocationUpdates();
        }
    }

    private void showLastKnownLocation() {
        final Location current = LocationServices.FusedLocationApi.getLastLocation();
        if (current != null) {
            updateCurrentLocationMapData(current);
            updateMapPosition(current);
        }
    }

    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_REQUEST_INTERVAL_MILLIS)
                .setFastestInterval(LOCATION_REQUEST_DISPLACEMENT_MILLIS)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(locationRequest,
                locationListener);
    }

    private void removeLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(locationListener);
        lostApiClient.disconnect();
    }

    private void handleLocationChange(Location location) {
        if (!myLocationEnabled) {
            return;
        }
        updateCurrentLocationMapData(location);
        updateMapPosition(location);
    }

    private void updateCurrentLocationMapData(final Location location) {
        currentLocationMapData.clear();
        currentLocationMapData.addPoint(convertLocation(location), null);
    }

    private void updateMapPosition(Location location) {
        if (mapController == null) {
            return;
        }

        final LngLat lngLat = new LngLat(location.getLongitude(), location.getLatitude());
        mapController.setPosition(lngLat, ANIMATION_DURATION_MILLIS);
        mapController.requestRender();
    }

    private LngLat convertLocation(Location location) {
        return new LngLat(location.getLongitude(), location.getLatitude());
    }

    /**
     * You must call this method from your activity or fragment.
     */
    public void onDestroy() {
        currentLocationMapData.clear();
    }
}
