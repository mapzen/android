package com.mapzen.android.core;

import android.content.Context;
import android.content.res.Resources;

/**
 * {@code MapzenManager} assists with Mapzen API key management. When created it reads the API key
 * set in string resources if one has been declared by the application.
 *
 * <pre>
 * &lt;resources&gt;
 *   &lt;string name="mapzen_api_key"&gt;[YOUR_MAPZEN_API_KEY]&lt;/string&gt;
 * &lt;/resources&gt;
 * </pre>
 *
 * If an API key is set in code via {@link #setApiKey(String)} it will override the declared as a
 * string resource.
 *
 * Finally, if an API key is explicitly passed into a specific component when created it will
 * supersede any key stored by this class.
 *
 * <pre>
 *   MapzenSearch mapzenSearch = new MapzenSearch(context, "mapzen-XXXXXXX")
 * </pre>
 */
public class MapzenManager {
  public static final String API_KEY_RES_NAME = "mapzen_api_key";
  public static final String API_KEY_RES_TYPE = "string";
  public static final String API_KEY_PARAM_NAME = "api_key";
  public static final String API_KEY_DEFAULT_VALUE = "[YOUR_MAPZEN_API_KEY]";

  static MapzenManager instance;

  /**
   * Get singleton instance.
   */
  public static MapzenManager instance(Context context) {
    if (instance == null) {
      instance = new MapzenManager(context);
    }

    return instance;
  }

  private String apiKey;

  /**
   * Creates a new instance of the manager.
   */
  private MapzenManager(Context context) {
    final Resources resources = context.getResources();
    if (resources != null) {
      int id = resources.getIdentifier(API_KEY_RES_NAME, API_KEY_RES_TYPE,
          context.getPackageName());
      if (id > 0) {
        apiKey = resources.getString(id);
      }
    }
  }

  /**
   * Returns the currently active API key stored by this class.
   *
   * @throws IllegalStateException if a valid API key has not been set in code or as a string
   * resource.
   */
  public String getApiKey() {
    if (apiKey == null || API_KEY_DEFAULT_VALUE.equals(apiKey)) {
      throw new IllegalStateException("A valid Mapzen API key has not been provided. Please visit "
          + "https://mapzen.com/documentation/android/getting-started/ to learn how.");
    }

    return apiKey;
  }

  /**
   * Sets a new API key value. This will override any previous key including those declared in xml.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }
}
