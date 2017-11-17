package io.servide.common.construct;

import io.servide.common.except.Try;
import java.lang.invoke.MethodHandle;

final class MethodConstructHandle implements ConstructHandle {

  private final MethodHandle handle;

  MethodConstructHandle(MethodHandle handle) {
    this.handle = handle;
  }

  @Override
  public Object invoke() {
    return Try.to(this::invokeWithoutAmbiguousType);
  }

  private Object invokeWithoutAmbiguousType() throws Throwable {
    return this.handle.invokeExact();
  }

}
