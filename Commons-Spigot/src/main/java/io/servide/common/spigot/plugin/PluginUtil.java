package io.servide.common.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public enum PluginUtil {

  ;

  public static JavaPlugin getPlugin() {
    return JavaPlugin.getProvidingPlugin(PluginUtil.class);
  }

}
