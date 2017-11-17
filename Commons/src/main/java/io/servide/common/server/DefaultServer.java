package io.servide.common.server;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

public final class DefaultServer extends ServerSkeleton {

  private DefaultServer(UUID id, String name, String host, int port) {
    super(id, name, host, port);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder implements
      org.apache.commons.lang3.builder.Builder<DefaultServer> {

    private final Map<String, Object> metadata = new CaseInsensitiveMap<>();
    private UUID id;
    private String name;
    private String host;
    private int port;

    private Builder() {

    }

    @Override
    public DefaultServer build() {
      this.validate();

      DefaultServer server = new DefaultServer(this.id, this.name, this.host, this.port);

      this.metadata.forEach(server::setMetadata);

      return server;
    }

    private void validate() {
      Objects.requireNonNull(this.id, "id");
      Objects.requireNonNull(this.name, "name");
      Objects.requireNonNull(this.host, "host");

      this.metadata.keySet().forEach(Objects::requireNonNull);
      this.metadata.values().forEach(Objects::requireNonNull);
    }

    public Builder setId(UUID id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setHost(String host) {
      this.host = host;
      return this;
    }

    public Builder setPort(int port) {
      this.port = port;
      return this;
    }

    public Builder setMetadata(String name, Object value) {
      this.metadata.put(name, value);
      return this;
    }

  }

}
