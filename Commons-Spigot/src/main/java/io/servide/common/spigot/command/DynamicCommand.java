package io.servide.common.spigot.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DynamicCommand<S extends CommandSender> {

  static UnhandledDynamicCommand<CommandSender> build(String command, String[] aliases) {
    return new DynamicCommand.UnhandledBuilder<>(command, aliases);
  }

  public static final class UnhandledBuilder<S extends CommandSender> implements
      UnhandledDynamicCommand<S> {

    private final String command;
    private final String[] aliases;
    private final ImmutableList.Builder<Predicate<CommandContext<?>>> predicates;

    private UnhandledBuilder(String command, String[] aliases) {
      this(command, aliases, ImmutableList.builder());
    }

    private UnhandledBuilder(String command, String[] aliases,
        ImmutableList.Builder<Predicate<CommandContext<?>>> predicates) {
      this.command = command;
      this.aliases = aliases;
      this.predicates = predicates;
    }

    @Override
    public UnhandledBuilder<Player> assertPlayer() {
      return this.assertRule(context -> context.getSender() instanceof Player);
    }

    @Override
    public UnhandledBuilder<Player> assertPlayer(String fail) {
      return this.assertOrFail(context -> context.getSender() instanceof Player, fail);
    }

    @Override
    public UnhandledBuilder<ConsoleCommandSender> assertConsole() {
      return this.assertRule(context -> context.getSender() instanceof ConsoleCommandSender);
    }

    @Override
    public UnhandledBuilder<ConsoleCommandSender> assertConsole(String fail) {
      return this
          .assertOrFail(context -> context.getSender() instanceof ConsoleCommandSender, fail);
    }

    @Override
    public UnhandledBuilder<S> assertOp() {
      return this.assertRule(context -> context.getSender().isOp());
    }

    @Override
    public UnhandledBuilder<S> assertOp(String fail) {
      return this.assertOrFail(context -> context.getSender().isOp(), fail);
    }

    @Override
    public UnhandledBuilder<S> assertPermission(String permission) {
      return this.assertRule(context -> context.getSender().hasPermission(permission));
    }

    @Override
    public UnhandledBuilder<S> assertPermission(String permission, String fail) {
      return this.assertOrFail(context -> context.getSender().hasPermission(permission), fail);
    }

    @Override
    public UnhandledBuilder<S> assertMinArgs(int args) {
      return this.assertRule(context -> context.getArgs().size() >= args);
    }

    @Override
    public UnhandledBuilder<S> assertMaxArgs(int args) {
      return this.assertRule(context -> context.getArgs().size() <= args);
    }

    @Override
    public UnhandledBuilder<S> assertExactArgs(int args) {
      return this.assertRule(context -> context.getArgs().size() == args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public UnhandledBuilder<S> assertCondition(Predicate<CommandContext<S>> condition) {
      return this.assertRule(context -> condition.test((CommandContext<S>) context));
    }

    @Override
    @SuppressWarnings("unchecked")
    public UnhandledBuilder<S> assertCondition(Predicate<CommandContext<S>> condition,
        String fail) {
      return this.assertOrFail(context -> condition.test((CommandContext<S>) context), fail);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handler(Consumer<CommandContext<S>> handler) {
      new HandledBuilder(this.predicates.build(),
          context -> handler.accept((CommandContext<S>) context))
          .register(this.command, this.aliases);
    }

    private <T extends CommandSender> UnhandledBuilder<T> assertRule(
        Predicate<CommandContext<?>> rule) {
      this.predicates.add(rule);
      return new UnhandledBuilder<>(this.command, this.aliases, this.predicates);
    }

    private <T extends CommandSender> UnhandledBuilder<T> assertOrFail(
        Predicate<CommandContext<?>> rule, String fail) {
      this.predicates.add(context -> {
        if (rule.test(context)) {
          return true;
        }
        context.getSender().sendMessage(fail);
        return false;
      });

      return new UnhandledBuilder<>(this.command, this.aliases, this.predicates);
    }

  }

  public static final class HandledBuilder implements CommandExecutor {

    private final List<Predicate<CommandContext<?>>> preconditions;
    private final Consumer<CommandContext<?>> handler;

    private HandledBuilder(List<Predicate<CommandContext<?>>> preconditions,
        Consumer<CommandContext<?>> handler) {
      this.preconditions = preconditions;
      this.handler = handler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      CommandContext<CommandSender> context = CommandContext.builder(sender)
          .withLabel(label)
          .withArgs(Lists.newArrayList(args))
          .build();

      for (Predicate<CommandContext<?>> precondition : this.preconditions) {
        if (!precondition.test(context)) {
          return false;
        }
      }

      this.handler.accept(context);

      return false;
    }

    private void register(String command, String... aliases) {
      CommandAutoRegistry.registerCommand(this, command, aliases);
    }

  }

}
