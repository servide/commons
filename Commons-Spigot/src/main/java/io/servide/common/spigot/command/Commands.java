package io.servide.common.spigot.command;

import org.bukkit.command.CommandSender;

public final class Commands {

  private Commands() {
    throw new IllegalStateException("Cannot be instantiated");
  }

  public static UnhandledDynamicCommand<CommandSender> create(String command, String... aliases) {
    return DynamicCommand.build(command, aliases);
  }

}
