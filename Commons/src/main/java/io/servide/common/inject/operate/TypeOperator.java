package io.servide.common.inject.operate;

public interface TypeOperator<T> {

  void operate(T value);

  Class<T> getType();

}
