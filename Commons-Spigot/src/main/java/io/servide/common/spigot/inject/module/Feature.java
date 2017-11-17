package io.servide.common.spigot.inject.module;

import com.google.inject.Inject;
import io.servide.common.inject.module.Enableable;
import io.servide.common.spigot.inject.plugin.PluginLogger;
import java.util.logging.Logger;

public class Feature implements Enableable {

  @Inject
  @PluginLogger
  private Logger logger;

  @Override
  public final void onEnable() {
    this.enable();
    this.logger.info("Enabled Feature [" + this.getName() + "]");
  }

  public void enable() {

  }

}
