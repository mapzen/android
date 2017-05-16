package com.mapzen.android.graphics;

import com.mapzen.tangram.SceneUpdate;

import org.mockito.ArgumentMatcher;

/**
 * Custom Mockito matcher for a list of SceneUpdates. Verifies paths and values are all equal.
 */
class SceneUpdateMatcher extends ArgumentMatcher<SceneUpdate> {
  SceneUpdate update;

  public SceneUpdateMatcher(SceneUpdate update) {
    this.update = update;
  }

  @Override public boolean matches(Object argument) {
    SceneUpdate otherObject = (SceneUpdate) argument;
    return updateEqualToUpdate(update, otherObject);
  }

  static boolean updateEqualToUpdate(SceneUpdate update, SceneUpdate otherUpdate) {
    if (!update.getPath().equals(otherUpdate.getPath())) {
      return false;
    }
    if (!update.getValue().equals(otherUpdate.getValue())) {
      return false;
    }
    return true;
  }
}
