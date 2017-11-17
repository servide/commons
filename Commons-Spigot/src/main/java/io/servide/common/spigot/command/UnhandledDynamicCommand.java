package io.servide.common.spigot.command;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface UnhandledDynamicCommand<S extends CommandSender> {

  UnhandledDynamicCommand<Player> assertPlayer();

  UnhandledDynamicCommand<Player> assertPlayer(String fail);

  UnhandledDynamicCommand<ConsoleCommandSender> assertConsole();

  UnhandledDynamicCommand<ConsoleCommandSender> assertConsole(String fail);

  UnhandledDynamicCommand<S> assertOp();

  UnhandledDynamicCommand<S> assertOp(String fail);

  UnhandledDynamicCommand<S> assertPermission(String permission);

  UnhandledDynamicCommand<S> assertPermission(String permission, String fail);

  UnhandledDynamicCommand<S> assertMinArgs(int args);

  UnhandledDynamicCommand<S> assertMaxArgs(int args);

  UnhandledDynamicCommand<S> assertExactArgs(int args);

  UnhandledDynamicCommand<S> assertCondition(Predicate<CommandContext<S>> condition);

  UnhandledDynamicCommand<S> assertCondition(Predicate<CommandContext<S>> condition, String fail);

  void handler(Consumer<CommandContext<S>> handler);

}
