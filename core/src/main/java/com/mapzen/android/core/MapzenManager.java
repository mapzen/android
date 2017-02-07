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
    int id = resources.getIdentifier(API_KEY_RES_NAME, API_KEY_RES_TYPE, context.getPackageName());
    if (id > 0) {
      apiKey = resources.getString(id);
    }
  }

  /**
   * Returns the currently active API key stored by this class.
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Sets a new API key value. This will override any previous key including those declared in xml.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }
}
