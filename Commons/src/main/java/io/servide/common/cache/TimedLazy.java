package io.servide.common.cache;

import java.util.function.Supplier;

public final class TimedLazy<T> {

  private final Supplier<T> supplier;
  private final long timeout;

  private long validUntil;
  private T cachedValue;

  private TimedLazy(int timeout, Supplier<T> supplier) {
    this.supplier = supplier;
    this.timeout = timeout;
    this.validUntil = System.currentTimeMillis() + timeout;
  }

  public static <T> TimedLazy<T> of(int timeout, Supplier<T> supplier) {
    return new TimedLazy<>(timeout, supplier);
  }

  public T get() {
    if (System.currentTimeMillis() >= this.validUntil) {
      this.cachedValue = this.supplier.get();
      this.validUntil = System.currentTimeMillis() + this.timeout;
    }
    return this.cachedValue;
  }

}
