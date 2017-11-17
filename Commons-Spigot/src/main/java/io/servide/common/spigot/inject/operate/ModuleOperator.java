package io.servide.common.spigot.inject.operate;

import com.google.inject.Inject;
import io.servide.common.inject.operate.TypeOperator;
import io.servide.common.spigot.inject.module.Module;
import io.servide.common.spigot.inject.plugin.PluginLogger;
import java.util.logging.Logger;

public class ModuleOperator implements TypeOperator<Module> {

  @Inject
  @PluginLogger
  private Logger logger;

  @Override
  public void operate(Module module) {
    this.logger.info("Enabled Module: [" + module.getName() + "]");
  }

  @Override
  public Class<Module> getType() {
    return Module.class;
  }

}
