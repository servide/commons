package io.servide.common.spigot.nbt;

public final class DoubleTag implements Tag {

  private final double value;

  public DoubleTag(double value) {
    this.value = value;
  }

  @Override
  public Double getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Double(" + this.value + ")";
  }

}
