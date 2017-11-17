package io.servide.common.spigot.nbt;

public final class ByteArrayTag implements Tag {

  private final byte[] value;

  public ByteArrayTag(byte[] value) {
    this.value = value;
  }

  @Override
  public byte[] getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    StringBuilder hex = new StringBuilder();

    for (byte b : this.value) {
      String hexDigits = Integer.toHexString(b).toUpperCase();

      if (hexDigits.length() == 1) {
        hex.append("0");
      }

      hex.append(hexDigits).append(" ");
    }

    return "TAG_Byte_Array(" + hex + ")";
  }

}
