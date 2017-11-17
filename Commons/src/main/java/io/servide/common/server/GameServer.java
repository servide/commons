package io.servide.common.server;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

public final class GameServer extends ServerSkeleton implements PlayerServer {

  private final int softCap;
  private final int hardCap;

  private GameServer(UUID id, String name, String host, int port, int softCap, int hardCap) {
    super(id, name, host, port);

    this.softCap = softCap;
    this.hardCap = hardCap;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public int softCap() {
    return this.softCap;
  }

  @Override
  public int hardCap() {
    return this.hardCap;
  }

  @Override
  public boolean isCap() {
    return this.hardCap <= 0;
  }

  public static final class Builder implements
      org.apache.commons.lang3.builder.Builder<GameServer> {

    private final Map<String, Object> metadata = new CaseInsensitiveMap<>();
    private UUID id;
    private String name;
    private String host;
    private int port;
    private int softCap;
    private int hardCap;

    private Builder() {

    }

    @Override
    public GameServer build() {
      this.validate();

      GameServer server = new GameServer(this.id, this.name, this.host,
          this.port, this.softCap, this.hardCap);

      this.metadata.forEach(server::setMetadata);

      return server;
    }

    private void validate() {
      Objects.requireNonNull(this.id, "id");
      Objects.requireNonNull(this.name, "name");
      Objects.requireNonNull(this.host, "host");

      if (this.hardCap < this.softCap) {
        throw new IllegalStateException("Hardcap is lower than softcap");
      }

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

    public Builder setSoftCap(int softCap) {
      this.softCap = softCap;
      return this;
    }

    public Builder setHardCap(int hardCap) {
      this.hardCap = hardCap;
      return this;
    }

    public Builder setMetadata(String name, Object value) {
      this.metadata.put(name, value);
      return this;
    }

  }

}
