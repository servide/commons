package io.servide.common.cache;

import java.util.function.Supplier;

public final class Lazy<T> {

  private final Supplier<T> supplier;
  private boolean toSupply = true;
  private T value;

  private Lazy(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public static <T> Lazy<T> of(Supplier<T> supplier) {
    return new Lazy<>(supplier);
  }

  public T get() {
    if (this.toSupply) {
      this.value = this.supplier.get();
      this.toSupply = false;
    }

    return this.value;
  }

}
