package io.servide.common.metadata;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface Metadata {

  Optional<Object> getMetadata(String name);

  void setMetadata(String name, Object value);

  default boolean metadataPresent(String name) {
    Objects.requireNonNull(name, "name");

    return this.getMetadata(name).isPresent();
  }

  Stream<Object> streamValues();

}
