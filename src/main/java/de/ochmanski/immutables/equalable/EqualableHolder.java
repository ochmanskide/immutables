package de.ochmanski.immutables.equalable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public interface EqualableHolder<S> {

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final S @NotNull ... array) {
    return !isIn(array);
  }

  @Contract(pure = true)
  boolean isIn(@NotNull final S @NotNull ... array);

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final S @NotNull [] array) {
    return !isInArray(array);
  }

  @Contract(pure = true)
  boolean isInArray(@NotNull final S @NotNull [] array);

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

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final S other) {
    return !isEqualTo();
  }

  @Contract(value = "null -> false", pure = true)
  boolean isEqualTo(@Nullable final S other);

  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final S other) {
    return !isSameAs();
  }

  @Contract(value = "null -> false", pure = true)
  boolean isSameAs(@Nullable final S other);

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <E extends @NotNull Enum<@NotNull E>> Equalable.EqualableEnum.EnumHolder<@NotNull E> element(@Nullable final E s) {
    return Equalable.EqualableEnum.EnumHolder.<@NotNull E>of(s);
  }
}
