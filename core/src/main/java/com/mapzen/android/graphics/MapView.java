package com.mapzen.android.graphics;

import com.mapzen.R;
import com.mapzen.android.core.CoreDI;
import com.mapzen.android.graphics.model.MapStyle;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Wrapper for Tangram MapView that initializes {@link MapzenMap} for client applications.
 */
public class MapView extends RelativeLayout {

  private static final String MAPZEN_RIGHTS = "https://mapzen.com/rights/";

  public static final int OVERLAY_MODE_SDK = 0;
  public static final int OVERLAY_MODE_CLASSIC = 1;

  @Inject MapInitializer mapInitializer;

  TangramMapView tangramMapView;
  CompassView compass;
  ImageButton findMe;
  ImageButton zoomIn;
  ImageButton zoomOut;
  TextView attribution;

  private int overlayMode = OVERLAY_MODE_SDK;
  private MapzenMap mapzenMap;

  /**
   * Create new instance.
   */
  public MapView(Context context) {
    super(context);
    initDI(context);
    initViews(context);
  }

  /**
   * Create new instance.
   */
  public MapView(Context context, AttributeSet attrs) {
    super(context, attrs);
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MapView);
    try {
      overlayMode = a.getInteger(R.styleable.MapView_overlayMode, OVERLAY_MODE_SDK);
    } finally {
      a.recycle();
    }

    initDI(context);
    initViews(context);
  }

  private void initDI(Context context) {
    CoreDI.init(context.getApplicationContext());
    CoreDI.component().inject(this);
  }

  private void initViews(Context context) {
    final LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (inflater != null) {
      if (overlayMode == OVERLAY_MODE_SDK) {
        inflater.inflate(R.layout.mz_view_map, this);
      } else {
        inflater.inflate(R.layout.mz_view_map_classic, this);
      }
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    tangramMapView = (TangramMapView) findViewById(R.id.mz_tangram_map);
    compass = (CompassView) findViewById(R.id.mz_compass);
    findMe = (ImageButton) findViewById(R.id.mz_find_me);
    zoomIn = (ImageButton) findViewById(R.id.mz_zoom_in);
    zoomOut = (ImageButton) findViewById(R.id.mz_zoom_out);
    attribution = (TextView) findViewById(R.id.mz_attribution);
    final TextView attribution = (TextView) findViewById(R.id.mz_attribution);
    attribution.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent();
        Uri uri = Uri.parse(MAPZEN_RIGHTS);
        intent.setData(uri);
        MapView.this.getContext().startActivity(intent);
      }
    });
  }

  /**
   * Get the underlying Tangram map object.
   */
  public TangramMapView getTangramMapView() {
    return tangramMapView;
  }

  /**
   * Load map asynchronously using APK key declared in XML resources. For example:
   * {@code <string name="mapzen_api_key">[YOUR_API_KEY]</string>}
   *
   * @param callback listener to be invoked when map is initialized and ready to use.
   */
  public void getMapAsync(@NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, callback);
  }

  /**
   * Load map asynchronously using APK key declared in XML resources. For example:
   * {@code <string name="mapzen_api_key">[YOUR_API_KEY]</string>}
   *
   * @param mapStyle mapStyle that should be set.
   * @param callback listener to be invoked when map is initialized and ready to use.
   */
  public void getMapAsync(MapStyle mapStyle, @NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, mapStyle, callback);
  }

  /**
   * Load map asynchronously using APK key declared in XML resources. For example:
   * {@code <string name="mapzen_api_key">[YOUR_API_KEY]</string>}
   *
   * @param mapStyle mapStyle that should be set.
   * @param locale used to determine language that should be used for map labels.
   * @param callback listener to be invoked when map is initialized and ready to use.
   */
  public void getMapAsync(MapStyle mapStyle, Locale locale, @NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, mapStyle, locale, callback);
  }

  /**
   * Get the compass button.
   */
  public CompassView getCompass() {
    return compass;
  }

  /**
   * Show compass button.
   */
  public CompassView showCompass() {
    compass.setVisibility(View.VISIBLE);
    return compass;
  }

  /**
   * Hide compass button.
   */
  public void hideCompass() {
    compass.setVisibility(View.GONE);
  }

  /**
   * Get the find me button.
   */
  public ImageButton getFindMe() {
    return findMe;
  }

  /**
   * Show button for finding user's location on map.
   */
  public ImageButton showFindMe() {
    findMe.setVisibility(View.VISIBLE);
    return findMe;
  }

  /**
   * Hide button for finding user's location on map.
   */
  public void hideFindMe() {
    findMe.setVisibility(View.GONE);
  }

  /**
   * Get the zoom in button.
   */
  public ImageButton getZoomIn() {
    return zoomIn;
  }

  /**
   * Show button for zooming in.
   */
  public ImageButton showZoomIn() {
    zoomIn.setVisibility(View.VISIBLE);
    return zoomIn;
  }

  /**
   * Hide button for zooming in.
   */
  public void hideZoomIn() {
    zoomIn.setVisibility(View.GONE);
  }

  /**
   * Get the zoom out button.
   */
  public ImageButton getZoomOut() {
    return zoomOut;
  }

  /**
   * Show button for zooming out.
   */
  public ImageButton showZoomOut() {
    zoomOut.setVisibility(View.VISIBLE);
    return zoomOut;
  }

  /**
   * Hide button for zooming out.
   */
  public void hideZoomOut() {
    zoomOut.setVisibility(View.GONE);
  }

  /**
   * Return the attribution text view.
   */
  public TextView getAttribution() {
    return attribution;
  }

  /**
   * You must call this method from the parent Activity/Fragment's corresponding method.
   */
  public void onDestroy() {
    if (mapzenMap != null) {
      mapzenMap.onDestroy();
    }
    getTangramMapView().onDestroy();
  }

  void setMapzenMap(MapzenMap mapzenMap) {
    this.mapzenMap = mapzenMap;
  }
}
