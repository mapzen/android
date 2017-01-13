package com.mapzen.places.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Filter for customizing autocomplete results from {@link GeoDataApi}.
 */
public class AutocompleteFilter implements Parcelable {

  /**
   * Only return geocoding results with a precise address.
   */
  public static final int TYPE_FILTER_ADDRESS = 2;
  /**
   * Only return results that represent cities.
   *
   * Return will be returned for the following place types:
   * <p><ul>
   * <li>{@link Place#TYPE_LOCALITY}
   * <li>{@link Place#TYPE_ADMINISTRATIVE_AREA_LEVEL_3}
   * </ul><p>
   */
  public static final int TYPE_FILTER_CITIES = 5;
  /**
   * Only return results that are classified as businesses.
   */
  public static final int TYPE_FILTER_ESTABLISHMENT = 34;
  /**
   * Only return geocoding results, rather than business results.
   */
  public static final int TYPE_FILTER_GEOCODE = 1007;
  /**
   * An empty type filter.
   */
  public static final int TYPE_FILTER_NONE = 0;
  /**
   * Only return results that represent regions.
   *
   * Results will be returned for the following place types:
   * <p><ul>
   * <li>{@link Place#TYPE_LOCALITY}
   * <li>{@link Place#TYPE_SUBLOCALITY}
   * <li>{@link Place#TYPE_POSTAL_CODE}
   * <li>{@link Place#TYPE_COUNTRY}
   * <li>{@link Place#TYPE_ADMINISTRATIVE_AREA_LEVEL_1}
   * <li>{@link Place#TYPE_ADMINISTRATIVE_AREA_LEVEL_2}
   * </ul><p>
   *
   */
  public static final int TYPE_FILTER_REGIONS = 4;

  private final String country;
  private final int typeFilter;

  /**
   * Constructs a new object.
   */
  public AutocompleteFilter(String country, int typeFilter) {
    this.country = country;
    this.typeFilter = typeFilter;
  }

  /**
   * Return the filter used to restrict autocomplete results.
   *
   * Possible values:
   * <p><ul>
   * <li>{@link AutocompleteFilter#TYPE_FILTER_ADDRESS}
   * <li>{@link AutocompleteFilter#TYPE_FILTER_CITIES}
   * <li>{@link AutocompleteFilter#TYPE_FILTER_ESTABLISHMENT}
   * <li>{@link AutocompleteFilter#TYPE_FILTER_GEOCODE}
   * <li>{@link AutocompleteFilter#TYPE_FILTER_NONE}
   * <li>{@link AutocompleteFilter#TYPE_FILTER_REGIONS}
   * </ul><p>
   * @return
   */
  public int getTypeFilter() {
    return typeFilter;
  }

  /**
   * Builder for {@link AutocompleteFilter}.
   */
  public static class Builder {

    private String country;
    private int typeFilter;

    /**
     * The country to restrict results to. This should be a ISO 3166-1 Alpha-2 country code
     * (case insensitive). If this is not set, no country filtering will take place.
     * @param country
     * @return
     */
    public Builder setCountry(String country) {
      this.country = country;
      return this;
    }

    /**
     * Allows you to restrict the result set of a PlaceAutocomplete request.
     *
     * Valid values are:
     * <p><ul>
     * <li>{@link AutocompleteFilter#TYPE_FILTER_ADDRESS}
     * <li>{@link AutocompleteFilter#TYPE_FILTER_CITIES}
     * <li>{@link AutocompleteFilter#TYPE_FILTER_ESTABLISHMENT}
     * <li>{@link AutocompleteFilter#TYPE_FILTER_GEOCODE}
     * <li>{@link AutocompleteFilter#TYPE_FILTER_NONE}
     * <li>{@link AutocompleteFilter#TYPE_FILTER_REGIONS}
     * </ul><p>
     *
     * @param typeFilter
     * @return
     */
    public Builder setTypeFilter(int typeFilter) {
      this.typeFilter = typeFilter;
      return this;
    }

    /**
     * Returns an AutocompleteFilter that can be passed to
     * {@link GeoDataApi#getAutocompletePredictions(com.mapzen.android.lost.api.LostApiClient,
     * String, LatLngBounds, AutocompleteFilter}.
     * @return
     */
    public AutocompleteFilter build() {
      return new AutocompleteFilter(country, typeFilter);
    }
  }

  /**
   * Construsts a new object from a {@link Parcel}.
   * @param in
   */
  protected AutocompleteFilter(Parcel in) {
    country = in.readString();
    typeFilter = in.readInt();
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
    parcel.writeString(country);
    parcel.writeInt(typeFilter);
  }
}
