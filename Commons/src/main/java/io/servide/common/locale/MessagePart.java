package io.servide.common.locale;

@FunctionalInterface
public interface MessagePart {

  String text();

  default boolean raw() {
    return true;
  }

}
