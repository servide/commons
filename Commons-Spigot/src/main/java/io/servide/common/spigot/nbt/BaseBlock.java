package io.servide.common.spigot.nbt;

import io.servide.common.except.Try;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagByteArray;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagEnd;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class BaseBlock {

  public static final int MAX_ID = 4095;
  public static final int MAX_DATA = 15;

  private static final Field NBT_LIST_FIELD = Try
      .to(() -> NBTTagList.class.getDeclaredField("list"));

  static {
    NBT_LIST_FIELD.setAccessible(true);
  }

  private short id;
  private short data;
  private CompoundTag nbtData;

  public BaseBlock(Block block) {
    this(block.getTypeId(), block.getData(), BaseBlock.getNBT(block));
  }

  public BaseBlock(int id, int data, CompoundTag nbtData) {
    this.setId(id);
    this.setData(data);
    this.setNbtData(nbtData);
  }

  private static CompoundTag getNBT(Block block) {
    TileEntity tile = ((CraftWorld) block.getWorld())
        .getTileEntityAt(block.getX(), block.getY(), block.getZ());

    NBTTagCompound nbt = new NBTTagCompound();

    if (tile != null) {
      tile.b(nbt);
    }

    return (CompoundTag) BaseBlock.toNative(nbt);
  }

  @SuppressWarnings("unchecked")
  private static Tag toNative(NBTBase foreign) {
    if (foreign == null) {
      return null;
    }

    if (foreign instanceof NBTTagCompound) {
      Map<String, Tag> values = new HashMap<>();
      Set<String> foreignKeys = ((NBTTagCompound) foreign).c();

      for (String str : foreignKeys) {
        NBTBase base = ((NBTTagCompound) foreign).get(str);
        values.put(str, toNative(base));
      }

      return new CompoundTag(values);
    } else if (foreign instanceof NBTTagByte) {
      return new ByteTag(((NBTTagByte) foreign).f()); // getByte
    } else if (foreign instanceof NBTTagByteArray) {
      return new ByteArrayTag(((NBTTagByteArray) foreign).c()); // data
    } else if (foreign instanceof NBTTagDouble) {
      return new DoubleTag(((NBTTagDouble) foreign).g()); // getDouble
    } else if (foreign instanceof NBTTagFloat) {
      return new FloatTag(((NBTTagFloat) foreign).h()); // getFloat
    } else if (foreign instanceof NBTTagInt) {
      return new IntTag(((NBTTagInt) foreign).d()); // getInt
    } else if (foreign instanceof NBTTagIntArray) {
      return new IntArrayTag(((NBTTagIntArray) foreign).c()); // data
    } else if (foreign instanceof NBTTagList) {
      try {

        return BaseBlock.toNativeList((NBTTagList) foreign);
      } catch (Throwable e) {
        System.out.println("ERROR nativising list " + foreign);
        return new ListTag(ByteTag.class, new ArrayList<ByteTag>());
      }
    } else if (foreign instanceof NBTTagLong) {
      return new LongTag(((NBTTagLong) foreign).c()); // getLong
    } else if (foreign instanceof NBTTagShort) {
      return new ShortTag(((NBTTagShort) foreign).e()); // getShort
    } else if (foreign instanceof NBTTagString) {
      return new StringTag(((NBTTagString) foreign).a_()); // data
    } else if (foreign instanceof NBTTagEnd) {
      return new EndTag();
    } else {
      throw new IllegalArgumentException(
          "Don't know how to make native " + foreign.getClass().getCanonicalName());
    }
  }

  private static ListTag toNativeList(NBTTagList foreign) throws Exception {
    List<Tag> values = new ArrayList<>();
    int type = foreign.f();

    List foreignList = (List) BaseBlock.NBT_LIST_FIELD.get(foreign);

    for (int i = 0; i < foreign.size(); i++) {
      NBTBase element = (NBTBase) foreignList.get(i);
      values.add(BaseBlock.toNative(element)); // List elements shouldn't have names
    }

    Class<? extends Tag> cls = NBTConstants.getClassFromType(type);
    return new ListTag(cls, values);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.setSafeId(id);
  }

  private void setSafeId(int id) {
    if (id > MAX_ID) {
      throw new IllegalArgumentException(
          "Can't have a block ID above " + MAX_ID + " (" + id + " given)");
    }

    if (id < 0) {
      throw new IllegalArgumentException("Can't have a block ID below 0");
    }

    this.id = (short) id;
  }

  public int getData() {
    return this.data;
  }

  private void setData(int data) {
    this.setSafeData(data);
  }

  private void setSafeData(int data) {
    if (data > MAX_DATA) {
      throw new IllegalArgumentException(
          "Can't have a block data value above " + MAX_DATA + " (" + data + " given)");
    }

    if (data < -1) {
      throw new IllegalArgumentException("Can't have a block data value below -1");
    }

    this.data = (short) data;
  }

  public String getNbtId() {
    CompoundTag nbtData = this.getNbtData();

    if (nbtData == null) {
      return "";
    }

    Tag idTag = nbtData.getValue().get("id");

    if (idTag != null && idTag instanceof StringTag) {
      return ((StringTag) idTag).getValue();
    } else {
      return "";
    }
  }

  public CompoundTag getNbtData() {
    return this.nbtData;
  }

  public void setNbtData(CompoundTag nbtData) {
    this.nbtData = nbtData;
  }

  public int getType() {
    return this.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BaseBlock)) {
      return false;
    }

    final BaseBlock otherBlock = (BaseBlock) o;

    return this.getType() == otherBlock.getType() && this.getData() == otherBlock.getData();

  }

  @Override
  public int hashCode() {
    int ret = this.getId() << 3;

    if (this.getData() != (byte) -1) {
      ret |= this.getData();
    }

    return ret;
  }

  @Override
  public String toString() {
    return "Block{ID:" + this.getId() + ", Data: " + this.getData() + "}";
  }

}
