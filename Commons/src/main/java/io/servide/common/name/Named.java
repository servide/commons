package io.servide.common.name;

public interface Named {

  default String getName() {
    return Names.getName(this.getClass());
  }

}
