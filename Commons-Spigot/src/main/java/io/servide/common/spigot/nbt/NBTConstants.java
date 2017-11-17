package io.servide.common.spigot.nbt;

import java.nio.charset.Charset;

public enum NBTConstants {

  ;

  public static final Charset CHARSET = Charset.forName("UTF-8");

  public static final int TYPE_END = 0, TYPE_BYTE = 1, TYPE_SHORT = 2,
      TYPE_INT = 3, TYPE_LONG = 4, TYPE_FLOAT = 5, TYPE_DOUBLE = 6,
      TYPE_BYTE_ARRAY = 7, TYPE_STRING = 8, TYPE_LIST = 9,
      TYPE_COMPOUND = 10, TYPE_INT_ARRAY = 11;

  public static Class<? extends Tag> getClassFromType(int id) {
    switch (id) {
      case TYPE_END:
        return EndTag.class;
      case TYPE_BYTE:
        return ByteTag.class;
      case TYPE_SHORT:
        return ShortTag.class;
      case TYPE_INT:
        return IntTag.class;
      case TYPE_LONG:
        return LongTag.class;
      case TYPE_FLOAT:
        return FloatTag.class;
      case TYPE_DOUBLE:
        return DoubleTag.class;
      case TYPE_BYTE_ARRAY:
        return ByteArrayTag.class;
      case TYPE_STRING:
        return StringTag.class;
      case TYPE_LIST:
        return ListTag.class;
      case TYPE_COMPOUND:
        return CompoundTag.class;
      case TYPE_INT_ARRAY:
        return IntArrayTag.class;
      default:
        throw new IllegalArgumentException("Unknown tag type ID of " + id);
    }
  }

}
