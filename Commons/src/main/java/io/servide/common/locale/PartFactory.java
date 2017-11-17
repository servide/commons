package io.servide.common.locale;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PartFactory {

  ;

  private static final Pattern PLACEHOLDER_FINDER =
      Pattern.compile("((?<!\\\\)\\{[^\\{\\}\\\\]*(?:\\\\.[^\\{\\}\\\\]*)*\\})");

  private static final Pattern LEFT_BRACE_FINDER = Pattern.compile("(?<!\\\\)\\{");
  private static final Pattern RIGHT_BRACE_FINDER = Pattern.compile("(?<!\\\\)\\}");

  public static List<MessagePart> compile(String text) {
    PartFactory.validatePlaceholder(text);

    return null;
  }

  public static List<MessagePart> basic(String text) {
    return new LinkedList<>(Collections.singletonList(() -> text));
  }

  private static void validatePlaceholder(String text) {
    int left = PartFactory.countOccurences(text, PartFactory.LEFT_BRACE_FINDER);
    int right = PartFactory.countOccurences(text, PartFactory.RIGHT_BRACE_FINDER);

    if (left != right) {
      System.out.println(text + " is not a valid placeholder!");
      System.out.println("It has " + left + " left braces and " + right + " right braces!");
      throw new RuntimeException("Failed to evaluate placeholder");
    }
  }

  private static int countOccurences(String text, Pattern pattern) {
    Matcher matcher = pattern.matcher(text);

    int count = 0;

    while (matcher.find()) {
      count++;
    }

    return count;
  }

}
