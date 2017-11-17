package io.servide.common.valid;

import java.util.Arrays;
import java.util.Objects;

public class Is {

  public static boolean valid(Validated... objectsToCheck) {
    return Arrays.stream(objectsToCheck).allMatch(Objects::nonNull) && Arrays.stream(objectsToCheck)
        .allMatch(Validated::isValid);
  }

  public static boolean nonNull(Object... objectsToCheck) {
    return Arrays.stream(objectsToCheck).allMatch(Objects::nonNull);
  }

  public static boolean nonEmpty(String... stringsToCheck) {
    return Arrays.stream(stringsToCheck).noneMatch(String::isEmpty);
  }

}
