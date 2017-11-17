package io.servide.common.spigot.schematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.internal.LocalWorldAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import io.servide.common.except.Try;
import io.servide.common.stream.OutputStreamAdapter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.commons.io.output.ByteArrayOutputStream;

public enum RegionSerialization {

  ;

  private static final int INFINITE_PASTE_SIZE = -1;

  public static byte[] serialize(Region region, World world) {

    EditSession session = RegionSerialization.createSession(world);

    Clipboard clipboard = RegionSerialization.copyRegionToClipboard(session, world, region);

    return OutputStreamAdapter
        .bufferStream(new ByteArrayOutputStream(), stream -> stream::toByteArray)
        .act(stream -> {
          ClipboardWriter writer = Try.to(() -> ClipboardFormat.SCHEMATIC.getWriter(stream));
          Try.to(() -> writer.write(clipboard, world.getWorldData()));
        });
  }

  public static Clipboard deserialize(byte[] bytes, org.bukkit.World bukkitWorld) {
    try (BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(bytes))) {
      ClipboardReader reader = Try.to(() -> ClipboardFormat.SCHEMATIC.getReader(input));
      return RegionSerialization.loadToClipboard(reader, bukkitWorld);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static EditSession createSession(World world) {
    WorldEdit worldEdit = WorldEditUtils.getWorldEdit();

    return worldEdit.getEditSessionFactory()
        .getEditSession(world, RegionSerialization.INFINITE_PASTE_SIZE);
  }

  private static Clipboard copyRegionToClipboard(EditSession session, World world,
      Region region) {
    CuboidRegion cuboidRegion = new CuboidRegion(world, region.getMinimumPoint(),
        region.getMaximumPoint());

    BlockArrayClipboard clipboard = new BlockArrayClipboard(cuboidRegion);

    ForwardExtentCopy copy = new ForwardExtentCopy(session, cuboidRegion, clipboard,
        region.getMinimumPoint());

    Operations.completeBlindly(copy);

    return clipboard;
  }

  private static Clipboard loadToClipboard(ClipboardReader reader, org.bukkit.World bukkitWorld) {
    World world = LocalWorldAdapter.adapt(new BukkitWorld(bukkitWorld));

    Clipboard clipboard = Try.to(() -> reader.read(world.getWorldData()));
    clipboard.getRegion().setWorld(world);

    return clipboard;
  }

}
