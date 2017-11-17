package io.servide.common.spigot.plugin.auto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.plugin.PluginLoadOrder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Plugin {

  String name() default "";

  String version() default "";

  String description() default "";

  PluginLoadOrder load() default PluginLoadOrder.POSTWORLD;

  String[] authors() default {};

  String website() default "";

  PluginDependency[] depends() default {};

  String[] hardDepends() default {};

  String[] softDepends() default {};

  String[] loadBefore() default {};

}
