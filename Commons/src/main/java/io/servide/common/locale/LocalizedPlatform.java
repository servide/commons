package io.servide.common.locale;

import io.servide.common.json.Jackson;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizedPlatform {

  private final Locale locale;
  private final Map<String, Message> messages = new HashMap<>();

  public LocalizedPlatform(Locale locale) {
    this.locale = locale;
  }

  public static LocalizedPlatform fromJson(String json) {
    return Jackson.jsonToObject(json, LocalizedPlatform.class);
  }

  public void mapCodeToMessage(String code, Message message) {
    this.messages.put(code.toLowerCase(), message);
  }

  public Message getMessage(String code) {
    String lowerCode = code.toLowerCase();

    return this.messages.getOrDefault(lowerCode, Message.basic(lowerCode));
  }

  public Locale getLocale() {
    return this.locale;
  }

  @Override
  public String toString() {
    return Jackson.objectToJson(this);
  }

}
