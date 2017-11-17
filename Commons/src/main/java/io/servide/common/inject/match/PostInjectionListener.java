package io.servide.common.inject.match;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.util.function.Consumer;

public enum PostInjectionListener {

  ;

  public static TypeListener create(Consumer<Object> consumer) {
    return new TypeListener() {
      @Override
      public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        typeEncounter.register(new InjectionListener<I>() {
          @Override
          public void afterInjection(I i) {
            consumer.accept(i);
          }
        });
      }
    };
  }

}
