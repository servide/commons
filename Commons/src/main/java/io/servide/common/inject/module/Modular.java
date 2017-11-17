package io.servide.common.inject.module;

import java.util.List;

public interface Modular extends Enableable {

  void install(Class<?> type);

  Modular parent();

  List<Modular> children();

  boolean isEnabled();

  void discoverIfIsEnabled();

}
