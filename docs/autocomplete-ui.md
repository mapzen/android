# Autocomplete UI

The Mapzen Places Autocomplete UI provides an easy way for you to display a search bar, allow
users to select a `Place` from autocomplete results, and then get information on the `Place` they
selected. It is available for you to integrate into your app via either a fragment or an intent.

## PlaceAutocompleteFragment

### Add Fragment to your Layout

First you need to add the fragment into your xml layout file like below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    >

  <fragment
      android:id="@+id/place_autocomplete_fragment"
      android:name="com.mapzen.places.api.ui.PlaceAutocompleteFragment"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"/>

</LinearLayout>
```

### Configure Fragment

Get a reference to your fragment and configure it to fit your needs. You can set a bounds bias
and/or filter on it to limit results to a specific area or type. You can also customize the hint
text shown in the search bar as well as set a default text to search. To get information back about
the place a user selects, set a listener on the fragment and implement the interface methods.

Example configuration:
```java
PlaceAutocompleteFragment fragment = (PlaceAutocompleteFragment) getFragmentManager().
    findFragmentById(R.id.place_autocomplete_fragment);
fragment.setPlaceSelectionListener(this);

AutocompleteFilter filter = new AutocompleteFilter.Builder()
    .setTypeFilter(TYPE_FILTER_ADDRESS)
    .build();
fragment.setFilter(filter);

LatLng sw = new LatLng(42.80749, -73.14697);
LatLng ne = new LatLng(44.98423, -71.58691);
LatLngBounds boundsBias = new LatLngBounds(sw, ne);
fragment.setBoundsBias(boundsBias);

fragment.setHint("Search");
fragment.setText("pizza");
```

Example implementation of selection listener methods:
```java
@Override public void onPlaceSelected(Place place) {
  // Do something with selected place
}

@Override public void onError(Status status) {
  // Show toast error message
}
```

## PlaceAutocomplete.IntentBuilder

The second way that the autocomplete UI can be invoked is via an `Intent`. To fire an `Intent`, use
the `PlaceAutocomplete#IntentBuilder` and optionally set a bounds bias to limit results to and/or a
filter to limit results by.

Configure `Intent`:
```java
public static final int REQUEST_CODE = 1;

LatLng sw = new LatLng(42.80749, -73.14697);
LatLng ne = new LatLng(44.98423, -71.58691);
LatLngBounds boundsBias = new LatLngBounds(sw, ne);

Intent intent = new PlaceAutocomplete.IntentBuilder(MODE_OVERLAY)
    .setBoundsBias(boundsBias)
    .setFilter(filter)
    .build(this);
startActivityForResult(intent, REQUEST_CODE);
```

Because the intent is started with `Activity#startActivityForResult`, your activity will receive a
callback to `Activity#onActivityResult` when the user has selected a `Place`. To handle what should
happen when a `Place` is selected, implement this method and use the `PlaceAutocomplete` static
methods to retrieve information about the selected `Place`.

Handle Callback:
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
    Place place = PlaceAutocomplete.getPlace(this, data);
    // update UI with place information

    Status status = PlaceAutocomplete.getStatus(this, data);
    // check status code and optionally show toast with error info
  }
}
```
