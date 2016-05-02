package com.mapzen.android;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import android.location.Location;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}.
 */
public class OverlayManager {

    private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
    private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
    private static final int ANIMATION_DURATION_MILLIS = 300;
    private static final String NAME_CURRENT_LOCATION = "mz_current_location";
    private static final String NAME_POLYLINE = "mz_default_line";
    private static final String NAME_POLYGON = "mz_default_polygon";
    private static final String NAME_MARKER = "mz_default_point";
    private static final int MIN_COORDINATES_POLYGON = 2;
    private static final int MIN_COORDINATES_POLYLINE = 2;

    /**
     * For interaction with the map.
     */
    private MapController mapController;
    /**
     * For interaction with location services.
     */
    private LostApiClient lostApiClient;
    /**
     * For interaction with map ui objects such as the "find me" button.
     */
    private MapView mapView;
    /**
     * Object to hold current location information to be displayed on map.
     */
    private MapData currentLocationMapData;
    /**
     * Should we track the user's current location on the map.
     */
    private boolean myLocationEnabled;

    private boolean locationRequested = false;

    /**
     * Receives location updates for {@link LostApiClient}.
     */
    LocationListener locationListener = new LocationListener() {
        @Override public void onLocationChanged(Location location) {
            if (!locationRequested) {
                return;
            }
            handleLocationChange(location);
        }
    };

    private MapData polylineMapData;

    private MapData polygonMapData;

    private MapData markerMapData;
    /**
     * Create a new {@link OverlayManager} object for handling functionality between map and
     * location services using the {@link LocationFactory}'s shared {@link LostApiClient}.
     * @param mapView
     * @param mapController
     */
    OverlayManager(MapView mapView, MapController mapController) {
        this(mapView, mapController, LocationFactory.sharedClient(mapView.getContext()));
    }

    /**
     * Create a new {@link OverlayManager} object for handling functionality between map and
     * location services.
     * @param mapController
     * @param lostApiClient
     * @param mapView
     */
    OverlayManager(MapView mapView, MapController mapController, LostApiClient lostApiClient) {
        this.mapView = mapView;
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
            initCurrentLocationMapData();
        }
        handleMyLocationEnabledChanged();
    }

    /**
     * Are we tracking the user's current location.
     * @return
     */
    public boolean isMyLocationEnabled() {
        return myLocationEnabled;
    }

    /**
     * Adds a polyline to the map.
     * @param polyline
     */
    public MapData addPolyline(Polyline polyline) {
        if (polyline == null) {
            throw new IllegalArgumentException("Must provide marker when calling "
                    + "MapData#addPolyline");
        }
        if (polyline.getCoordinates().size() < MIN_COORDINATES_POLYLINE) {
            throw new IllegalArgumentException("Polyine must contain at least 2 points");
        }

        if (polylineMapData == null) {
            initPolylineMapData();
        }
        return addPolylineToPolylineMapData(polyline.getCoordinates());
    }

    /**
     * Adds a polygon to the map.
     * @param polygon
     */
    public MapData addPolygon(Polygon polygon) {
        if (polygon == null) {
            throw new IllegalArgumentException("Must provide marker when calling "
                    + "MapData#addPolygon");
        }
        if (polygon.getCoordinates().size() < MIN_COORDINATES_POLYGON) {
            throw new IllegalArgumentException("Polygon must contain at least 2 points");
        }

        if (polygonMapData == null) {
            initPolygonMapData();
        }

        List<LngLat> coords = new ArrayList<>();
        coords.addAll(polygon.getCoordinates());
        LngLat first = polygon.getCoordinates().get(0);
        int size = polygon.getCoordinates().size();
        LngLat last = polygon.getCoordinates().get(size - 1);
        boolean closed = first.equals(last);
        if (!closed) {
            coords.add(first);
        }
        List allCoords = new ArrayList();
        allCoords.add(coords);
        return addPolygonToPolygonMapData(allCoords);
    }

    /**
     * Add a point to the map for the marker.
     * @param marker
     * @return
     */
    public MapData addMarker(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Must provide marker when calling "
                    + "MapData#addMarker");
        }

        if (markerMapData == null) {
            initMarkerMapData();
        }
        return addPointToMarkerMapData(marker);
    }

    private void initCurrentLocationMapData() {
        currentLocationMapData = mapController.addDataLayer(NAME_CURRENT_LOCATION);
    }

    private void handleMyLocationEnabledChanged() {
        if (myLocationEnabled) {
            lostApiClient.connect();
            showLastKnownLocation();
            showFindMe();
            requestLocationUpdates();
        } else {
            hideFindMe();
            removeLocationUpdates();
        }
    }

    private void showFindMe() {
        ImageButton button = mapView.showFindMe();
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                centerMap();
            }
        });
    }

    private void hideFindMe() {
        mapView.hideFindMe();
        locationRequested = false;
    }

    private void centerMap() {
        locationRequested = true;
        showLastKnownLocation();
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
        if (!myLocationEnabled || !locationRequested) {
            return;
        }
        locationRequested = false;
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
        mapController.setPositionEased(lngLat, ANIMATION_DURATION_MILLIS);
        mapController.requestRender();
    }

    private LngLat convertLocation(Location location) {
        return new LngLat(location.getLongitude(), location.getLatitude());
    }

    private void initPolylineMapData() {
        polylineMapData = mapController.addDataLayer(NAME_POLYLINE);
    }

    private void initPolygonMapData() {
        polygonMapData = mapController.addDataLayer(NAME_POLYGON);
    }

    private void initMarkerMapData() {
        markerMapData = mapController.addDataLayer(NAME_MARKER);
    }

    private MapData addPolylineToPolylineMapData(List<LngLat> coordinates) {
        return polylineMapData.addPolyline(coordinates, null);
    }

    private MapData addPolygonToPolygonMapData(List<List<LngLat>> coordinates) {
        return polygonMapData.addPolygon(coordinates, null);
    }

    private MapData addPointToMarkerMapData(Marker marker) {
        return markerMapData.addPoint(marker.getLocation(), null);
    }
}
