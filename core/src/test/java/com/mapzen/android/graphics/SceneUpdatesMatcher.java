package com.mapzen.android.graphics;

import com.mapzen.tangram.SceneUpdate;

import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * Custom Mockito matcher for a list of SceneUpdates. Verifies paths and values are all equal.
 */
class SceneUpdatesMatcher extends ArgumentMatcher<List<SceneUpdate>> {
  List<SceneUpdate> updates;

  public SceneUpdatesMatcher(SceneUpdate update) {
    this.updates = Arrays.asList(update);
  }

  public SceneUpdatesMatcher(List<SceneUpdate> updates) {
    this.updates = updates;
  }

  @Override public boolean matches(Object argument) {
    List<SceneUpdate> otherObject = (List<SceneUpdate>) argument;
    if (updates.size() != otherObject.size()) {
      return false;
    }
    for (int i = 0; i < updates.size(); i++) {
      if (!updateEqualToUpdate(updates.get(i), otherObject.get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean updateEqualToUpdate(SceneUpdate update, SceneUpdate otherUpdate) {
    if (!update.getPath().equals(otherUpdate.getPath())) {
      return false;
    }
    if (!update.getValue().equals(otherUpdate.getValue())) {
      return false;
    }
    return true;
  }
}
