package io.servide.common.spigot.event;

import io.servide.common.valid.If;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public final class EventHandler<T> {

  private final List<EventMapping<?, T>> mappings = new ArrayList<>();
  private final Plugin plugin;
  private final EventDetails eventDetails = EventDetails.basic();
  private final EventLogic<T> eventLogic;
  private final Listener listener = new Listener() {
  };
  private Consumer<T> eventConsumer;
  private boolean expired = false;

  private EventHandler(Plugin plugin) {
    this.plugin = plugin;
    this.eventLogic = EventLogic.basic();
  }

  static <T> EventHandler<T> create(Plugin plugin) {
    return new EventHandler<>(plugin);
  }

  public EventHandler<T> filter(Predicate<T> predicate) {
    this.eventLogic.addFilter(predicate);
    return this;
  }

  public EventHandler<T> expireif(Predicate<T> predicate) {
    this.eventLogic.addExpireCondition(predicate);
    return this;
  }

  public EventHandler<T> expireAfter(long value, TimeUnit timeUnit) {
    this.eventLogic.setExpireAfterMillis(timeUnit.toMillis(value));
    return this;
  }

  public EventHandler<T> expireAfter(long eventCalls) {
    this.eventLogic.setExpireAfterCalls(eventCalls);
    return this;
  }

  public <E extends Event> EventHandler<T> bindEvent(Class<E> type, Function<E, T> mapping) {
    this.mappings.add(EventMapping.create(type, mapping));
    return this;
  }

  public void handle(Consumer<T> handler) {
    this.eventConsumer = handler;
    this.eventDetails.setStartedListening(System.currentTimeMillis());
    this.registerWithBukkit();
  }

  private void registerWithBukkit() {
    this.mappings.stream()
        .map(EventMapping::eventType)
        .forEach(this::registerWithBukkit);
  }

  private void registerWithBukkit(Class<? extends Event> eventType) {
    Bukkit.getPluginManager().registerEvent(eventType, this.listener,
        this.eventDetails.getEventPriority(), this::execute, this.plugin, false);
  }

  private void removeFromBukkit() {
    HandlerList.unregisterAll(this.listener);
    this.expired = true;
  }

  private void removeFromBukkitIf(boolean condition) {
    If.trueDo(condition, this::removeFromBukkit);
  }

  private <E extends Event> void execute(Listener listener, E event) throws EventException {
    long timeToExpire =
        this.eventDetails.getStartedListening() + this.eventLogic.getExpireAfterMillis();

    @SuppressWarnings("unchecked")
    EventMapping<E, T> mapping = (EventMapping<E, T>) this.mappings.stream()
        .filter(map -> map.eventType().isAssignableFrom(event.getClass()))
        .findFirst()
        .orElse(null);

    if (mapping == null) {
      return;
    }

    T value = mapping.apply(event);

    if (!this.eventLogic.runThroughFilters(value)) {
      return;
    }

    this.removeFromBukkitIf(this.eventLogic.getExpireAfterMillis() > 0 &&
        (System.currentTimeMillis() - timeToExpire) > 0);

    this.removeFromBukkitIf(this.eventLogic.getExpireAfterCalls() > 0 &&
        this.eventLogic.getExpireAfterCalls() <= this.eventDetails.getTimesCalled());

    this.removeFromBukkitIf(!this.eventLogic.runThroughExpireConditions(value));

    If.falseDo(this.expired, () -> {
      this.eventDetails.addTimesCalled(1);
      this.eventConsumer.accept(value);
    });

  }

}
