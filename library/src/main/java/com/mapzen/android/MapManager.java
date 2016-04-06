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
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}
 */
public class MapManager {

    private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
    private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
    private static final String NAME_CURRENT_LOCATION = "com.mapzen.android.current_location";

    /**
     * For interaction with the map
     */
    private MapController mapController;
    /**
     * For interaction with location services
     */
    private LostApiClient lostApiClient;
    /**
     * Object to hold current location information to be displayed on map
     */
    private MapData currentLocationMapData;
    /**
     * Should we track the user's current location on the map
     */
    private boolean myLocationEnabled;

    /**
     * Receives location updates for {@link LostApiClient}
     */
    LocationListener locationListener = new LocationListener() {
        @Override public void onLocationChanged(Location location) {
            handleLocationChange(location);
        }
    };

    /**
     * Create a new {@link MapController} object for handling functionality between map and location
     * services
     * @param context
     * @param mapController
     */
    public MapManager(Context context, MapController mapController) {
        this.lostApiClient = new LostApiClient.Builder(context).build();
        this.mapController = mapController;
        this.currentLocationMapData = new MapData(NAME_CURRENT_LOCATION);
        Tangram.addDataSource(this.currentLocationMapData);
    }

    /**
     * Track the user's current location by displaying an icon on the map and centering the map
     * @param enabled
     */
    public void setMyLocationEnabled(boolean enabled) {
        myLocationEnabled = enabled;
        handleMyLocationEnabledChanged();
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
    }

    private void updateCurrentLocationMapData(Location location) {
        currentLocationMapData.clear();
        currentLocationMapData.addPoint(new Properties(), convertLocation(location));
        mapController.requestRender();
    }

    private LngLat convertLocation(Location location) {
        return new LngLat(location.getLongitude(), location.getLatitude());
    }
}
