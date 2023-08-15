package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<E extends Enum<@NotNull E>> extends Equalable<@NotNull E>
{

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final E other)
  {
    return this == other;
  }

  default boolean anyMatch(@NotNull final E @NotNull ... array)
  {
    return isIn((E[])array);
  }

  default boolean anyMatch(@NotNull final List<@NotNull E> elements)
  {
    return isIn(elements);
  }

  default boolean allMatch(@NotNull final E @NotNull ... array)
  {
    return allMatch(Arrays.asList(array));
  }

  default boolean allMatch(@NotNull final List<@NotNull E> elements)
  {
    return elements.stream().allMatch(this::isEqualTo);
  }

  default boolean noneMatch(@NotNull final E @NotNull ... array)
  {
    return isNotIn(array);
  }

  default boolean noneMatch(@NotNull final List<@NotNull E> elements)
  {
    return isNotIn(elements);
  }

  default boolean isNotIn(@NotNull final E @NotNull ... array)
  {
    return !isIn(array);
  }

  default boolean isNotIn(@NotNull final List<@NotNull E> elements)
  {
    return !isIn(elements);
  }

  default boolean isIn(@NotNull final E @NotNull ... array)
  {
    return isIn(Arrays.asList(array));
  }

  default boolean isIn(@NotNull final List<@NotNull E> elements)
  {
    return elements.contains(this);
  }

  //@MustBeInvokedByOverriders
  @Contract(value = "_ -> new")
  @NotNull
  static <E> Stream<@NotNull E> createStream(@NotNull final Class<@NotNull E> clazz)
  {
    return Arrays.stream(getEnumConstants(clazz));
  }

  //@MustBeInvokedByOverriders
  static <E> void forEachHelper(@NotNull final Class<@NotNull E> clazz, @NotNull final Consumer<@NotNull E> consumer)
  {
    Arrays.asList(getEnumConstants(clazz)).forEach(consumer);
  }

  @NotNull
  static <E> E @NotNull [] getEnumConstants(@NotNull final Class<@NotNull E> enumClass)
  {
    return enumClass.getEnumConstants();
  }
}
