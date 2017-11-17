package io.servide.common.identity;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.commons.lang3.RandomStringUtils;

public class BigId {

  private final String id;

  private BigId(String id) {
    Preconditions.checkNotNull(id);
    Preconditions.checkArgument(id.length() == 256);
    this.id = id;
  }

  public static BigId create(String id) {
    return new BigId(id);
  }

  public static BigId random() {
    return new BigId(RandomStringUtils.randomAlphabetic(64));
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BigId bigId = (BigId) o;
    return Objects.equals(id, bigId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return id;
  }

}
