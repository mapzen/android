package com.mapzen.android.graphics;

import com.mapzen.android.OkHttp3TestUtils;
import com.mapzen.android.core.CoreDI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static com.mapzen.TestHelper.getMockContext;
import okhttp3.internal.tls.CertificateChainCleaner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TrustManagerFactory.class, SSLContext.class, CertificateChainCleaner.class })
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

  private MapInitializer mapInitializer;

  @Before public void setUp() throws Exception {
    OkHttp3TestUtils.initMockSslContext();
    CoreDI.init(getMockContext());
    mapInitializer = new MapInitializer(mock(Context.class), mock(TileHttpHandler.class),
        new MapDataManager(), new MapStateManager());
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapInitializer).isNotNull();
  }

  @Test public void init_shouldReturnMapzenMap() throws Exception {
    final TestCallback callback = new TestCallback();
    final TestMapView mapView = new TestMapView();
    mapInitializer.init(mapView, "test_api_key", callback);
    assertThat(callback.map).isInstanceOf(MapzenMap.class);
  }
}
