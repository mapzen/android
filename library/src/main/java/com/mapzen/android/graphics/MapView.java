package com.mapzen.android.graphics;

import com.mapzen.android.core.DI;
import com.mapzen.android.R;
import com.mapzen.android.graphics.model.MapStyle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

/**
 * Wrapper for Tangram MapView that initializes {@link MapzenMap} for client applications.
 */
public class MapView extends RelativeLayout {

  private static final String MAPZEN_RIGHTS = "https://mapzen.com/rights/";

  @Inject MapInitializer mapInitializer;

  TangramMapView tangramMapView;
  ImageButton findMe;
  TextView attribution;

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
    initDI(context);
    initViews(context);
  }

  private void initDI(Context context) {
    DI.init(context);
    DI.component().inject(this);
  }

  private void initViews(Context context) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (inflater != null) {
      inflater.inflate(R.layout.mz_view_map, this);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    tangramMapView = (TangramMapView) findViewById(R.id.mz_tangram_map);
    findMe = (ImageButton) findViewById(R.id.mz_find_me);
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
   * {@code <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>}
   *
   * @param callback listener to be invoked when map is initialized and ready to use.
   */
  public void getMapAsync(@NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, callback);
  }

  /**
   * Load map asynchronously using APK key declared in XML resources. For example:
   * {@code <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>}
   *
   * @param callback listener to be invoked when map is initialized and ready to use.
   * @param mapStyle mapStyle that should be set
   */
  public void getMapAsync(MapStyle mapStyle, @NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, mapStyle, callback);
  }

  /**
   * Load map asynchronously using given API key.
   *
   * @param callback listener to be invoked when map is initialized and ready to use.
   * @param key vector tiles API key that should be used to load map tiles.
   */
  public void getMapAsync(@NonNull String key, @NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, key, callback);
  }

  /**
   * Load map asynchronously using given API key.
   *
   * @param callback listener to be invoked when map is initialized and ready to use.
   * @param key vector tiles API key that should be used to load map tiles.
   * @param mapStyle mapStyle that should be set
   */
  public void getMapAsync(@NonNull String key, MapStyle mapStyle,
      @NonNull OnMapReadyCallback callback) {
    mapInitializer.init(this, key, mapStyle, callback);
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
   * Return the attribution text view.
   */
  public TextView getAttribution() {
    return attribution;
  }

  /**
   * You must call this method from the parent Activity/Fragment's corresponding method.
   */
  public void onDestroy() {
    getTangramMapView().onDestroy();
  }
}
