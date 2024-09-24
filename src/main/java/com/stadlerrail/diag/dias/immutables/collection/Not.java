package com.stadlerrail.diag.dias.immutables.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

public interface Not<S> {

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final S @NotNull ... array) {
    return !isInArray(array);
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final S @NotNull ... array) {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final S @NotNull [] array) {
    return !isInArray(array);
  }

  @Contract(pure = true)
  default boolean isInArray(@NotNull final S @NotNull [] array) {
    final Collection<@NotNull S> list = Arrays.stream(array).toList();
    return isIn(list);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull S> elements) {
    return !isIn(elements);
  }

  @Contract(pure = true)
  boolean isIn(@NotNull final Collection<? extends @NotNull S> elements);

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Set<? extends @NotNull S> elements) {
    return !isIn(elements);
  }

  @Contract(pure = true)
  boolean isIn(@NotNull final Set<? extends @NotNull S> elements);

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Stream<? extends @NotNull S> elements) {
    return !isIn(elements);
  }

  @Contract(pure = true)
  boolean isIn(@NotNull final Stream<? extends @NotNull S> elements);

  @Contract(pure = true)
  default boolean isNotEqualTo(@Nullable final S other) {
    return !isEqualTo(other);
  }

  @Contract(pure = true)
  boolean isEqualTo(@Nullable final S other);

  @Contract(pure = true)
  default boolean isNotSameAs(@Nullable final S other) {
    return !isSameAs(other);
  }

  @Contract(pure = true)
  boolean isSameAs(@Nullable final S other);
}
