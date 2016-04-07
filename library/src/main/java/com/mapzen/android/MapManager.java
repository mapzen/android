package com.mapzen.android;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.Properties;
import com.mapzen.tangram.Tangram;

import android.content.Context;
import android.location.Location;


/**
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}.
 */
public class MapManager {

    private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
    private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
    private static final String NAME_CURRENT_LOCATION = "find_me";
    private static final float ANIMATION_DURATION_SEC = .3f;

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
     * services using the {@link LocationManager}'s shared {@link LostApiClient}.
     * @param context
     * @param mapController
     */
    public MapManager(Context context, MapController mapController) {
        this.mapController = mapController;
        this.lostApiClient = LocationManager.sharedClient(context);
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
        this.currentLocationMapData = new MapData(NAME_CURRENT_LOCATION);
        Tangram.addDataSource(this.currentLocationMapData);
    }

    private void handleMyLocationEnabledChanged() {
        if (myLocationEnabled) {
            lostApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(LOCATION_REQUEST_INTERVAL_MILLIS)
                    .setFastestInterval(LOCATION_REQUEST_DISPLACEMENT_MILLIS)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(locationRequest,
                    locationListener);
        } else {
            LocationServices.FusedLocationApi.removeLocationUpdates(locationListener);
            lostApiClient.disconnect();
        }
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
        currentLocationMapData.addPoint(new Properties(), convertLocation(location));
    }

    private void updateMapPosition(Location location) {
        mapController.setMapPosition(location.getLongitude(), location.getLatitude(),
                ANIMATION_DURATION_SEC);
        mapController.requestRender();
    }

    private LngLat convertLocation(Location location) {
        return new LngLat(location.getLongitude(), location.getLatitude());
    }
}
