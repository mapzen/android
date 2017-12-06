package com.mapzen.places.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Represents a rectangular area.
 */
public class LatLngBounds implements Parcelable {

  private final LatLng northeast;
  private final LatLng southwest;

  /**
   * Constructs a new object given southwest and northwest points.
   * @param southwest
   * @param northeast
   */
  public LatLngBounds(@NonNull LatLng southwest, @NonNull LatLng northeast) {
    this.southwest = southwest;
    this.northeast = northeast;
  }

  @NonNull public LatLng getSouthwest() {
    return southwest;
  }

  @NonNull public LatLng getNortheast() {
    return northeast;
  }

  /**
   * Determines whether the given point is contained within the lat/lng's bounds.
   * @param point
   * @return
   */
  public boolean contains(LatLng point) {
    return this.includesLat(point.getLatitude()) && this.includesLng(point.getLongitude());
  }

  /**
   * Returns a new object which includes the given point.
   * @param point
   * @return
   */
  @NonNull public LatLngBounds including(@NonNull LatLng point) {
    double swLat = Math.min(this.southwest.getLatitude(), point.getLatitude());
    double neLat = Math.max(this.northeast.getLatitude(), point.getLatitude());
    double neLng = this.northeast.getLongitude();
    double swLng = this.southwest.getLongitude();
    double ptLng = point.getLongitude();
    if (!this.includesLng(ptLng)) {
      if (swLngMod(swLng, ptLng) < neLngMod(neLng, ptLng)) {
        swLng = ptLng;
      } else {
        neLng = ptLng;
      }
    }

    return new LatLngBounds(new LatLng(swLat, swLng), new LatLng(neLat, neLng));
  }

  /**
   * Returns the center of the lat/lng bounds.
   * @return
   */
  @NonNull public LatLng getCenter() {
    double midLat = (this.southwest.getLatitude() + this.northeast.getLatitude()) / 2.0D;
    double neLng = this.northeast.getLongitude();
    double swLng = this.southwest.getLongitude();
    double midLng;
    if (swLng <= neLng) {
      midLng = (neLng + swLng) / 2.0D;
    } else {
      midLng = (neLng + 360.0D + swLng) / 2.0D;
    }

    return new LatLng(midLat, midLng);
  }

  private static double swLngMod(double swLng, double ptLng) {
    return (swLng - ptLng + 360.0D) % 360.0D;
  }

  private static double neLngMod(double neLng, double ptLng) {
    return (ptLng - neLng + 360.0D) % 360.0D;
  }

  private boolean includesLat(double lat) {
    return this.southwest.getLatitude() <= lat && lat <= this.northeast.getLatitude();
  }

  private boolean includesLng(double lng) {
    return this.southwest.getLongitude() <= this.northeast.getLongitude() ?
        this.southwest.getLongitude() <= lng && lng <= this.northeast.getLongitude() :
        this.southwest.getLongitude() <= lng || lng <= this.northeast.getLongitude();
  }

  public static final Parcelable.Creator<LatLngBounds> CREATOR
      = new Parcelable.Creator<LatLngBounds>() {
    public LatLngBounds createFromParcel(Parcel in) {
      return new LatLngBounds(in);
    }

    public LatLngBounds[] newArray(int size) {
      return new LatLngBounds[size];
    }
  };

  private LatLngBounds(Parcel in) {
    this.northeast = in.readParcelable(LatLng.class.getClassLoader());
    this.southwest = in.readParcelable(LatLng.class.getClassLoader());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeParcelable(this.northeast, i);
    parcel.writeParcelable(this.southwest, i);
  }

  /**
   * Builder class for {@link LatLngBounds}.
   */
  public static class Builder {

    private double northeastLat = 0;
    private double northeastLng = 0;
    private double southwestLat = 0;
    private double southwestLng = 0;

    /**
     * Includes this point for building of the bounds. The bounds will be extended in a minimum way
     * to include this point.
     * @param point
     * @return builder object
     */
    @NonNull public Builder include(@NonNull LatLng point) {
      if (northeastLat == 0) {
        northeastLat = point.getLatitude();
        northeastLng = point.getLongitude();
        southwestLat = point.getLatitude();
        southwestLng = point.getLongitude();
      }
      if (point.getLatitude() > northeastLat) {
        northeastLat = point.getLatitude();
      } else if (point.getLatitude() < southwestLat) {
        southwestLat = point.getLatitude();
      }
      if (point.getLongitude() > northeastLng) {
        northeastLng = point.getLongitude();
      } else if (point.getLongitude() < southwestLng) {
        southwestLng = point.getLongitude();
      }
      return this;
    }

    /**
     * Constructs a new {@link LatLngBounds} from current boundaries.
     * @return
     */
    @NonNull public LatLngBounds build() {
      LatLng sw = new LatLng(southwestLat, southwestLng);
      LatLng ne = new LatLng(northeastLat, northeastLng);
      return new LatLngBounds(sw, ne);
    }
  }
}
