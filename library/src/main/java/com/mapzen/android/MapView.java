package com.mapzen.android;

import com.mapzen.android.dagger.DI;
import com.mapzen.tangram.MapController;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import javax.inject.Inject;

/**
 * Wrapper for Tangram MapView that initializes {@link MapController} for client applications.
 */
public class MapView extends RelativeLayout {
    @Inject MapInitializer mapInitializer;

    TangramMapView tangramMapView;
    ImageButton findMe;
    MapController mapController;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.view_map, this);
        }
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        tangramMapView = (TangramMapView) findViewById(R.id.map);
        findMe = (ImageButton) findViewById(R.id.find_me);
    }

    /**
     * Get the underlying Tangram map object.
     * @return
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
     * Load map asynchronously using given API key.
     *
     * @param callback listener to be invoked when map is initialized and ready to use.
     * @param key vector tiles API key that should be used to load map tiles.
     */
    public void getMapAsync(@NonNull OnMapReadyCallback callback, @NonNull String key) {
        mapInitializer.init(this, callback, key);
    }

    /**
     * Show button for finding user's location on map.
     * @return
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
}
