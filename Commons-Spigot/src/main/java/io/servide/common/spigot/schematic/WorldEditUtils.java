package io.servide.common.spigot.schematic;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.servide.common.cache.Lazy;
import org.bukkit.Bukkit;

public enum WorldEditUtils {

  ;

  private static final Lazy<WorldEdit> WORLD_EDIT = Lazy.of(WorldEditUtils::findWorldEdit);

  public static WorldEdit getWorldEdit() {
    return WorldEditUtils.WORLD_EDIT.get();
  }

  private static WorldEdit findWorldEdit() {
    return ((WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
  }

}
