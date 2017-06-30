package com.mapzen.android.core;

import java.util.HashMap;
import java.util.Map;

public class TestVals {

  public static Map<String, String> testParams() {
    Map<String, String> map = new HashMap<>();
    map.put("param1", "val1");
    map.put("param2", "val2");
    return map;
  }

  public static Map<String, String> testHeaders() {
    Map<String, String> map = new HashMap<>();
    map.put("header1", "val1");
    map.put("header2", "val2");
    return map;
  }
}
