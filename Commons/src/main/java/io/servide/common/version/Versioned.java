package io.servide.common.version;

public interface Versioned {

  default double getVersion(Class<? extends Versioned> versioned) {
    Version version = versioned.getAnnotation(Version.class);

    if (version == null) {
      throw new VersionMissingException(versioned);
    }

    return version.value();
  }

}
