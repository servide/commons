package io.servide.common.cache;

import java.util.function.Supplier;

public final class Lazy<T> {

  private final Supplier<T> supplier;
  private boolean valid = false;
  private T cachedValue;

  private Lazy(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public static <T> Lazy<T> of(Supplier<T> supplier) {
    return new Lazy<>(supplier);
  }

  public T get() {
    if (!this.valid) {
      this.cachedValue = this.supplier.get();
      this.valid = true;
    }

    return this.cachedValue;
  }

  public void invalidate() {
    this.valid = false;
  }

}
