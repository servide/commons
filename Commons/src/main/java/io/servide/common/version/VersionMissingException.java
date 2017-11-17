package io.servide.common.version;

public class VersionMissingException extends RuntimeException {

  public VersionMissingException(Class<? extends Versioned> versioned) {
    super("Version annotation missing for: " + versioned);
  }

}
