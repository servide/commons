package io.servide.common.spigot.entity;

import io.servide.common.except.Try;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Entity;

public enum EntityUtils {

  ;

  public static void registerEntity(Class<? extends Entity> entityClass, String name, int id,
      Class<?> entityTypesClass) {
    Map<String, Class<? extends Entity>> c = (Map<String, Class<? extends Entity>>) getMap(
        entityTypesClass, "c");
    Map<Class<? extends Entity>, String> d = (Map<Class<? extends Entity>, String>) getMap(
        entityTypesClass, "d");
    Map<Integer, Class<? extends Entity>> e = (Map<Integer, Class<? extends Entity>>) getMap(
        entityTypesClass, "e");
    Map<Class<? extends Entity>, Integer> f = (Map<Class<? extends Entity>, Integer>) getMap(
        entityTypesClass, "f");
    Map<String, Integer> g = (Map<String, Integer>) getMap(entityTypesClass, "g");
    c.put(name, entityClass);
    d.put(entityClass, name);
    e.put(id, entityClass);
    f.put(entityClass, id);
    g.put(name, id);
  }

  private static Object getMap(Class<?> entityTypesClass, String mapName) {
    Field f = Try.to(() -> entityTypesClass.getDeclaredField(mapName));
    f.setAccessible(true);
    return Try.to(() -> f.get(null));
  }

}
