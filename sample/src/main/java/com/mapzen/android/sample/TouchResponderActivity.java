package com.mapzen.android.sample;

import com.mapzen.android.MapView;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.tangram.TouchInput;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates handling touches on the map.
 */
public class TouchResponderActivity extends AppCompatActivity {

  private MapzenMap map;

  private List<String> responderEvents = new ArrayList<String>();

  private BaseAdapter listAdapter = new BaseAdapter() {
    @Override public int getCount() {
      return responderEvents.size();
    }

    @Override public Object getItem(int position) {
      return responderEvents.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView =
            View.inflate(TouchResponderActivity.this, android.R.layout.simple_list_item_1, null);
      }

      TextView textView = (TextView) convertView;
      textView.setText(responderEvents.get(position));

      return convertView;
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_touch_responder);
    final MapView mapView = (MapView) findViewById(R.id.map_view);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        TouchResponderActivity.this.map = map;
        configureMap();
      }
    });

    final ListView listView = (ListView) findViewById(R.id.list_view);
    listView.setAdapter(listAdapter);
  }

  private void configureMap() {
    map.setTapResponder(new TouchInput.TapResponder() {
      @Override public boolean onSingleTapUp(float x, float y) {
        String extra = "x:" + x + " y:" + y;
        addEventToList(R.string.on_single_tap_up, extra);
        return false;
      }

      @Override public boolean onSingleTapConfirmed(float x, float y) {
        String extra = "x:" + x + " y:" + y;
        addEventToList(R.string.on_single_tap_confirmed, extra);
        return false;
      }
    });

    map.setShoveResponder(new TouchInput.ShoveResponder() {
      @Override public boolean onShove(float distance) {
        String extra = "distance:" + distance;
        addEventToList(R.string.on_shove, extra);
        return false;
      }
    });
    map.setScaleResponder(new TouchInput.ScaleResponder() {
      @Override public boolean onScale(float x, float y, float scale, float velocity) {
        String extra = "x:" + x + " y:" + y + " scale:" + scale + " velocity:" + velocity;
        addEventToList(R.string.on_scale, extra);
        return false;
      }
    });
    map.setRotateResponder(new TouchInput.RotateResponder() {
      @Override public boolean onRotate(float x, float y, float rotation) {
        String extra = "x:" + x + " y:" + y + " rotation:" + rotation;
        addEventToList(R.string.on_rotate, extra);
        return false;
      }
    });
    map.setPanResponder(new TouchInput.PanResponder() {
      @Override public boolean onPan(float startX, float startY, float endX, float endY) {
        String extra = "startX:" + startX + " startY:" + startY + "endX" + endX + " endY" + endY;
        addEventToList(R.string.on_pan, extra);
        return false;
      }

      @Override public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
        String extra = "posX:" + posX + " posY:" + posY + " velocityX:" + velocityX;
        addEventToList(R.string.on_fling, extra);
        return false;
      }
    });
    map.setDoubleTapResponder(new TouchInput.DoubleTapResponder() {
      @Override public boolean onDoubleTap(float x, float y) {
        String extra = "x:" + x + " y:" + y;
        addEventToList(R.string.on_double_tap, extra);
        return false;
      }
    });
    map.setLongPressResponder(new TouchInput.LongPressResponder() {
      @Override public void onLongPress(float x, float y) {
        String extra = "x:" + x + " y:" + y;
        addEventToList(R.string.on_long_press, extra);
      }
    });
  }

  private void addEventToList(int resId, String extra) {
    responderEvents.add(getString(resId) + " " + extra);
    listAdapter.notifyDataSetChanged();
  }
}
