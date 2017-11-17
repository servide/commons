package io.servide.common.identity;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.commons.lang3.RandomStringUtils;

public class BigId {

  private final String id;

  private BigId(String id) {
    Preconditions.checkNotNull(id);
    Preconditions.checkArgument(id.length() > 0);
    this.id = id;
  }

  public static BigId create(String id) {
    return new BigId(id);
  }

  public static BigId random(int size) {
    return new BigId(RandomStringUtils.randomAlphabetic(size));
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
