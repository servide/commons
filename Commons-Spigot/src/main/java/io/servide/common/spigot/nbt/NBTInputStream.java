package io.servide.common.spigot.nbt;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NBTInputStream implements Closeable {

  private final DataInputStream is;

  public NBTInputStream(InputStream is) throws IOException {
    this.is = new DataInputStream(is);
  }

  public NamedTag readNamedTag() throws IOException {
    return this.readNamedTag(0);
  }

  private NamedTag readNamedTag(int depth) throws IOException {
    int type = this.is.readByte() & 0xFF;

    String name;

    if (type != NBTConstants.TYPE_END) {
      int nameLength = this.is.readShort() & 0xFFFF;
      byte[] nameBytes = new byte[nameLength];
      this.is.readFully(nameBytes);
      name = new String(nameBytes, NBTConstants.CHARSET);
    } else {
      name = "";
    }

    return new NamedTag(name, this.readTagPayload(type, depth));
  }

  private Tag readTagPayload(int type, int depth) throws IOException {
    switch (type) {
      case NBTConstants.TYPE_END:
        if (depth == 0) {
          throw new IOException(
              "TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
        } else {
          return new EndTag();
        }
      case NBTConstants.TYPE_BYTE:
        return new ByteTag(this.is.readByte());
      case NBTConstants.TYPE_SHORT:
        return new ShortTag(this.is.readShort());
      case NBTConstants.TYPE_INT:
        return new IntTag(this.is.readInt());
      case NBTConstants.TYPE_LONG:
        return new LongTag(this.is.readLong());
      case NBTConstants.TYPE_FLOAT:
        return new FloatTag(this.is.readFloat());
      case NBTConstants.TYPE_DOUBLE:
        return new DoubleTag(this.is.readDouble());
      case NBTConstants.TYPE_BYTE_ARRAY:
        int length = this.is.readInt();
        byte[] bytes = new byte[length];
        this.is.readFully(bytes);
        return new ByteArrayTag(bytes);
      case NBTConstants.TYPE_STRING:
        length = this.is.readShort();
        bytes = new byte[length];
        this.is.readFully(bytes);
        return new StringTag(new String(bytes, NBTConstants.CHARSET));
      case NBTConstants.TYPE_LIST:
        int childType = this.is.readByte();
        length = this.is.readInt();

        List<Tag> tagList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
          Tag tag = this.readTagPayload(childType, depth + 1);

          if (tag instanceof EndTag) {
            throw new IOException("TAG_End not permitted in a list.");
          }

          tagList.add(tag);
        }

        return new ListTag(NBTUtils.getTypeClass(childType), tagList);
      case NBTConstants.TYPE_COMPOUND:
        Map<String, Tag> tagMap = new HashMap<>();
        while (true) {
          NamedTag namedTag = this.readNamedTag(depth + 1);
          Tag tag = namedTag.getTag();

          if (tag instanceof EndTag) {
            break;
          } else {
            tagMap.put(namedTag.getName(), tag);
          }
        }

        return new CompoundTag(tagMap);
      case NBTConstants.TYPE_INT_ARRAY:
        length = this.is.readInt();
        int[] data = new int[length];
        for (int i = 0; i < length; i++) {
          data[i] = this.is.readInt();
        }
        return new IntArrayTag(data);
      default:
        throw new IOException("Invalid tag type: " + type + ".");
    }
  }

  @Override
  public void close() throws IOException {
    this.is.close();
  }

}
