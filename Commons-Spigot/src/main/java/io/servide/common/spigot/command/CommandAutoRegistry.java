package io.servide.common.spigot.command;

import com.google.common.collect.Lists;
import io.servide.common.except.Try;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

public final class CommandAutoRegistry {

  private static final CommandMap COMMAND_MAP = CommandAutoRegistry.commandMap();

  private CommandAutoRegistry() {
    throw new IllegalStateException("Cannot be instantiated");
  }

  private static CommandMap commandMap() {
    return (CommandMap) Try
        .to(() -> FieldUtils.readDeclaredField(Bukkit.getPluginManager(), "commandMap", true));
  }

  public static void registerCommand(CommandExecutor command, String name, String... aliases) {
    org.bukkit.command.Command bukkitCommand = new org.bukkit.command.Command(name) {
      @Override
      public boolean execute(CommandSender sender, String label, String[] args) {
        command.onCommand(sender, this, label, args);
        return false;
      }
    };

    bukkitCommand.setAliases(Lists.newArrayList(aliases));

    CommandAutoRegistry.COMMAND_MAP.register(name, bukkitCommand);
  }

}
