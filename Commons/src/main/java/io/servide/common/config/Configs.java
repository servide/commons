package io.servide.common.config;

import com.google.common.base.Charsets;
import io.servide.common.except.Try;
import io.servide.common.json.Jackson;
import java.io.File;
import org.apache.commons.io.FileUtils;

public enum Configs {

  ;

  public static <T> T unwrap(File config, Class<T> mapping) {
    String contents = Try.to(() -> FileUtils.readFileToString(config, Charsets.UTF_8));

    return Jackson.jsonToObject(contents, mapping);
  }

  public static <T> void save(File config, T value) {
    String json = Jackson.objectToJson(value);

    Try.to(() -> FileUtils.write(config, json, Charsets.UTF_8));
  }

  @Deprecated
  public static <T> T unwrapUsingYaml(File config, Class<T> mapping) {
    String contents = Try.to(() -> FileUtils.readFileToString(config, Charsets.UTF_8));

    return Jackson.yamlToObject(contents, mapping);
  }

  @Deprecated
  public static <T> void saveUsingYaml(File config, T value) {
    String yaml = Jackson.objectToYaml(value);

    Try.to(() -> FileUtils.write(config, yaml, Charsets.UTF_8));
  }

}
