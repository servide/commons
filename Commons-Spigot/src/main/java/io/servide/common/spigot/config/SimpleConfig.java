package io.servide.common.spigot.config;

import com.google.inject.Binder;
import io.servide.common.except.Try;
import io.servide.common.json.Jackson;
import java.io.File;
import org.apache.commons.io.FileUtils;

public enum SimpleConfig {

  ;

  public static void save(File target, Object value) {
    String data = Jackson.objectToYaml(value);
    Try.to(() -> FileUtils.write(target, data));
  }

  public static void saveIfNotExists(File target, Object value) {
    String data = Jackson.objectToYaml(value);
    if (!target.exists()) {
      Try.to(() -> FileUtils.write(target, data));
    }
  }

  public static <T> T load(File target, Class<T> tClass) {
    if (target.exists()) {
      String data = Try.to(() -> FileUtils.readFileToString(target));
      return Jackson.yamlToObject(data, tClass);
    }
    T t = Try.to(tClass::newInstance);
    save(target, t);
    return t;
  }

  public static <T> void bind(Binder binder, File target, Class<T> tClass) {
    binder.bind(tClass).toInstance(load(target, tClass));
  }

}
