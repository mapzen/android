# Get Started

LOST is an open source alternative to the Google Play services location APIs that depends only on the Android SDK. It provides 1:1 replacements for the FusedLocationProviderApi, GeofencingApi, and SettingsApi.

Lost operates by making calls directly to the LocationManger. Lost can run on any Android device running API 15 or higher regardless of whether or not it supports the Google Play ecosystem.

When using Lost, [`GoogleApiClient`](https://developer.android.com/reference/com/google/android/gms/common/api/GoogleApiClient.html) is replaced by [`LostApiClient`](https://github.com/mapzen/lost/blob/master/lost/src/main/java/com/mapzen/android/lost/api/LostApiClient.java).

## Create and connect a LostApiClient

After calling `lostApiClient.connect()` you should wait for `ConnectionCallbacks#onConnected()` before performing other operations like getting the last known location or requesting location updates.

```java
LostApiClient lostApiClient = new LostApiClient.Builder(this).addConnectionCallbacks(this).build();
lostApiClient.connect();

@Override public void onConnected() {
  // Client is connected and ready to for use
}

@Override public void onConnectionSuspended() {
  // Fused location provider service has been suspended
}
```
