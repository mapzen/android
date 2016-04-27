package com.mapzen.android;

import com.mapzen.android.model.MapStyle;

import java.util.HashMap;

/**
 * Links {@link MapStyle} enum entry to stylesheet scene file.
 */
public class MapStyleToSceneFile {

    private static final String BUBBLE_WRAP_SCENE_FILE = "styles/bubble-wrap/bubble-wrap.yaml";
    private static final String CINNABAR_SCENE_FILE = "styles/cinnabar/cinnabar-style.yaml";
    private static final String REFILL_SCENE_FILE = "styles/refill/refill-style.yaml";
    private static final String OUTDOOR_SCENE_FILE = "styles/outdoors/outdoor-style.yaml";
    private static final String GOTHAM_SCENE_FILE = "styles/gotham/gotham.yaml";
    private static final String BLUEPRINT_SCENE_FILE = "styles/blueprint/blueprint.yaml";

    /**
     * Initialize the static map.
     */
    public static final HashMap<MapStyle, String> MAP_STYLE_TO_SCENE_FILE = new HashMap<>();

    static
    {
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.BUBBLE_WRAP, BUBBLE_WRAP_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.CINNABAR, CINNABAR_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.REFILL, REFILL_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.OUTDOOR, OUTDOOR_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.GOTHAM, GOTHAM_SCENE_FILE);
        MAP_STYLE_TO_SCENE_FILE.put(MapStyle.BLUEPRINT, BLUEPRINT_SCENE_FILE);
    }
}
