package io.servide.common.server;

public interface PlayerServer extends Server {

  int softCap();

  int hardCap();

  boolean isCap();

}
