package io.servide.common.spigot.nbt;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CompoundTag implements Tag {

  private final Map<String, Tag> value;

  public CompoundTag(Map<String, Tag> value) {
    this.value = Collections.unmodifiableMap(value);
  }

  public boolean containsKey(String key) {
    return this.value.containsKey(key);
  }

  @Override
  public Map<String, Tag> getValue() {
    return this.value;
  }

  public CompoundTag setValue(Map<String, Tag> value) {
    return new CompoundTag(value);
  }

  public CompoundTagBuilder createBuilder() {
    return new CompoundTagBuilder(new HashMap<>(this.value));
  }

  public byte[] getByteArray(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof ByteArrayTag) {
      return ((ByteArrayTag) tag).getValue();
    } else {
      return new byte[0];
    }
  }

  public byte getByte(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof ByteTag) {
      return ((ByteTag) tag).getValue();
    } else {
      return (byte) 0;
    }
  }

  public double getDouble(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof DoubleTag) {
      return ((DoubleTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public double asDouble(String key) {
    Tag tag = this.value.get(key);

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


  public float getFloat(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof FloatTag) {
      return ((FloatTag) tag).getValue();
    } else {
      return 0;
    }
  }


  public int[] getIntArray(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof IntArrayTag) {
      return ((IntArrayTag) tag).getValue();
    } else {
      return new int[0];
    }
  }

  public int getInt(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof IntTag) {
      return ((IntTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public int asInt(String key) {
    Tag tag = this.value.get(key);

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

  public List<Tag> getList(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof ListTag) {
      return ((ListTag) tag).getValue();
    } else {
      return Collections.emptyList();
    }
  }

  public ListTag getListTag(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof ListTag) {
      return (ListTag) tag;
    } else {
      return new ListTag(StringTag.class, Collections.emptyList());
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends Tag> List<T> getList(String key, Class<T> listType) {
    Tag tag = this.value.get(key);

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

  public long getLong(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof LongTag) {
      return ((LongTag) tag).getValue();
    } else {
      return 0L;
    }
  }

  public long asLong(String key) {
    Tag tag = this.value.get(key);

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
      return 0L;
    }
  }

  public short getShort(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof ShortTag) {
      return ((ShortTag) tag).getValue();
    } else {
      return 0;
    }
  }

  public String getString(String key) {
    Tag tag = this.value.get(key);

    if (tag instanceof StringTag) {
      return ((StringTag) tag).getValue();
    } else {
      return "";
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("TAG_Compound").append(": ").append(this.value.size())
        .append(" entries\r\n{\r\n");

    for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
      builder.append("   ").append(entry.getValue().toString().replaceAll("\r\n", "\r\n   "))
          .append("\r\n");
    }

    builder.append("}");

    return builder.toString();
  }

}
