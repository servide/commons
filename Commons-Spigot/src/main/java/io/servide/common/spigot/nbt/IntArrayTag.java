package io.servide.common.spigot.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

public final class IntArrayTag implements Tag {

  private final int[] value;

  public IntArrayTag(int[] value) {
    checkNotNull(value);
    this.value = value;
  }

  @Override
  public int[] getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    StringBuilder hex = new StringBuilder();

    for (int b : this.value) {
      String hexDigits = Integer.toHexString(b).toUpperCase();

      if (hexDigits.length() == 1) {
        hex.append("0");
      }

      hex.append(hexDigits).append(" ");
    }

    return "TAG_Int_Array(" + hex + ")";
  }

}
