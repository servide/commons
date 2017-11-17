package io.servide.common.locale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Message {

  private final List<MessagePart> parts;

  public Message(List<MessagePart> parts) {
    this.parts = parts;
  }

  public static Message basic(String text) {
    return new Message(PartFactory.basic(text));
  }

  public String getAsString() {
    return this.getAsString(new HashMap<>());
  }

  public String getAsString(Map<Class<?>, Object> parameters) {
    return this.parts.stream()
        .map(part -> this.partValue(part, parameters))
        .collect(Collectors.joining());
  }

  private String partValue(MessagePart part, Map<Class<?>, Object> parameters) {
    if (part.raw() || !(part instanceof PlaceholderMessagePart)) {
      return part.text();
    }

    @SuppressWarnings("unchecked")
    PlaceholderMessagePart<Object> placeholder = (PlaceholderMessagePart<Object>) part;

    Object parameter = parameters.get(placeholder.argumentType());

    if (parameter == null) {
      return placeholder.escapedPlaceholder();
    }

    return placeholder.format(parameter);
  }

}
