package io.servide.common.spigot.command;

import java.util.List;
import org.bukkit.command.CommandSender;

public final class CommandContext<S extends CommandSender> {

  private final S sender;
  private final String label;
  private final List<String> args;

  private CommandContext(S sender, String label, List<String> args) {
    this.sender = sender;
    this.label = label;
    this.args = args;
  }

  static <S extends CommandSender> Builder<S> builder(S sender) {
    return new CommandContext.Builder<>(sender);
  }

  public S getSender() {
    return this.sender;
  }

  public String getLabel() {
    return this.label;
  }

  public List<String> getArgs() {
    return this.args;
  }

  public String argument(int index) {
    return this.args.get(index);
  }

  public static final class Builder<S extends CommandSender> implements
      org.apache.commons.lang3.builder.Builder<CommandContext> {

    private final S sender;
    private String label;
    private List<String> args;

    private Builder(S sender) {
      this.sender = sender;
    }

    public Builder<S> withLabel(String label) {
      this.label = label;

      return this;
    }

    public Builder<S> withArgs(List<String> args) {
      this.args = args;

      return this;
    }

    @Override
    public CommandContext<S> build() {
      return new CommandContext<>(this.sender, this.label, this.args);
    }

  }

}
