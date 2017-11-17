package io.servide.common.server;

import io.servide.common.identity.Unique;
import io.servide.common.metadata.Metadata;
import io.servide.common.name.Named;

public interface Server extends Named, Unique, Metadata {

  String getHost();

  int getPort();

}
