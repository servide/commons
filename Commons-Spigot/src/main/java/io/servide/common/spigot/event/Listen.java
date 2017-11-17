package io.servide.common.spigot.event;

import java.util.function.Function;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Listen {

  public static <T extends Event> EventHandler<T> to(Class<T> eventType) {
    Plugin plugin = JavaPlugin.getProvidingPlugin(Listen.class);

    return EventHandler.<T>create(plugin).bindEvent(eventType, Function.identity());
  }

  public static <T> EventHandler<T> binding(Class<T> binding) {
    Plugin plugin = JavaPlugin.getProvidingPlugin(Listen.class);

    return EventHandler.create(plugin);
  }

}
