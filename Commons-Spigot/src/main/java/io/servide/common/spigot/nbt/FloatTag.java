package io.servide.common.spigot.nbt;

public final class FloatTag implements Tag {

  private final float value;

  public FloatTag(float value) {
    this.value = value;
  }

  @Override
  public Float getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Float(" + this.value + ")";
  }

}
