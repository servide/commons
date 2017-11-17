package io.servide.common.spigot.nbt;

public final class ShortTag implements Tag {

  private final short value;

  public ShortTag(short value) {
    this.value = value;
  }

  @Override
  public Short getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Short(" + this.value + ")";
  }

}
