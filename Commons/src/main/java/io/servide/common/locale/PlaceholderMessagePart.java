package io.servide.common.locale;

public interface PlaceholderMessagePart<T> extends MessagePart {

  default String handle(Object value) {
    if (!value.getClass().isAssignableFrom(this.argumentType())) {
      return this.escapedPlaceholder();
    }

    @SuppressWarnings("unchecked")
    String formatted = this.format((T) value);

    return formatted;
  }

  String format(T value);

  Class<T> argumentType();

  String placeholderName();

  @Override
  default String text() {
    return this.placeholderName();
  }

  default String escapedPlaceholder() {
    return Placeholders.escapePlaceholder(this.placeholderName());
  }

  @Override
  default boolean raw() {
    return false;
  }

}
