package de.ochmanski.immutables.equalable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface Equalable<T>
{

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final T other)
  {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final T other)
  {
    return areEqual(this, other);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final T @NotNull ... array)
  {
    return isIn(array);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final List<@NotNull T> elements)
  {
    return isIn(elements);
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final T @NotNull ... array)
  {
    return allMatch(Arrays.asList(array));
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final List<@NotNull T> elements)
  {
    return elements.stream().allMatch(this::isEqualTo);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final T @NotNull ... array)
  {
    return isNotIn(array);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final List<@NotNull T> elements)
  {
    return isNotIn(elements);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final T @NotNull ... array)
  {
    return !isIn(array);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final List<@NotNull T> elements)
  {
    return !isIn(elements);
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final T @NotNull ... array)
  {
    return isIn(Arrays.asList(array));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final List<@NotNull T> elements)
  {
    return elements.contains(this);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b)
  {
    return !areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areEqual(@Nullable final S a, @Nullable final S b)
  {
    return Objects.equals(a, b);
  }
}
