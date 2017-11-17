package io.servide.common.inject.discover;

import com.google.inject.Injector;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.servide.common.inject.operate.OperatorRegistry;
import io.servide.common.inject.operate.TypeOperator;

public enum Discovery {

  ;

  public static void discover(Injector injector) {
    Discovery.findAndRegisterTypeOperators(injector);
    Discovery.discoverTypes(injector);
  }

  private static void findAndRegisterTypeOperators(Injector injector) {
    Discovery.scan()
        .matchClassesImplementing(TypeOperator.class, operatorType ->
            OperatorRegistry.registerOperator(injector.getInstance(operatorType)))
        .scan();
  }

  private static void discoverTypes(Injector injector) {
    Discovery.scan()
        .matchClassesWithAnnotation(Discover.class,
            match -> Discovery.discoverMatch(injector, match))
        .scan();
  }

  private static void discoverMatch(Injector injector, Class<?> match) {
    Object instance = injector.getInstance(match);
    OperatorRegistry.operateOn(instance);
  }

  private static FastClasspathScanner scan() {
    return new FastClasspathScanner().addClassLoader(Discovery.class.getClassLoader());
  }

}
