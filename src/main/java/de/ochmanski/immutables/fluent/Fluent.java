package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<F extends @NotNull Enum<? extends @NotNull F> & @NotNull Equalable<? extends @NotNull F>> extends Equalable<@NotNull Fluent<@NotNull F>>
{

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Fluent<? extends @NotNull F> other)
  {
    return isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Fluent<? extends @NotNull F> other)
  {
    return Fluent.<@NotNull F>areTheSame(this, other);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<? extends @NotNull F> & @NotNull Equalable<? extends @NotNull F>> boolean areTheSame(@Nullable final Fluent<? extends @NotNull F> a, @Nullable final Fluent<? extends @NotNull F> b) {
    return Equalable.<Fluent<? extends @NotNull F>>areTheSame(a, b);
  }

  default boolean anyMatch(@NotNull final Fluent<? extends @NotNull F> @NotNull ... array)
  {
    return isIn(array);
  }

  default boolean anyMatch(@NotNull final List<@NotNull Fluent<? extends @NotNull F>> elements)
  {
    return isIn(elements);
  }

  default boolean allMatch(@NotNull final Fluent<? extends @NotNull F> @NotNull ... array)
  {
    return allMatch(Arrays.asList(array));
  }

  default boolean allMatch(@NotNull final List<@NotNull Fluent<? extends @NotNull F>> elements)
  {
    return elements.stream().allMatch(this::isEqualTo);
  }

  default boolean noneMatch(@NotNull final Fluent<? extends @NotNull F> @NotNull ... array)
  {
    return isNotIn(array);
  }

  default boolean noneMatch(@NotNull final List<@NotNull Fluent<? extends @NotNull F>> elements)
  {
    return isNotIn(elements);
  }

  default boolean isNotIn(@NotNull final Fluent<? extends @NotNull F> @NotNull ... array)
  {
    return !isIn(array);
  }

  default boolean isNotIn(@NotNull final List<@NotNull Fluent<? extends @NotNull F>> elements)
  {
    return !isIn(elements);
  }

  default boolean isIn(@NotNull final Fluent<? extends @NotNull F> @NotNull ... array)
  {
    return isIn(Arrays.asList(array));
  }

  default boolean isIn(@NotNull final List<@NotNull Fluent<? extends @NotNull F>> elements)
  {
    return elements.contains(this);
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new")
  static <E> Stream<? extends @NotNull E> createStream(@NotNull final Class<? extends @NotNull E> clazz)
  {
    return Arrays.stream(getEnumConstants(clazz));
  }

  //@MustBeInvokedByOverriders
  static <E> void forEachHelper(@NotNull final Class<? extends @NotNull E> clazz, @NotNull final Consumer<? super @NotNull E> consumer)
  {
    Arrays.asList(getEnumConstants(clazz)).forEach(consumer);
  }

  @NotNull
  static <E> E @NotNull [] getEnumConstants(@NotNull final Class<? extends @NotNull E> enumClass)
  {
    return enumClass.getEnumConstants();
  }
}
