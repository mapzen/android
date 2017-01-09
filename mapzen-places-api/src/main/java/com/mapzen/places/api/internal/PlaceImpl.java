package com.mapzen.places.api.internal;

import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;

/**
 * Represents a physical place selected from a {@link com.mapzen.android.graphics.MapzenMap}.
 */
class PlaceImpl implements Place, Parcelable {

  private final CharSequence address;
  private final CharSequence attributions;
  private final String id;
  private final LatLng latLng;
  private final Locale locale;
  private final CharSequence name;
  private final CharSequence phoneNumber;
  private final List<Integer> placeTypes;
  private final int priceLevel;
  private final float rating;
  private final LatLngBounds viewport;
  private final Uri websiteUri;

  /**
   * Builder class for {@link PlaceImpl}.
   */
  public static class Builder {
    private CharSequence address;
    private CharSequence attributions;
    private String id;
    private LatLng latLng;
    private Locale locale;
    private CharSequence name;
    private CharSequence phoneNumber;
    private List<Integer> placeTypes;
    private int priceLevel;
    private float rating;
    private LatLngBounds viewport;
    private Uri websiteUri;

    /**
     * Set builder address.
     * @param address
     * @return
     */
    public Builder setAddress(CharSequence address) {
      this.address = address;
      return this;
    }

    /**
     * Set builder attribution.
     * @param attributions
     * @return
     */
    public Builder setAttributions(CharSequence attributions) {
      this.attributions = attributions;
      return this;
    }

    /**
     * Set builder id.
     * @param id
     * @return
     */
    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    /**
     * Set builder coordinates.
     * @param latLng
     * @return
     */
    public Builder setLatLng(LatLng latLng) {
      this.latLng = latLng;
      return this;
    }

    /**
     * Set builder locale.
     * @param locale
     * @return
     */
    public Builder setLocale(Locale locale) {
      this.locale = locale;
      return this;
    }

    /**
     * Set builder name.
     * @param name
     * @return
     */
    public Builder setName(CharSequence name) {
      this.name = name;
      return this;
    }

    /**
     * Set builder phone number.
     * @param phoneNumber
     * @return
     */
    public Builder setPhoneNumber(CharSequence phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    /**
     * Set builder place types.
     * @param placeTypes
     * @return
     */
    public Builder setPlaceTypes(List<Integer> placeTypes) {
      this.placeTypes = placeTypes;
      return this;
    }

    /**
     * Set builder price level.
     * @param priceLevel
     * @return
     */
    public Builder setPriceLevel(int priceLevel) {
      this.priceLevel = priceLevel;
      return this;
    }

    /**
     * Set builder rating.
     * @param rating
     * @return
     */
    public Builder setRating(float rating) {
      this.rating = rating;
      return this;
    }

    /**
     * Set builder viewport.
     * @param viewport
     * @return
     */
    public Builder setViewPort(LatLngBounds viewport) {
      this.viewport = viewport;
      return this;
    }

    /**
     * Set builder website uri.
     * @param websiteUri
     * @return
     */
    public Builder setWebsiteUri(Uri websiteUri) {
      this.websiteUri = websiteUri;
      return this;
    }

    /**
     * Construct a new place from the builder properties.
     * @return
     */
    public Place build() {
      return new PlaceImpl(address, attributions, id, latLng, locale, name, phoneNumber, placeTypes,
          priceLevel, rating, viewport, websiteUri);
    }
  }

  /**
   * Constructs a new place.
   * @param address
   * @param attributions
   * @param id
   * @param latLng
   * @param locale
   * @param name
   * @param phoneNumber
   * @param placeTypes
   * @param priceLevel
   * @param rating
   * @param viewport
   * @param websiteUri
   */
  private PlaceImpl(CharSequence address, CharSequence attributions, String id, LatLng latLng,
      Locale locale, CharSequence name, CharSequence phoneNumber, List<Integer> placeTypes,
      int priceLevel, float rating, LatLngBounds viewport, Uri websiteUri) {
    this.address = address;
    this.attributions = attributions;
    this.id = id;
    this.latLng = latLng;
    this.locale = locale;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.placeTypes = placeTypes;
    this.priceLevel = priceLevel;
    this.rating = rating;
    this.viewport = viewport;
    this.websiteUri = websiteUri;
  }

  /**
   * Returns human readable address for this place.
   * @return
   */
  @Override public CharSequence getAddress() {
    return address;
  }

  /**
   * Returns the attributions to be shown to the user if data from the Place is used.
   * @return
   */
  @Override public CharSequence getAttributions() {
    return attributions;
  }

  /**
   * Returns the unique id of this Place.
   * @return
   */
  @Override public String getId() {
    return id;
  }

  /**
   * Returns the location of this Place.
   * @return
   */
  @Override public LatLng getLatLng() {
    return latLng;
  }

  /**
   * Returns the locale in which the names and addresses were localized.
   * @return
   */
  @Override public Locale getLocale() {
    return locale;
  }

  /**
   * Returns the name of this Place.
   * @return
   */
  @Override public CharSequence getName() {
    return name;
  }

  /**
   * Returns the place's phone number in international format.
   * @return
   */
  @Override public CharSequence getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Returns a list of place types for this Place.
   * @return
   */
  @Override public List<Integer> getPlaceTypes() {
    return placeTypes;
  }

  /**
   * Returns the price level for this place on a scale from 0 (cheapest) to 4.
   * @return
   */
  @Override public int getPriceLevel() {
    return priceLevel;
  }

  /**
   * Returns the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
   * @return
   */
  @Override public float getRating() {
    return rating;
  }

  /**
   * Returns a viewport for displaying this Place.
   * @return
   */
  @Override public LatLngBounds getViewport() {
    return viewport;
  }

  /**
   * Returns website uri for this place.
   * @return
   */
  @Override public Uri getWebsiteUri() {
    return websiteUri;
  }

  public static final Parcelable.Creator<Place> CREATOR
      = new Parcelable.Creator<Place>() {
    public Place createFromParcel(Parcel in) {
      return new PlaceImpl(in);
    }

    public Place[] newArray(int size) {
      return new Place[size];
    }
  };

  private PlaceImpl(Parcel in) {
    this.address = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    this.attributions = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    this.id = in.readString();
    this.latLng = in.readParcelable(LatLng.class.getClassLoader());
    this.locale = (Locale) in.readSerializable();
    this.name = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    this.phoneNumber = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    this.placeTypes = in.readArrayList(Integer.class.getClassLoader());
    this.priceLevel = in.readInt();
    this.rating = in.readFloat();
    this.viewport = in.readParcelable(LatLngBounds.class.getClassLoader());
    this.websiteUri = in.readParcelable(Uri.class.getClassLoader());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    TextUtils.writeToParcel(this.address, parcel, i);
    TextUtils.writeToParcel(this.attributions, parcel, i);
    parcel.writeString(this.id);
    parcel.writeParcelable(this.latLng, i);
    parcel.writeSerializable(this.locale);
    TextUtils.writeToParcel(this.name, parcel, i);
    TextUtils.writeToParcel(this.phoneNumber, parcel, i);
    parcel.writeList(this.placeTypes);
    parcel.writeInt(this.priceLevel);
    parcel.writeFloat(this.rating);
    parcel.writeParcelable(this.viewport, i);
    parcel.writeParcelable(this.websiteUri, i);
  }
}
