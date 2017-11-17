package io.servide.common.spigot.plugin.auto;

import io.servide.common.except.Try;
import java.io.BufferedWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.yaml.snakeyaml.Yaml;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"Plugin", "PluginDependency"})
public class PluginProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
    Set<? extends Element> annotatedElements = env.getElementsAnnotatedWith(Plugin.class);
    if (annotatedElements.isEmpty()) {
      return false;
    }

    if (annotatedElements.size() > 1) {
      this.processingEnv.getMessager()
          .printMessage(Diagnostic.Kind.ERROR, "More than one @Plugin element found.");
      return false;
    }

    Element element = annotatedElements.iterator().next();

    if (!(element instanceof TypeElement)) {
      this.processingEnv.getMessager()
          .printMessage(Diagnostic.Kind.ERROR, "@Plugin element is not instance of TypeElement");
      return false;
    }

    TypeElement type = ((TypeElement) element);
    Map<String, Object> data = new LinkedHashMap<>();

    Plugin annotation = type.getAnnotation(Plugin.class);

    this.putNotIf(data, "name", annotation.name(), String::isEmpty,
        this.getAfter(type.getQualifiedName().toString(), "\\."));

    String defaultVersion = new SimpleDateFormat("yyyy-MM-dd-HH-mm")
        .format(new Date(System.currentTimeMillis()));
    this.putNotIf(data, "version", annotation.version(), String::isEmpty, defaultVersion);

    data.put("main", type.getQualifiedName().toString());

    String description = annotation.description();
    this.putNotIf(data, "description", annotation.description(), String::isEmpty);

    this.putIf(data, "load", annotation.load().name(), Objects::nonNull);

    this.putIf(data, "author", annotation.authors(), authors -> authors.length == 1);
    this.putIf(data, "authors", this.wrap(annotation.authors()), authors -> authors.size() > 1);

    this.putNotIf(data, "webite", annotation.website(), String::isEmpty);

    PluginDependency[] depends = annotation.depends();
    List<String> hard = new ArrayList<>();
    List<String> soft = new ArrayList<>();

    for (PluginDependency depend : depends) {
      if (!depend.soft()) {
        hard.add(depend.value());
      } else {
        soft.add(depend.value());
      }
    }

    hard.addAll(Arrays.asList(annotation.hardDepends()));
    soft.addAll(Arrays.asList(annotation.softDepends()));

    this.putNotIf(data, "depend", hard, List::isEmpty);
    this.putNotIf(data, "softdepend", soft, List::isEmpty);

    this.putNotIf(data, "loadbefore", this.wrap(annotation.loadBefore()), List::isEmpty);

    Try.to(() -> {
      Yaml yaml = new Yaml();
      FileObject resource =
          this.processingEnv.getFiler()
              .createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");

      try (Writer writer = resource.openWriter(); BufferedWriter bw = new BufferedWriter(writer)) {
        yaml.dump(data, bw);
        bw.flush();
      }

    });

    return true;
  }

  private <T> void putIf(Map<String, Object> data, String key, T value, Predicate<T> putIf) {
    if (putIf.test(value)) {
      data.put(key, value);
    }
  }

  private <T> void putIf(Map<String, Object> data, String key, T value, Predicate<T> putIf,
      T elseValue) {
    if (putIf.test(value)) {
      data.put(key, value);
    } else {
      data.put(key, elseValue);
    }
  }

  private <T> void putNotIf(Map<String, Object> data, String key, T value, Predicate<T> putIf) {
    this.putIf(data, key, value, this.invert(putIf));
  }

  private <T> void putNotIf(Map<String, Object> data, String key, T value, Predicate<T> putIf,
      T elseValue) {
    this.putIf(data, key, value, this.invert(putIf), elseValue);
  }

  private <T> Predicate<T> invert(Predicate<T> predicate) {
    return value -> !predicate.test(value);
  }

  private String getAfter(String string, String split) {
    String[] parts = string.split(split);
    return parts[parts.length - 1];
  }

  private <T> List<T> wrap(T[] array) {
    return new ArrayList<>(Arrays.asList(array));
  }

}