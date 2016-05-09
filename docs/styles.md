# Switching Styles

The map's style can be configured on initialization with `MapFragment#getMapAsync` or switched at runtime with `MapzenMap#setStyle`.

```java
private MapFragment mapFragment;
private MapzenMap mapzenMap;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_switch_style);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.style_array, R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new RefillStyle(), new OnMapReadyCallback() {
        @Override public void onMapReady(MapzenMap mapzenMap) {
            SwitchStyleActivity.this.mapzenMap = mapzenMap;
        }
    });
}
    
private void changeMapStyle(MapStyle style) {
    if (mapzenMap != null) {
        mapzenMap.setStyle(style);
    }
}

@Override 
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    switch (position) {
        case 0:
            changeMapStyle(new BubbleWrapStyle());
            break;
        case 1:
            changeMapStyle(new CinnabarStyle());
            break;
        case 2:
            changeMapStyle(new RefillStyle());
            break;
        default:
            changeMapStyle(new BubbleWrapStyle());
            break;
    }
}

@Override 
public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
}

```
