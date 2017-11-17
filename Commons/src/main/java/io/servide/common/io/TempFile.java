package io.servide.common.io;

import io.servide.common.except.Try;
import java.io.File;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public final class TempFile {

  private final String extension;
  private final File directory;

  private TempFile(String extension, File directory) {
    this.extension = extension;
    this.directory = directory;
  }

  public static TempFile create() {
    return new TempFile(".tmp", null);
  }

  public static TempFile create(String extension) {
    return new TempFile(extension, null);
  }

  public static TempFile createInDirectory(String extension, File directory) {
    return new TempFile(extension, directory);
  }

  public void use(Consumer<File> consumer) {
    File tempFile = this.createTempFile();

    consumer.accept(tempFile);

    tempFile.delete();
  }

  public <R> R use(Function<File, R> function) {
    File tempFile = this.createTempFile();

    R value = function.apply(tempFile);

    tempFile.delete();

    return value;
  }

  private File createTempFile() {
    return Try
        .to(() -> File.createTempFile(this.getFileName(), this.extension, this.directory));
  }

  private String getFileName() {
    return UUID.randomUUID().toString();
  }

}
