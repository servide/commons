package io.servide.common.spigot.plugin.auto;

public @interface PluginDependency {

  String value();

  boolean soft() default false;

}
