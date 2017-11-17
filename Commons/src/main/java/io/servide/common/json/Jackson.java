package io.servide.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.servide.common.except.Try;

public enum Jackson {

  ;

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
  private static final ObjectWriter JSON_WRITER = Jackson.JSON_MAPPER
      .writerWithDefaultPrettyPrinter();

  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
  private static final ObjectWriter YAML_WRITER = Jackson.YAML_MAPPER
      .writerWithDefaultPrettyPrinter();

  public static String objectToJson(Object value) {
    return Try.to(() -> Jackson.JSON_WRITER.writeValueAsString(value));
  }

  public static <T> T jsonToObject(String json, Class<T> mapping) {
    return Try.to(() -> Jackson.JSON_MAPPER.readValue(json, mapping));
  }

  public static String objectToYaml(Object value) {
    return Try.to(() -> Jackson.YAML_WRITER.writeValueAsString(value));
  }

  public static <T> T yamlToObject(String yaml, Class<T> mapping) {
    return Try.to(() -> Jackson.YAML_MAPPER.readValue(yaml, mapping));
  }

}
