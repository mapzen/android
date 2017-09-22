package mapzen.com.sdksampleapp.models;

import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.routing.MapzenRouter;
import com.mapzen.android.search.MapzenSearch;

/**
 * Represents a feature of the SDK to demonstrate from within a {@link Section}.
 */
public abstract class Sample {

  public final String title;

  /**
   * Constructor.
   * @param title
   */
  public Sample(String title) {
    this.title = title;
  }

  /**
   * Called when the {@link Sample} has been selected. Use this method to setup state.
   * @param map
   * @param router
   * @param search
   */
  public abstract void setup(MapzenMap map, MapzenRouter router, MapzenSearch search);

  /**
   * Called when a new {@link Sample} has been selected. Use this method to cleanup state that
   * should not persist between samples.
   * @param map
   * @param router
   * @param search
   */
  public abstract void takedown(MapzenMap map, MapzenRouter router, MapzenSearch search);
}
