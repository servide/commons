package io.servide.common.spigot.command;

import java.util.function.Consumer;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandHandler<S extends CommandSender> extends Consumer<CommandContext<S>> {

}
