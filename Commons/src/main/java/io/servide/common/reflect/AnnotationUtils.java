package io.servide.common.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections4.SetUtils;

public enum AnnotationUtils {

  ;

  public static List<Annotation> getLeafAnnotations(AnnotatedElement holder,
      Class<? extends Annotation> seed) {
    Objects.requireNonNull(holder);
    Objects.requireNonNull(seed);

    return new AnnotationLookup<>(seed).getLeaves(holder);
  }

  public static <T extends Annotation> T getRootAnnotation(AnnotatedElement holder, Class<T> seed) {
    Objects.requireNonNull(holder);
    Objects.requireNonNull(seed);

    return new AnnotationLookup<>(seed).getRoot(holder);
  }

  private static final class AnnotationLookup<T extends Annotation> {

    private final Class<T> seed;
    private final Set<Class<?>> checked;
    private final List<Annotation> results = new ArrayList<>();

    AnnotationLookup(Class<T> seed) {
      this.seed = seed;
      this.checked = SetUtils.newIdentityHashSet();
    }

    public T getRoot(AnnotatedElement holder) {
      Class<T> seed = this.seed;
      Set<Class<?>> checked = this.checked;
      for (Annotation annotation : holder.getAnnotations()) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (!checked.add(annotationType)) {
          continue;
        }

        if (annotationType == seed) {
          @SuppressWarnings("unchecked")
          T root = (T) annotation;
          return root;
        }

        T root = this.getRoot(annotationType);
        if (root != null) {
          return root;
        }
      }

      return null;
    }

    public List<Annotation> getLeaves(AnnotatedElement holder) {
      Class<T> seed = this.seed;
      Set<Class<?>> checked = this.checked;
      for (Annotation annotation : holder.getAnnotations()) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (!checked.add(annotationType)) {
          continue;
        }

        if (annotationType == seed) {
          this.results.add(annotation);
          continue;
        }

        if (annotationType.getAnnotationsByType(seed).length > 0) {
          this.results.add(annotation);
          continue;
        }

        this.getLeaves(annotationType);
      }

      return this.results;
    }
  }

}