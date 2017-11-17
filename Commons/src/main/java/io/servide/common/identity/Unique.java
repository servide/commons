package io.servide.common.identity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

@FunctionalInterface
public interface Unique {

  @JsonProperty
  UUID getUuid();

}
