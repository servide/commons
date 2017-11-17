package io.servide.common.stream;

import io.servide.common.except.Try;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class OutputStreamAdapter<T, O extends OutputStream> {

  private final O stream;
  private final Supplier<T> streamResult;

  private OutputStreamAdapter(O stream, Supplier<T> streamResult) {
    this.stream = stream;
    this.streamResult = streamResult;
  }

  public static <T, O extends OutputStream> OutputStreamAdapter<T, O> stream(
      O stream, Function<O, Supplier<T>> streamResult) {
    return new OutputStreamAdapter<>(stream, streamResult.apply(stream));
  }

  public static <T, O extends OutputStream> OutputStreamAdapter<T, BufferedOutputStream> bufferStream(
      O stream, Function<O, Supplier<T>> streamResult) {
    return new OutputStreamAdapter<>(new BufferedOutputStream(stream),
        streamResult.apply(stream));
  }

  public T act(Consumer<O> consumer) {
    consumer.accept(this.stream);

    T result = this.streamResult.get();

    Try.to(this.stream::close);

    return result;
  }

}
