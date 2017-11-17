package io.servide.common.except;

public enum Try {

  ;

  public static void to(CheckedRunnable runnable) {
    try {
      runnable.run();
    } catch (Throwable throwable) {
      throw Try.propagate(throwable);
    }
  }

  public static <T> T to(CheckedSupplier<T> supplier) {
    try {
      return supplier.get();
    } catch (Throwable throwable) {
      throw Try.propagate(throwable);
    }
  }

  private static RuntimeException propagate(Throwable throwable) {
    return throwable instanceof RuntimeException ? (RuntimeException) throwable
        : new RuntimeException(throwable);
  }

  @FunctionalInterface
  public interface CheckedRunnable {

    void run() throws Throwable;

  }

  @FunctionalInterface
  public interface CheckedSupplier<T> {

    T get() throws Throwable;

  }

}
