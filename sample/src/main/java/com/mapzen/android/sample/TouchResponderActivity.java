package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.tangram.TouchInput;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Demonstrates handling touches on the map.
 */
public class TouchResponderActivity extends AppCompatActivity {

    private MapzenMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                TouchResponderActivity.this.map = map;
                configureMap();
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void configureMap() {
        map.setTapResponder(new TouchInput.TapResponder() {
            @Override public boolean onSingleTapUp(float x, float y) {
                return false;
            }

            @Override public boolean onSingleTapConfirmed(float x, float y) {
                return false;
            }
        });
        map.setShoveResponder(new TouchInput.ShoveResponder() {
            @Override public boolean onShove(float distance) {
                return false;
            }
        });
        map.setScaleResponder(new TouchInput.ScaleResponder() {
            @Override public boolean onScale(float x, float y, float scale, float velocity) {
                return false;
            }
        });
        map.setRotateResponder(new TouchInput.RotateResponder() {
            @Override public boolean onRotate(float x, float y, float rotation) {
                return false;
            }
        });
        map.setPanResponder(new TouchInput.PanResponder() {
            @Override public boolean onPan(float startX, float startY, float endX, float endY) {
                return false;
            }

            @Override public boolean onFling(float posX, float posY, float velocityX,
                    float velocityY) {
                return false;
            }
        });
        map.setDoubleTapResponder(new TouchInput.DoubleTapResponder() {
            @Override public boolean onDoubleTap(float x, float y) {
                return false;
            }
        });
        map.setLongPressResponder(new TouchInput.LongPressResponder() {
            @Override public void onLongPress(float x, float y) {
                
            }
        });
    }
}
