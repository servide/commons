package io.servide.common.spigot.event;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;

public final class EventLogic<T> {

  private final List<Predicate<T>> filters = Lists.newArrayList();
  private final List<Predicate<T>> expirationConditions = Lists.newArrayList();
  private long expireAfterMillis = -1;
  private long expireAfterCalls = -1;

  private EventLogic() {

  }

  public static <T> EventLogic<T> basic() {
    return new EventLogic<>();
  }

  public List<Predicate<T>> getFilters() {
    return Lists.newArrayList(this.filters);
  }

  public List<Predicate<T>> getExpirationConditions() {
    return Lists.newArrayList(this.expirationConditions);
  }

  public long getExpireAfterMillis() {
    return this.expireAfterMillis;
  }

  public void setExpireAfterMillis(long expireAfterMillis) {
    this.expireAfterMillis = expireAfterMillis;
  }

  public long getExpireAfterCalls() {
    return this.expireAfterCalls;
  }

  public void setExpireAfterCalls(long getExpireAfterCalls) {
    this.expireAfterCalls = getExpireAfterCalls;
  }

  public void addFilter(Predicate<T> predicate) {
    this.filters.add(predicate);
  }

  public void addExpireCondition(Predicate<T> predicate) {
    this.expirationConditions.add(predicate);
  }

  public boolean runThroughFilters(T event) {
    return this.filters.stream().allMatch(predicate -> predicate.test(event));
  }

  public boolean runThroughExpireConditions(T event) {
    return this.expirationConditions.stream().noneMatch(predicate -> predicate.test(event));
  }

}
