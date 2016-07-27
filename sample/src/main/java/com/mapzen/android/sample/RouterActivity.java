package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.MapzenRouter;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polyline;
import com.mapzen.model.ValhallaLocation;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.TouchInput;
import com.mapzen.valhalla.Route;
import com.mapzen.valhalla.RouteCallback;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates how to use {@link MapzenRouter}.
 */
public class RouterActivity extends AppCompatActivity {

  MapzenMap map;
  MapzenRouter router;
  MapData markerMapData;
  MapData lineMapData;
  int points = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_route);

    final String turnByturnKey = BuildConfig.TURN_BY_TURN_KEY;
    if (turnByturnKey != null) {
      router = new MapzenRouter(this, turnByturnKey);
    } else {
      router = new MapzenRouter(this);
    }

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        RouterActivity.this.map = map;
        configureMap();
        configureBtns();
        configureRouter();
      }
    });
  }

  private void configureMap() {
    map.setMyLocationEnabled(true);
    map.setZoom(15f);
    map.setPosition(new LngLat(-73.9918, 40.73633));
    map.setTapResponder(new TouchInput.TapResponder() {
      @Override public boolean onSingleTapUp(float x, float y) {
        LngLat point = map.screenPositionToLngLat(new PointF(x, y));
        addPointToRoute(point);
        return false;
      }

      @Override public boolean onSingleTapConfirmed(float x, float y) {
        return false;
      }
    });
  }

  private void configureBtns() {
    Button clearBtn = (Button) findViewById(R.id.clear_btn);
    clearBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        router.clearLocations();
        if (lineMapData != null) {
          lineMapData.clear();
        }
        if (markerMapData != null) {
          markerMapData.clear();
        }
        points = 0;
      }
    });

    Button routeBtn = (Button) findViewById(R.id.route_btn);
    routeBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (points >= 2) {
          router.fetch();
        } else {
          Toast.makeText(RouterActivity.this, R.string.min_two_points, Toast.LENGTH_SHORT).show();
        }
      }
    });

  }

  private void configureRouter() {
    router.setWalking();
    router.setCallback(new RouteCallback() {
      @Override public void success(Route route) {
        List<LngLat> coordinates = new ArrayList<>();
        for (ValhallaLocation location : route.getGeometry()) {
            coordinates.add(new LngLat(location.getLongitude(), location.getLatitude()));
        }
        Polyline polyline = new Polyline(coordinates);
        lineMapData = map.addPolyline(polyline);
      }

      @Override public void failure(int i) {
        Log.d("Fail", "Failed to get route");
      }
    });
  }

  private void addPointToRoute(LngLat lngLat) {
    double[] point = {lngLat.latitude, lngLat.longitude};
    router.setLocation(point);
    Marker marker = new Marker(lngLat.longitude, lngLat.latitude);
    markerMapData = map.addMarker(marker);
    points++;
  }
}
