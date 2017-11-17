package io.servide.common.spigot.schematic;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.internal.LocalWorldAdapter;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import java.util.function.Consumer;

public enum SchematicUtils {

  ;

  public static void paste(Clipboard clipboard, org.bukkit.World bukkitWorld, Vector origin) {
    World world = LocalWorldAdapter.adapt(new BukkitWorld(bukkitWorld));

    SchematicUtils.useEditSession(world, editSession -> {

      Operation operation = SchematicUtils
          .operatePaste(editSession, clipboard, world, origin);

      Operations.completeBlindly(operation);

    });
  }

  private static void useEditSession(World world, Consumer<EditSession> consumer) {
    WorldEdit worldEdit = WorldEditUtils.getWorldEdit();

    EditSession editSession = worldEdit.getEditSessionFactory().getEditSession(world, -1);
    editSession.enableQueue();

    consumer.accept(editSession);

    editSession.flushQueue();
  }

  private static Operation operatePaste(EditSession editSession, Clipboard clipboard, World world,
      Vector origin) {
    ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard, world.getWorldData());

    LocalSession session = new LocalSession(WorldEditUtils.getWorldEdit().getConfiguration());

    session.setClipboard(clipboardHolder);

    return clipboardHolder
        .createPaste(editSession, editSession.getWorld().getWorldData())
        .to(origin)
        .build();
  }

}
