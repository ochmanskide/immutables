package de.ochmanski.immutables;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<E extends Enum<@NotNull E>> extends Equalable<@NotNull E>
{
  default boolean isNotIn(@NotNull final E... states)
  {
    return !isIn(states);
  }

  default boolean isIn(@NotNull final E... states)
  {
    return isIn(Arrays.asList(states));
  }

  default boolean isNotIn(@NotNull final List<@NotNull E> states)
  {
    return !isNotIn(states);
  }

  default boolean isIn(@NotNull final List<@NotNull E> states)
  {
    return states.contains(this);
  }

  @NotNull
  static <E> Stream<@NotNull E> stream(@NotNull final Class<@NotNull E> clazz)
  {

    return Arrays.stream(getEnumConstants(clazz));
  }

  default void forEach(@NotNull final Consumer<@NotNull E> consumer)
  {
    getEnumConstantsAsList().forEach(consumer);
  }

  @NonNull
  private List<@NotNull E> getEnumConstantsAsList()
  {
    return Arrays.asList(getEnumConstants());
  }

  @NotNull
  default E @NotNull [] getEnumConstants()
  {
    return getEnumConstants(getGenericSuperClass());
  }

  @NotNull
  static <E> E @NotNull [] getEnumConstants(@NotNull final Class<@NotNull E> enumClass)
  {
    return enumClass.getEnumConstants();
  }

  @NotNull
  default Class<@NotNull E> getGenericSuperClass()
  {
    return (Class)((ParameterizedType)getClass()
        .getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

}
