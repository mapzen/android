package com.mapzen.android.core;

import com.mapzen.BuildConfig;
import com.mapzen.android.core.test.R;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.core.MapzenManager.API_KEY_RES_NAME;
import static com.mapzen.android.core.MapzenManager.API_KEY_RES_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BuildConfig.class)
public class MapzenManagerTest {
  @After public void tearDown() throws Exception {
    MapzenManager.instance = null;
  }

  @Test public void shouldNotBeNull() throws Exception {
    Context context = getMockContext();
    Resources resources = new TestResources();
    when(context.getResources()).thenReturn(resources);
    MapzenManager mapzenManager = MapzenManager.instance(context);
    assertThat(mapzenManager).isNotNull();
  }

  @Test(expected = IllegalStateException.class)
  public void getApiKey_shouldReturnThrowIfNotSet() throws Exception {
    Context context = getMockContext();
    Resources resources = new TestResources();
    when(context.getResources()).thenReturn(resources);
    MapzenManager mapzenManager = MapzenManager.instance(context);
    mapzenManager.getApiKey();
  }

  @Test(expected = IllegalStateException.class)
  public void getApiKey_shouldReturnThrowIfResourceNotFound() throws Exception {
    Context context = getMockContext();
    Resources resources = new TestResourcesNotFound();
    when(context.getResources()).thenReturn(resources);
    MapzenManager mapzenManager = MapzenManager.instance(context);
    mapzenManager.getApiKey();
  }

  @Test public void getApiKey_shouldReturnStringResourceValue() throws Exception {
    Context context = getMockContext();
    TestResources resources = new TestResources();
    resources.testApiKey = "mapzen-fake-api-key";
    when(context.getResources()).thenReturn(resources);
    MapzenManager mapzenManager = MapzenManager.instance(context);
    assertThat(mapzenManager.getApiKey()).isEqualTo("mapzen-fake-api-key");
  }

  @Test public void setApiKey_shouldOverrideStringResourcesValue() throws Exception {
    Context context = getMockContext();
    TestResources resources = new TestResources();
    resources.testApiKey = "mapzen-fake-api-key";
    when(context.getResources()).thenReturn(resources);
    MapzenManager mapzenManager = MapzenManager.instance(context);
    mapzenManager.setApiKey("mapzen-fake-api-key-2");
    assertThat(mapzenManager.getApiKey()).isEqualTo("mapzen-fake-api-key-2");
  }

  @Test public void getSdkVersion() throws Exception {
    assertThat(MapzenManager.getSdkVersion()).isNotEmpty();
  }

  private class TestResources extends Resources {
    private String testApiKey;

    TestResources() {
      super(null, null, null);
    }

    @Override public int getIdentifier(String name, String defType, String defPackage) {
      if (API_KEY_RES_NAME.equals(name) && API_KEY_RES_TYPE.equals(defType)) {
        return R.id.mapzen_api_key;
      }

      return 0;
    }

    @NonNull @Override public String getString(int id) throws NotFoundException {
      if (id == R.id.mapzen_api_key) {
        return testApiKey;
      }

      throw new NotFoundException();
    }
  }

  private class TestResourcesNotFound extends Resources {
    TestResourcesNotFound() {
      super(null, null, null);
    }

    @Override public int getIdentifier(String name, String defType, String defPackage) {
      return 0;
    }

    @NonNull @Override public String getString(int id) throws NotFoundException {
      throw new NotFoundException();
    }
  }
}
