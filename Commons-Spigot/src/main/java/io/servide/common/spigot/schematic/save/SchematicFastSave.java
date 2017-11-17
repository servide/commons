package io.servide.common.spigot.schematic.save;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.servide.common.except.Try;
import io.servide.common.spigot.inject.plugin.ServidePlugin;
import io.servide.common.spigot.nbt.BaseBlock;
import io.servide.common.spigot.nbt.ByteArrayTag;
import io.servide.common.spigot.nbt.CompoundTag;
import io.servide.common.spigot.nbt.IntTag;
import io.servide.common.spigot.nbt.ListTag;
import io.servide.common.spigot.nbt.NBTOutputStream;
import io.servide.common.spigot.nbt.ShortTag;
import io.servide.common.spigot.nbt.StringTag;
import io.servide.common.spigot.nbt.Tag;
import io.servide.common.spigot.schematic.CubeSelection;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

@Singleton
public class SchematicFastSave {

  @Inject
  private ServidePlugin plugin;

  public void save(CubeSelection selection, File file) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> this.saveAsync(selection, file));
  }

  private void saveAsync(CubeSelection selection, File file) {
    int width = selection.getSize().getBlockX();
    int height = selection.getSize().getBlockY();
    int length = selection.getSize().getBlockZ();

    Map<String, Tag> schematic = new HashMap<>();
    schematic.put("Width", new ShortTag((short) width));
    schematic.put("Height", new ShortTag((short) height));
    schematic.put("Length", new ShortTag((short) length));
    schematic.put("Materials", new StringTag("Alpha"));

    byte[] blocks = new byte[width * height * length];
    byte[] blockData = new byte[width * height * length];
    List<Tag> tileEntities = new ArrayList<>();

    byte[] addBlocks = null;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int z = 0; z < length; z++) {
          int index = y * width * length + z * width + x;
          BaseBlock block = new BaseBlock(
              this.blockAt(selection.getWorld(), selection.getOrigin(), x, y, z));

          // Save 4096 IDs in an AddBlocks section
          if (block.getType() > 255) {
            if (addBlocks == null) { // Lazily create section
              addBlocks = new byte[(blocks.length >> 1) + 1];
            }

            addBlocks[index >> 1] = (byte) (((index & 1) == 0) ?
                addBlocks[index >> 1] & 0xF0 | (block.getType() >> 8) & 0xF
                : addBlocks[index >> 1] & 0xF | ((block.getType() >> 8) & 0xF) << 4);
          }

          blocks[index] = (byte) block.getType();
          blockData[index] = (byte) block.getData();

          // Get the list of key/values from the block
          CompoundTag rawTag = block.getNbtData();
          if (rawTag != null) {
            Map<String, Tag> values = new HashMap<>();

            for (Map.Entry<String, Tag> entry : rawTag.getValue().entrySet()) {
              values.put(entry.getKey(), entry.getValue());
            }

            values.put("id", new StringTag(block.getNbtId()));
            values.put("x", new IntTag(x));
            values.put("y", new IntTag(y));
            values.put("z", new IntTag(z));

            CompoundTag tileEntityTag = new CompoundTag(values);
            tileEntities.add(tileEntityTag);
          }
        }
      }
    }

    schematic.put("Blocks", new ByteArrayTag(blocks));
    schematic.put("Data", new ByteArrayTag(blockData));
    schematic.put("Entities", new ListTag(CompoundTag.class, new ArrayList<>()));
    schematic.put("TileEntities", new ListTag(CompoundTag.class, tileEntities));

    if (addBlocks != null) {
      schematic.put("AddBlocks", new ByteArrayTag(addBlocks));
    }

    Bukkit.getScheduler().runTask(this.plugin, () -> this.buildSchematic(file, schematic));

  }

  private void buildSchematic(File file, Map<String, Tag> schematic) {
    Try.to(() ->
    {
      CompoundTag schematicTag = new CompoundTag(schematic);
      NBTOutputStream stream = new NBTOutputStream(
          new GZIPOutputStream(new FileOutputStream(file)));
      stream.writeNamedTag("Schematic", schematicTag);
      stream.close();
    });
  }

  private Block blockAt(World world, Vector origin, int offsetX, int offsetY, int offsetZ) {
    int x = origin.getBlockX() + offsetX;
    int y = origin.getBlockY() + offsetY;
    int z = origin.getBlockZ() + offsetZ;

    return world.getBlockAt(x, y, z);
  }

}
