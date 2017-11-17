package io.servide.common.spigot.inject.operate;

import io.servide.common.inject.operate.TypeOperator;
import io.servide.common.spigot.plugin.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerOperator implements TypeOperator<Listener> {

  @Override
  public void operate(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, PluginUtil.getPlugin());
  }

  @Override
  public Class<Listener> getType() {
    return Listener.class;
  }

}
