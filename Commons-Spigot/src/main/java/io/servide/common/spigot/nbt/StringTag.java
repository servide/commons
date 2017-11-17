package io.servide.common.spigot.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

public final class StringTag implements Tag {

  private final String value;

  public StringTag(String value) {
    checkNotNull(value);
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_String(" + this.value + ")";
  }

}
