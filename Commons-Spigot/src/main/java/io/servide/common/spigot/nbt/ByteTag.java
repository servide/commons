package io.servide.common.spigot.nbt;

public final class ByteTag implements Tag {

  private final byte value;

  public ByteTag(byte value) {
    this.value = value;
  }

  @Override
  public Byte getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "TAG_Byte(" + this.value + ")";
  }

}
