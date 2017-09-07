package com.mapzen.android.core;

import android.os.Build;

import java.util.Map;

/**
 * Generic SDK interface for service-specific handlers to implement.
 */
public interface GenericHttpHandler {

  String HEADER_USER_AGENT = "User-Agent";
  String USER_AGENT = "android-sdk;" + MapzenManager.getSdkVersion() + ";" + Build.VERSION.RELEASE;

  /**
   * Log levels for http requests.
   */
  enum LogLevel {
    /** No logs. */
    NONE,
    /**
     * Logs request and response lines.
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1 (3-byte body)
     *
     * <-- 200 OK (22ms, 6-byte body)
     * }</pre>
     */
    BASIC,
    /**
     * Logs request and response lines and their respective headers.
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
     * }</pre>
     */
    HEADERS,
    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     *
     * <p>Example:
     * <pre>{@code
     * --> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     *
     * Hi?
     * --> END POST
     *
     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     *
     * Hello!
     * <-- END HTTP
     * }</pre>
     */
    BODY
  }

  /**
   * Return query parameters to be appended to every request.
   * @return
   */
  Map<String, String> queryParamsForRequest();

  /**
   * Return headers to be added to every request.
   * @return
   */
  Map<String, String> headersForRequest();
}
