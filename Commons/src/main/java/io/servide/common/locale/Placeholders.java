package io.servide.common.locale;

public enum Placeholders {

  ;

  public static String escapePlaceholder(String placeholder) {
    return "{" + placeholder + "}";
  }

}
