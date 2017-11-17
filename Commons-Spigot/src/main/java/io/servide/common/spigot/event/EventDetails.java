package io.servide.common.spigot.event;

import org.bukkit.event.EventPriority;

public final class EventDetails {

  private EventPriority eventPriority;
  private boolean ignoreCancelled;
  private long timesCalled;
  private long startedListening;

  private EventDetails(EventPriority eventPriority, boolean ignoreCancelled, long timesCalled) {
    this.eventPriority = eventPriority;
    this.ignoreCancelled = ignoreCancelled;
    this.timesCalled = timesCalled;
  }

  public static EventDetails create(EventPriority eventPriority, boolean ignoreCancelled,
      long timesCalled) {
    return new EventDetails(eventPriority, ignoreCancelled, timesCalled);
  }

  public static EventDetails basic() {
    return new EventDetails(EventPriority.NORMAL, true, 0L);
  }

  public EventPriority getEventPriority() {
    return this.eventPriority;
  }

  public void setEventPriority(EventPriority eventPriority) {
    this.eventPriority = eventPriority;
  }

  public boolean isIgnoreCancelled() {
    return this.ignoreCancelled;
  }

  public void setIgnoreCancelled(boolean ignoreCancelled) {
    this.ignoreCancelled = ignoreCancelled;
  }

  public long getTimesCalled() {
    return this.timesCalled;
  }

  public void addTimesCalled(long timesCalled) {
    this.timesCalled += timesCalled;
  }

  public long getStartedListening() {
    return this.startedListening;
  }

  public void setStartedListening(long startedListening) {
    this.startedListening = startedListening;
  }
}
