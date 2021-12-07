package com.disqo.exchangerate.utility;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ApiCallTimeout {

  private final long timeout;
  private final TimeUnit timeUnit;

  public ApiCallTimeout(long timeout, TimeUnit timeUnit) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  public long getTimeout() {
    return timeout;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public static ApiCallTimeout MILLISECONDS(long timeout) {
    return new ApiCallTimeout(timeout, MILLISECONDS);
  }

  public static ApiCallTimeout SECONDS(long timeout) {
    return new ApiCallTimeout(timeout, SECONDS);
  }
}
