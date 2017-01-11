package com.mapzen.places.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Filter for customizing autocomplete results from {@link GeoDataApi}.
 */
public class AutocompleteFilter implements Parcelable {

  public AutocompleteFilter() {

  }

  protected AutocompleteFilter(Parcel in) {
  }

  public static final Creator<AutocompleteFilter> CREATOR = new Creator<AutocompleteFilter>() {
    @Override public AutocompleteFilter createFromParcel(Parcel in) {
      return new AutocompleteFilter(in);
    }

    @Override public AutocompleteFilter[] newArray(int size) {
      return new AutocompleteFilter[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
  }
}
