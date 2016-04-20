package com.mapzen.android;

import com.mapzen.android.model.MapStyle;

import java.util.HashMap;

/**
 * Mapping for {@link MapStyle} to stylesheet scene file.
 */
public class MapStyleToSceneFile {

    private static final String BUBBLE_WRAP_SCENE_FILE = "style/bubble-wrap.yaml";
    private static final String OUTDOOR_SCENE_FILE = "outdoor-style/outdoor-style.yaml";

    /**
     * Initialize the static map.
     */
    public static final HashMap<MapStyle, String> MAP_STYLE_TO_SCENE_FILE;
    static
    {
        MAP_STYLE_TO_SCENE_FILE = new HashMap();
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.BUBBLE_WRAP, BUBBLE_WRAP_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.OUTDOOR, OUTDOOR_SCENE_FILE);
    }
}
