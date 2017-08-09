package com.mapzen.android.graphics.internal;

import com.mapzen.android.graphics.model.EaseType;
import com.mapzen.tangram.MapController;

import java.util.HashMap;

/**
 * Converts between SDK {@link EaseType} and internal {@link MapController.EaseType}.
 */
public class EaseTypeConverter {

  public static final HashMap<EaseType, MapController.EaseType>
      EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE = new HashMap();

  static {
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.LINEAR, MapController.EaseType.LINEAR);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.CUBIC, MapController.EaseType.CUBIC);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.QUINT, MapController.EaseType.QUINT);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.SINE, MapController.EaseType.SINE);
  }
}
