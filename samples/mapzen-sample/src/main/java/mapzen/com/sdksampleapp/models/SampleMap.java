package mapzen.com.sdksampleapp.models;

import android.util.SparseArray;

import mapzen.com.sdksampleapp.R;
import static mapzen.com.sdksampleapp.models.MapSampleList.MAP_SAMPLES;

/**
 * Maps navigation ids to their list of {@link Sample}s.
 */
public class SampleMap {

  private SampleMap() { }

  public static final SparseArray<Sample[]> NAV_ID_TO_SECTIONS;
  static {
    NAV_ID_TO_SECTIONS = new SparseArray<>(4);
    NAV_ID_TO_SECTIONS.append(R.id.navigation_map, MAP_SAMPLES);
    NAV_ID_TO_SECTIONS.append(R.id.navigation_route, null);
    NAV_ID_TO_SECTIONS.append(R.id.navigation_search, null);
    NAV_ID_TO_SECTIONS.append(R.id.navigation_more, null);
  }

}
