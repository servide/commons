package io.servide.common.spigot.event;

import java.util.function.Function;
import org.bukkit.event.Event;

public interface EventMapping<E extends Event, T> extends Function<E, T> {

  static <E extends Event, T> EventMapping<E, T> create(Class<E> type, Function<E, T> mapping) {
    return new EventMapping<E, T>() {
      @Override
      public Class<E> eventType() {
        return type;
      }

      @Override
      public T apply(E event) {
        return mapping.apply(event);
      }
    };
  }

  Class<E> eventType();

}
