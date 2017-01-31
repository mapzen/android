package com.mapzen.android;

import org.powermock.api.mockito.PowerMockito;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.internal.tls.CertificateChainCleaner;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Mocks static methods used in {@code okhttp3.OkHttpClient.<init>} so OkHttp3 client builder can
 * safely be invoked from JVM unit tests.
 * <p/>
 * The following {@code PowerMock} annotations must be added to the test class declaration:
 * <pre>
 * &#064;RunWith(PowerMockRunner.class)
 * &#064;PrepareForTest({ TrustManagerFactory.class, SSLContext.class, CertificateChainCleaner.class })
 * </pre>
 */
public final class OkHttp3TestUtils {
  public static void initMockSslContext() throws KeyStoreException, NoSuchAlgorithmException {
    final TrustManagerFactory trustManagerFactory = PowerMockito.mock(TrustManagerFactory.class);
    final TrustManager[] trustManagers = new TrustManager[1];

    // Mock TrustManagerFactory.getInstance(String)
    mockStatic(TrustManagerFactory.class);
    when(TrustManagerFactory.getInstance(anyString())).thenReturn(trustManagerFactory);

    // Mock TrustManagerFactory#getTrustManagers()
    trustManagers[0] = PowerMockito.mock(X509TrustManager.class);
    doNothing().when(trustManagerFactory).init(any(KeyStore.class));
    when(trustManagerFactory.getTrustManagers()).thenReturn(trustManagers);

    // Mock SSLContext.getInstance(String)
    mockStatic(SSLContext.class);
    when(SSLContext.getInstance(anyString())).thenReturn(PowerMockito.mock(SSLContext.class));

    // Mock CertificateChainCleaner.get(X509TrustManager)
    mockStatic(CertificateChainCleaner.class);
    when(CertificateChainCleaner.get(any(X509TrustManager.class)))
        .thenReturn(PowerMockito.mock(CertificateChainCleaner.class));
  }
}
