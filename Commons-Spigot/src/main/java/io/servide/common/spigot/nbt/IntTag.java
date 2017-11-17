package io.servide.common.spigot.nbt;

public final class IntTag implements Tag {

  private final int value;

  public IntTag(int value) {
    this.value = value;
  }

  @Override
  public Integer getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Int(" + this.value + ")";
  }

}
