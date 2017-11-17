package io.servide.common.spigot.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ListTagBuilder {

  private final Class<? extends Tag> type;
  private final List<Tag> entries;

  ListTagBuilder(Class<? extends Tag> type) {
    checkNotNull(type);
    this.type = type;
    this.entries = new ArrayList<>();
  }

  public static ListTagBuilder create(Class<? extends Tag> type) {
    return new ListTagBuilder(type);
  }

  public static <T extends Tag> ListTagBuilder createWith(T... entries) {
    checkNotNull(entries);

    if (entries.length == 0) {
      throw new IllegalArgumentException("This method needs an array of at least one entry");
    }

    Class<? extends Tag> type = entries[0].getClass();

    for (int i = 1; i < entries.length; i++) {
      if (!type.isInstance(entries[i])) {
        throw new IllegalArgumentException("An array of different tag types was provided");
      }
    }

    ListTagBuilder builder = new ListTagBuilder(type);

    builder.addAll(Arrays.asList(entries));

    return builder;
  }

  public ListTagBuilder add(Tag value) {
    checkNotNull(value);

    if (!this.type.isInstance(value)) {
      throw new IllegalArgumentException(
          value.getClass().getCanonicalName() + " is not of expected type " + this.type
              .getCanonicalName());
    }

    this.entries.add(value);

    return this;
  }

  public ListTagBuilder addAll(Collection<? extends Tag> value) {
    checkNotNull(value);
    value.forEach(this::add);

    return this;
  }

  public ListTag build() {
    return new ListTag(this.type, new ArrayList<>(this.entries));
  }

}
