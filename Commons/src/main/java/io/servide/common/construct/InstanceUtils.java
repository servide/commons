package io.servide.common.construct;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public enum InstanceUtils {

  ;

  private static final Map<Class<?>, ConstructHandle> CONSTRUCTORS =
      Collections.synchronizedMap(new IdentityHashMap<>());

  public static <T> T createOrNull(Class<T> request) {
    Objects.requireNonNull(request);

    if (request.isEnum()) {
      return InstanceUtils.getFirstEnumValueOrNull(request);
    }

    @SuppressWarnings("unchecked")
    T instance = (T) InstanceUtils.getOrCreateConstructor(request).invoke();
    return instance;
  }

  private static <E> E getFirstEnumValueOrNull(Class<E> request) {
    E[] constants = request.getEnumConstants();

    if (constants.length == 0) {
      return null;
    }

    return constants[0];
  }

  private static ConstructHandle getOrCreateConstructor(Class<?> request) {
    return InstanceUtils.CONSTRUCTORS.computeIfAbsent(request, InstanceUtils::createConstructor);
  }

  private static ConstructHandle createConstructor(Class<?> request) {
    try {
      Constructor<?> constructor = request.getDeclaredConstructor();
      constructor.setAccessible(true);

      MethodHandle handle = MethodHandles.lookup().unreflectConstructor(constructor)
          .asType(MethodType.methodType(Object.class));

      return new MethodConstructHandle(handle);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException ignore) {
      return EmptyConstructHandle.INSTANCE;
    }
  }

}
