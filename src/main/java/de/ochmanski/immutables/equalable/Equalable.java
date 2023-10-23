package de.ochmanski.immutables.equalable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Equalable<T extends @NotNull Equalable<@NotNull T>>
{

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return isIn(elements);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> b,
    @NotNull final Function<? super @NotNull Equalable<@NotNull T>, @NotNull String> a)
  {
    final String s = a.apply(this);
    return Equalable.<@NotNull String>anyMatchIgnoreCase(b, s);
  }

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull S> s,
    @NotNull final Function<? super @NotNull S,
      @NotNull String> a, @NotNull final Collection<@NotNull String> b)
  {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(s.stream().map(a), b);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Function<? super @NotNull Equalable<@NotNull T>,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b)
  {
    return Equalable.<@NotNull Equalable<@NotNull T>>anyMatchIgnoreCase(this, a, b);
  }

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final S s, @NotNull final Function<? super @NotNull S,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b)
  {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(a.apply(s), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Collection<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b)
  {
    return Equalable.<@NotNull String>anyMatchIgnoreCase(a.stream(), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Stream<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b)
  {
    final Set<@NotNull String> s = a
      .map(String::toUpperCase)
      .collect(Collectors.toUnmodifiableSet());
    return Equalable.<@NotNull String>anyMatchT(b, c -> s.contains(c.toUpperCase()));
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> elements, @NotNull final String text)
  {
    return Equalable.<@NotNull String>anyMatchT(elements, p -> p.equalsIgnoreCase(text));
  }

  @Contract(pure = true)
  static <T> boolean anyMatchT(
    @NotNull final Collection<? extends @NotNull T> elements,
    final Predicate<? super @NotNull T> predicate)
  {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean anyMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    return allMatchArray(array);
  }

  @Contract(pure = true)
  default boolean allMatchArray(@NotNull final Equalable<@NotNull T> @NotNull [] array)
  {
    return allMatch(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return allMatch(elements, this::isEqualTo); //TODO: test if this can be converted into elements.containsAll(this)
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean allMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    return elements.stream().allMatch(predicate);
  }

  @Contract(pure = true)
  default boolean noneMatchElements(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return isNotIn(elements);
  }

  @Contract(pure = true)
  static <T> boolean noneMatchT(@NotNull final Collection<? extends @NotNull T> elements,
    final Predicate<? super @NotNull T> predicate)
  {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean noneMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array)
  {
    return !isIn(array);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return isNotIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return !isIn(elements);
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array)
  {
    return isIn(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return isIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    return elements.contains(this);
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final T other)
  {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull T> other)
  {
    return Equalable.<@NotNull Equalable<@NotNull T>>areEqual(this, other);
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull T> other)
  {
    return !isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull T> other)
  {
    return Equalable.<@NotNull Equalable<@NotNull T>>areTheSame(this, other);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotEqual(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    return Equalable.<@NotNull Equalable<@NotNull S>>areNotEqual(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b)
  {
    return !Equalable.<@NotNull S>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areEqual(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    return Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areEqual(@Nullable final S a, @Nullable final S b)
  {
    return Objects.equals(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotTheSame(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    return Equalable.<@NotNull Equalable<@NotNull S>>areNotTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotTheSame(@Nullable final S a, @Nullable final S b)
  {
    return !Equalable.<@NotNull S>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areTheSame(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    return Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areTheSame(@Nullable final S a, @Nullable final S b)
  {
    return a == b;
  }
}
