package io.servide.common.locale;

public interface BasicPlaceholder extends PlaceholderMessagePart<Object> {

  @Override
  default String handle(Object value) {
    return this.format(value);
  }

  @Override
  default String format(Object value) {
    return this.format();
  }

  String format();

  @Override
  default Class<Object> argumentType() {
    return Object.class;
  }

}
