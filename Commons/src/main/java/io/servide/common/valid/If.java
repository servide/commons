package io.servide.common.valid;

import java.util.function.Consumer;
import java.util.function.Function;

public class If {

  public static <T extends Validated> T validReturn(T validated, T fallback) {
    return Is.valid(validated) ? validated : fallback;
  }

  public static <T extends Validated, R> R validDoReturn(T validated, Function<T, R> function,
      R fallback) {
    if (Is.valid(validated)) {
      return function.apply(validated);
    }
    return fallback;
  }

  public static <T extends Validated> boolean validDo(T validated, Consumer<T> consumer) {
    boolean valid = Is.valid(validated);
    if (valid) {
      consumer.accept(validated);
    }
    return valid;
  }

  public static boolean trueDo(boolean condition, Runnable runnable) {
    if (condition) {
      runnable.run();
    }
    return condition;
  }

  public static boolean falseDo(boolean condition, Runnable runnable) {
    return If.trueDo(!condition, runnable);
  }

}
