package com.mapzen.android.graphics;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton to manage restoring {@link MapzenMap} state after orientation changes.
 */
public class PersistDataManagers {

  private static PersistDataManagers instance;

  private Map<String, MapStateManager> mapStateManagers = new HashMap<>();
  private MapStateManager defaultMapStateManager = new MapStateManager();
  private Map<String, MapDataManager> mapDataManagers = new HashMap<>();
  private MapDataManager defaultMapDataManager = new MapDataManager();

  /**
   * Get singleton instance.
   */
  public static PersistDataManagers instance() {
    if (instance == null) {
      instance = new PersistDataManagers();
    }

    return instance;
  }

  /**
   * Returns the manager for a given map id. If the id passed is null, the global default manager
   * is returned.
   * @param mapId
   * @return
   */
  public MapStateManager getMapStateManager(String mapId) {
    if (mapId == null) {
      return defaultMapStateManager;
    }
    MapStateManager manager = mapStateManagers.get(mapId);
    if (manager == null) {
      manager = new MapStateManager();
      mapStateManagers.put(mapId, manager);
    }
    return manager;
  }

  /**
   * Returns the global default manager.
   * is returned.
   * @return
   */
  public MapStateManager getDefaultMapStateManager() {
    return defaultMapStateManager;
  }

  /**
   * Returns the manager for a given map id. If the id passed is null, the global default manager
   * is returned.
   * @param mapId
   * @return
   */
  public MapDataManager getMapDataManager(String mapId) {
    if (mapId == null) {
      return defaultMapDataManager;
    }
    MapDataManager manager = mapDataManagers.get(mapId);
    if (manager == null) {
      manager = new MapDataManager();
      mapDataManagers.put(mapId, manager);
    }
    return manager;
  }

  /**
   * Returns the global default manager.
   * is returned.
   * @return
   */
  public MapDataManager getDefaultMapDataManager() {
    return defaultMapDataManager;
  }
}
