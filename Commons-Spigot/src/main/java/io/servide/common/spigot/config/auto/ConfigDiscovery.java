package io.servide.common.spigot.config.auto;

import com.google.inject.Binder;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.servide.common.spigot.config.SimpleConfig;
import org.bukkit.plugin.Plugin;

public enum ConfigDiscovery {

  ;

  public static void inject(Plugin plugin, Binder binder) {
    ConfigDiscovery.scan(plugin).matchClassesWithAnnotation(Config.class,
        aClass -> SimpleConfig.bind(binder, plugin.getDataFolder(), aClass)).scan();
  }

  private static FastClasspathScanner scan(Plugin plugin) {
    return new FastClasspathScanner(
        plugin.getClass().getPackage().getName())
        .addClassLoader(ConfigDiscovery.class.getClassLoader());
  }

}