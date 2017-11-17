package io.servide.common.inject.initialize;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import io.servide.common.except.Try;
import io.servide.common.inject.match.PostInjectionListener;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class InitializerModule extends AbstractModule {

  @Override
  protected void configure() {
    this.bindListener(Matchers.any(), PostInjectionListener.create(this::initialize));
  }

  private void initialize(Object object) {
    Class<?> type = object.getClass();

    Stream.of(type.getDeclaredMethods())
        .filter(this::isInitializer)
        .forEach(method -> this.invokeInitializer(object, method));
  }

  private boolean isInitializer(Method method) {
    return method.isAnnotationPresent(Initialize.class);
  }

  private void invokeInitializer(Object instance, Method method) {
    Try.to(() -> {
      method.setAccessible(true);
      method.invoke(instance);
    });
  }

}
