package com.mapzen.android.graphics;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages {@link PersistableMapData} objects for handling recreation of
 * {@link com.mapzen.tangram.MapData} objects
 * across orientation changes.
 */
class MapDataManager {

  private Set<PersistableMapData> data = new HashSet<>();
  private boolean persistMapData = false;

  /**
   * Adds a {@link PersistableMapData} to the set of data to be restored.
   * @param mapData
   */
  public void addMapData(PersistableMapData mapData) {
    data.add(mapData);
  }

  /**
   * Removes a type of {@link PersistableMapData} from the set of data to be restored.
   * @param dataLayerType
   */
  public void removeMapData(DataLayerType dataLayerType) {
    for (PersistableMapData mapData : data) {
      if (mapData.getDataLayerType() == dataLayerType) {
        data.remove(mapData);
        break;
      }
    }

  }

  /**
   * Returns the set of {@link PersistableMapData} to be restored.
   * @return
   */
  public Set<PersistableMapData> getMapData() {
    return data;
  }

  public void setPersistMapData(boolean persist) {
    persistMapData = persist;
  }

  public boolean getPersistMapData() {
    return persistMapData;
  }
}
