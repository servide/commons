package io.servide.common.inject.match;

import com.google.inject.matcher.AbstractMatcher;

public final class TypeMatcher extends AbstractMatcher<Object> {

  private final Class<?> type;

  private TypeMatcher(Class<?> type) {
    this.type = type;
  }

  public static TypeMatcher matchSubtypesOf(Class<?> type) {
    return new TypeMatcher(type);
  }

  @Override
  public boolean matches(Object value) {
    return this.type.isAssignableFrom(value.getClass());
  }

}
