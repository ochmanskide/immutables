package de.ochmanski.immutables;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<@NotNull E extends Enum<@NotNull E>> extends Equalable<@NotNull E>
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
  default Stream<@NotNull E> stream()
  {

    return Arrays.stream(getGenericSuperClass().getEnumConstants());
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
    return getGenericSuperClass().getEnumConstants();
  }

  @NotNull
  default Class<@NotNull E> getGenericSuperClass()
  {
    return (Class)((ParameterizedType)getClass()
        .getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

}
