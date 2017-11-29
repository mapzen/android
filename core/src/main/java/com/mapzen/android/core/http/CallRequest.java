package com.mapzen.android.core.http;

import retrofit2.Call;

/**
 * Default {@link Request} implementation.
 */
public class CallRequest implements Request {

  private Call call;

  /**
   * Public constructor.
   * @param call
   */
  public CallRequest(Call call) {
    this.call = call;
  }

  @Override public boolean isCanceled() {
    return call.isCanceled();
  }

  @Override public void cancel() {
    call.cancel();
  }
}
