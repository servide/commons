package io.servide.common.server;

import io.servide.common.json.Jackson;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

public class ServerSkeleton implements Server {

  private final UUID id;
  private final String name;
  private final String host;
  private final int port;
  private final Map<String, Object> metadata = new CaseInsensitiveMap<>();

  protected ServerSkeleton(UUID id, String name, String host, int port) {
    this.id = id;
    this.name = name;
    this.host = host;
    this.port = port;
  }

  @Override
  public Optional<Object> getMetadata(String name) {
    Objects.requireNonNull(name, "name");

    return Optional.ofNullable(this.metadata.get(name));
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public UUID getUuid() {
    return this.id;
  }

  @Override
  public void setMetadata(String name, Object value) {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(value, "value");

    this.metadata.put(name, value);
  }

  @Override
  public String getHost() {
    return this.host;
  }

  @Override
  public int getPort() {
    return this.port;
  }

  @Override
  public Stream<Object> streamValues() {
    return this.metadata.values().stream();
  }

  @Override
  public String toString() {
    return Jackson.objectToJson(this);
  }

}
