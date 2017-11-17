package io.servide.common.inject.module;

import io.servide.common.name.Named;

@FunctionalInterface
public interface Enableable extends Named {

  void onEnable();

}
