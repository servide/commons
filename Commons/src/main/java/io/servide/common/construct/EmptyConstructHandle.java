package io.servide.common.construct;

enum EmptyConstructHandle implements ConstructHandle {

  INSTANCE;

  @Override
  public Object invoke() {
    return null;
  }

}
