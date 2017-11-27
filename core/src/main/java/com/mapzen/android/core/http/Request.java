package com.mapzen.android.core.http;

/**
 * Generic interface for objects abstracting http requests.
 */
public interface Request {
  /**
   * Returns whether or not the request has been canceled.
   */
  boolean isCanceled();
  /**
   * Cancels the request.
   */
  void cancel();
}
