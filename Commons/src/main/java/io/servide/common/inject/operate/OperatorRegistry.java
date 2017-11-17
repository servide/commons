package io.servide.common.inject.operate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum OperatorRegistry {

  ;

  private static final Set<TypeOperator<?>> OPERATORS = new HashSet<>();

  public static void registerOperator(TypeOperator<?> operator) {
    OperatorRegistry.OPERATORS.add(operator);
  }

  public static <T> Optional<TypeOperator<T>> getOperator(Class<T> type) {
    @SuppressWarnings("unchecked")
    Optional<TypeOperator<T>> value = OperatorRegistry.OPERATORS.stream()
        .filter(operator -> operator.getType().isAssignableFrom(type))
        .map(operator -> (TypeOperator<T>) operator)
        .findFirst();

    return value;
  }

  public static <T> void operateOn(T value) {
    @SuppressWarnings("unchecked")
    Optional<TypeOperator<T>> optional = OperatorRegistry.getOperator((Class<T>) value.getClass());

    optional.ifPresent(operator -> operator.operate(value));
  }

}
