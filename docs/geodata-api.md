# Autocomplete Retrieval

The GeoDataAPI allows you to programmatically retrieve autocomplete (and soon place) results. It
does not have any UI components, allowing you to use your own custom UI. To get started with this
API you need to create and connect a `LostApiClient`. After the client is connected, you can query
the GeoDataApi for information. This API is still in active development and will have more features added soon.

##Create and Connect `LostApiClient`:
LostApiClient client;

```java
@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  // setContentView
  // configure custom UI elements

  connectClient();
}

private void connectClient() {
  client = new LostApiClient.Builder(this).addConnectionCallbacks(
      new LostApiClient.ConnectionCallbacks() {

        @Override public void onConnected() {
          // query GeoDataApi for information
        }

        @Override public void onConnectionSuspended() {

        }
      }).build();
  client.connect();
}
```

After the client connects, you can invoke the `GeoDataApi`.

## `Places.GeoDataApi.getAutocompletePredictions`:

When querying for autocomplete results, you can configure a query string to search for, limit
results to a certain bounds, and limit results to a specific type.

```java
String query = "Main Street";

LatLng pointA = new LatLng(40.020451, -105.274679);
LatLng pointB = new LatLng(40.012004, -105.289957);
LatLngBounds bounds = new LatLngBounds.Builder().include(pointA).include(pointB).build();

AutocompleteFilter filter = new AutocompleteFilter.Builder()
        .setTypeFilter(TYPE_FILTER_GEOCODE)
        .build();

PendingResult<AutocompletePredictionBuffer> result =
    Places.GeoDataApi.getAutocompletePredictions(client, query, bounds, null);

result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
  @Override public void onResult(@NonNull AutocompletePredictionBuffer result) {
    // Iterate through results and display them in list or other UI
  }
});
```
