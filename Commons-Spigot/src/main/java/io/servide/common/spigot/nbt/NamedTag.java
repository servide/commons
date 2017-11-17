package io.servide.common.spigot.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

public class NamedTag {

  private final String name;
  private final Tag tag;

  public NamedTag(String name, Tag tag) {
    checkNotNull(name);
    checkNotNull(tag);
    this.name = name;
    this.tag = tag;
  }

  public String getName() {
    return this.name;
  }

  public Tag getTag() {
    return this.tag;
  }

}
