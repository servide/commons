package io.servide.common.spigot.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public final class ListTag implements Tag {

  private final Class<? extends Tag> type;
  private final List<Tag> value;

  public ListTag(Class<? extends Tag> type, List<? extends Tag> value) {
    checkNotNull(value);
    this.type = type;
    this.value = Collections.unmodifiableList(value);
  }

  public Class<? extends Tag> getType() {
    return this.type;
  }

  @Override
  public List<Tag> getValue() {
    return this.value;
  }

  public ListTag setValue(List<Tag> list) {
    return new ListTag(this.getType(), list);
  }

  public Tag getIfExists(int index) {
    try {
      return this.value.get(index);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  public byte[] getByteArray(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ByteArrayTag) {
      return ((ByteArrayTag) tag).getValue();
    } else {
      return new byte[0];
    }
  }

  public byte getByte(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ByteTag) {
      return ((ByteTag) tag).getValue();
    } else {
      return (byte) 0;
    }
  }

  public double getDouble(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof DoubleTag) {
      return ((DoubleTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public double asDouble(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ByteTag) {
      return ((ByteTag) tag).getValue();
    } else if (tag instanceof ShortTag) {
      return ((ShortTag) tag).getValue();
    } else if (tag instanceof IntTag) {
      return ((IntTag) tag).getValue();
    } else if (tag instanceof LongTag) {
      return ((LongTag) tag).getValue();
    } else if (tag instanceof FloatTag) {
      return ((FloatTag) tag).getValue();
    } else if (tag instanceof DoubleTag) {
      return ((DoubleTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public float getFloat(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof FloatTag) {
      return ((FloatTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public int[] getIntArray(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof IntArrayTag) {
      return ((IntArrayTag) tag).getValue();
    } else {
      return new int[0];
    }
  }

  public int getInt(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof IntTag) {
      return ((IntTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public int asInt(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ByteTag) {
      return ((ByteTag) tag).getValue();
    } else if (tag instanceof ShortTag) {
      return ((ShortTag) tag).getValue();
    } else if (tag instanceof IntTag) {
      return ((IntTag) tag).getValue();
    } else if (tag instanceof LongTag) {
      return ((LongTag) tag).getValue().intValue();
    } else if (tag instanceof FloatTag) {
      return ((FloatTag) tag).getValue().intValue();
    } else if (tag instanceof DoubleTag) {
      return ((DoubleTag) tag).getValue().intValue();
    } else {
      return 0;
    }
  }

  public List<Tag> getList(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ListTag) {
      return ((ListTag) tag).getValue();
    } else {
      return Collections.emptyList();
    }
  }

  public ListTag getListTag(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ListTag) {
      return (ListTag) tag;
    } else {
      return new ListTag(StringTag.class, Collections.emptyList());
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> List<T> getList(int index, Class<T> listType) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ListTag) {
      ListTag listTag = (ListTag) tag;

      if (listTag.getType().equals(listType)) {
        return (List<T>) listTag.getValue();
      } else {
        return Collections.emptyList();
      }
    } else {
      return Collections.emptyList();
    }
  }

  public long getLong(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof LongTag) {
      return ((LongTag) tag).getValue();
    } else {
      return 0L;
    }
  }

  public long asLong(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ByteTag) {
      return ((ByteTag) tag).getValue();
    } else if (tag instanceof ShortTag) {
      return ((ShortTag) tag).getValue();
    } else if (tag instanceof IntTag) {
      return ((IntTag) tag).getValue();
    } else if (tag instanceof LongTag) {
      return ((LongTag) tag).getValue();
    } else if (tag instanceof FloatTag) {
      return ((FloatTag) tag).getValue().longValue();
    } else if (tag instanceof DoubleTag) {
      return ((DoubleTag) tag).getValue().longValue();
    } else {
      return 0;
    }
  }

  public short getShort(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof ShortTag) {
      return ((ShortTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public String getString(int index) {
    Tag tag = this.getIfExists(index);

    if (tag instanceof StringTag) {
      return ((StringTag) tag).getValue();
    } else {
      return "";
    }
  }

  @Override
  public String toString() {
    StringBuilder bldr = new StringBuilder();

    bldr.append("TAG_List")
        .append(": ")
        .append(this.value.size())
        .append(" entries of type ")
        .append(NBTUtils.getTypeName(this.type))
        .append("\r\n{\r\n");

    for (Tag t : this.value) {
      bldr.append("   ").append(t.toString().replaceAll("\r\n", "\r\n   ")).append("\r\n");
    }

    bldr.append("}");

    return bldr.toString();
  }

}
