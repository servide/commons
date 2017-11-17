package io.servide.common.spigot.nbt;

public final class LongTag implements Tag {

  private final long value;

  public LongTag(long value) {
    this.value = value;
  }

  @Override
  public Long getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Long(" + this.value + ")";
  }

}
