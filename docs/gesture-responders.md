# Gesture Responders

The map supports taps, double taps, shoves, scales, rotations, pans, and long presses. To receive information about when these events occur, create a responder.

```java

private MapzenMap map;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_touch_responder);
    final MapView mapView = (MapView) findViewById(R.id.map_view);
    mapView.getMapAsync(new OnMapReadyCallback() {
        @Override public void onMapReady(MapzenMap map) {
            TouchResponderActivity.this.map = map;
            configureMap();
        }
    });
}

private void configureMap() {
    map.setTapResponder(new TouchInput.TapResponder() {
        @Override public boolean onSingleTapUp(float x, float y) {
            String extra = "x:" + x + " y:" + y;
            logResponderEvent("Single Tap Up", extra);
            return false;
        }

        @Override public boolean onSingleTapConfirmed(float x, float y) {
            String extra = "x:" + x + " y:" + y;
            logResponderEvent("Single Tap Confirmed", extra);
            return false;
        }
    });

    map.setShoveResponder(new TouchInput.ShoveResponder() {
        @Override public boolean onShove(float distance) {
            String extra = "distance:" + distance;
            logResponderEvent("Shove", extra);
            return false;
        }
    });
    map.setScaleResponder(new TouchInput.ScaleResponder() {
        @Override public boolean onScale(float x, float y, float scale, float velocity) {
            String extra = "x:" + x + " y:" + y + " scale:" + scale + " velocity:" + velocity;
            logResponderEvent("Scale", extra);
            return false;
        }
    });
    map.setRotateResponder(new TouchInput.RotateResponder() {
        @Override public boolean onRotate(float x, float y, float rotation) {
            String extra = "x:" + x + " y:" + y + " rotation:" + rotation;
            logResponderEvent("Rotate", extra);
            return false;
        }
    });
    map.setPanResponder(new TouchInput.PanResponder() {
        @Override public boolean onPan(float startX, float startY, float endX, float endY) {
            String extra = "startX:" + startX + " startY:" + startY + "endX" + endX + " endY"
                    + endY;
            logResponderEvent("Pan", extra);
            return false;
        }

        @Override public boolean onFling(float posX, float posY, float velocityX,
                float velocityY) {
            String extra = "posX:" + posX + " posY:" + posY + " velocityX:" + velocityX;
            logResponderEvent("Fling", extra);
            return false;
        }
    });
    map.setDoubleTapResponder(new TouchInput.DoubleTapResponder() {
        @Override public boolean onDoubleTap(float x, float y) {
            String extra = "x:" + x + " y:" + y;
            logResponderEvent("Double Tap", extra);
            return false;
        }
    });
    map.setLongPressResponder(new TouchInput.LongPressResponder() {
        @Override public void onLongPress(float x, float y) {
            String extra = "x:" + x + " y:" + y;
            logResponderEvent("Long Press", extra);
        }
    });
}

private void logResponderEvent(String event, String extra) {
    Log.d("MapzenMap Responder Event", event + " " + extra);
}
```