package de.ochmanski.immutables.equalable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

import static de.ochmanski.immutables.constants.Constants.Warning.NULLABLE_PROBLEMS;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface Equalable<T extends @NotNull Equalable<@NotNull T>> {

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull S> s,
                                        @NotNull final Function<? super @NotNull S,
                                          @NotNull String> a, @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(s.stream().map(a), b);
  }

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final S s, @NotNull final Function<? super @NotNull S,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(a.apply(s), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Collection<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull String>anyMatchIgnoreCase(a.stream(), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Stream<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b) {
    final Set<@NotNull String> s = a
      .map(String::toUpperCase)
      .collect(Collectors.toUnmodifiableSet());
    return Equalable.<@NotNull String>anyMatchT(b, c -> s.contains(c.toUpperCase()));
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> elements, @NotNull final String text) {
    return Equalable.<@NotNull String>anyMatchT(elements, p -> p.equalsIgnoreCase(text));
  }

  @Contract(pure = true)
  static <T> boolean anyMatchT(
    @NotNull final Collection<? extends @NotNull T> elements,
    @NotNull final Predicate<? super @NotNull T> predicate) {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean anyMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean allMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().allMatch(predicate);
  }

  @Contract(pure = true)
  static <T> boolean noneMatchT(@NotNull final Collection<? extends @NotNull T> elements,
                                @NotNull final Predicate<? super @NotNull T> predicate) {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean noneMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotEqual(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return !Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b) {
    return !Equalable.<@NotNull S>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areEqual(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areEqual(@Nullable final S a, @Nullable final S b) {
    return Objects.equals(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotTheSame(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return !Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotTheSame(@Nullable final S a, @Nullable final S b) {
    return !Equalable.<@NotNull S>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areTheSame(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areTheSame(@Nullable final S a, @Nullable final S b) {
    return a == b;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <S> Equalable.EqualableHolder<@NotNull S> element(@Nullable final S s) {
    return EqualableHolder.<@NotNull S>builder().s(s).build();
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> of(@Nullable final E s) {
    return EqualableHolder.<@NotNull E>element(s);
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> element(@Nullable final E s) {
    return EnumHolder.<@NotNull E>of(s);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean anyMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isIn(elements);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> b,
                                     @NotNull final Function<? super @NotNull Equalable<@NotNull T>, @NotNull String> a) {
    final String s = a.apply(this);
    return Equalable.<@NotNull String>anyMatchIgnoreCase(b, s);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Function<? super @NotNull Equalable<@NotNull T>,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull Equalable<@NotNull T>>anyMatchIgnoreCase(this, a, b);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean allMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return allMatchArray(array);
  }

  @Contract(pure = true)
  default boolean allMatchArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return allMatch(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return allMatch(elements, this::isEqualTo); //TODO: test if this can be converted into elements.containsAll(this)
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean noneMatchElements(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isNotIn(elements);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isNotIn(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return !isIn(array);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isNotIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements) {
    return !isIn(elements);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isIn(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return isIn(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements) {
    return elements.contains(this);
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areEqual(this, other);
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull T> other) {
    return !isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areTheSame(this, other);
  }

  @Value
  @Builder
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableHolder<S> {

    @Nullable S s;

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final S @NotNull ... array) {
      return !isIn(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final S @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public boolean isNotInArray(@NotNull final S @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Collection<? extends @NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(@NotNull final Collection<? extends @NotNull S> elements) {
      return !elements.isEmpty() && isIn(Set.<@NotNull S>copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Set<? extends @NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(@NotNull final Set<? extends @NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotEqualTo(@Nullable final S other) {
      return !isEqualTo(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotSameAs(@Nullable final S other) {
      return !isSameAs(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> of(@Nullable final E s) {
      return EqualableHolder.<@NotNull E>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> element(@Nullable final E s) {
      return EnumHolder.<@NotNull E>of(s);
    }
  }

  @Value
  @Builder
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EnumHolder<S extends @NotNull Enum<@NotNull S>> {

    @Nullable S s;

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final S @NotNull ... array) {
      return !isIn(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final S @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public boolean isNotInArray(@NotNull final S @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Collection<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(@NotNull final Collection<@NotNull S> elements) {
      return !elements.isEmpty() && isIn(EnumSet.<@NotNull S>copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotIn(@NotNull final EnumSet<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(@NotNull final Set<@NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotEqualTo(@Nullable final S other) {
      return !isEqualTo(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotSameAs(@Nullable final S other) {
      return !isSameAs(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> of(@Nullable final E s) {
      return EnumHolder.<@NotNull E>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> Equalable.EnumHolder<@NotNull E> element(@Nullable final E s) {
      return EnumHolder.<@NotNull E>builder().s(s).build();
    }
  }

  interface EqualableString {

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqual(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    static boolean areEqual(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areEqual(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.areEqualIgnoreCase(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    static boolean areEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return EqualableString.areTheSame(a, b) || EqualableString.bothAreBlank(a, b) || (a != null && a.equalsIgnoreCase(b));
    }

    @Contract(value = "null, null -> true", pure = true)
    static boolean bothAreNotBlank(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.bothAreBlank(a, b);
    }

    @Contract(value = "null, null -> false", pure = true)
    static boolean bothAreBlank(@Nullable final String a, @Nullable final String b) {
      return a != null && b != null && a.isBlank() && b.isBlank();
    }

    @Contract(pure = true)
    static boolean areNotTheSame(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areTheSame(a, b);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableString.Holder element(@Nullable final String s) {
      return EqualableString.Holder.builder().s(s).build();
    }

    @Value
    @Builder
    @Unmodifiable
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class Holder {// implements Equalable<@NotNull String> {

      @Nullable String s;

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final String @NotNull ... array) {
        return !isIn(array);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final String @NotNull ... array) {
        return isInArray(array);
      }

      @Contract(pure = true)
      public boolean isNotInArray(@NotNull final String @NotNull [] array) {
        return !isInArray(array);
      }

      @Contract(pure = true)
      public final boolean isInArray(@NotNull final String @NotNull [] array) {
        final List<java.lang.@NotNull String> list = Arrays.stream(array).toList();
        return isIn(list);
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Collection<java.lang.@NotNull String> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Collection<java.lang.@NotNull String> elements) {
        return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull String>copyOf(elements));
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Set<java.lang.@NotNull String> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Set<java.lang.@NotNull String> elements) {
        if (null == s) {
          return false;
        }
        return elements.contains(s);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Stream<@NotNull String> elements) {
        return elements.anyMatch(p -> EqualableString.areTheSame(p, s));
      }

      @Contract(pure = true)
      public boolean isNotEqualTo(final String other) {
        return !isEqualTo(other);
      }

      @Contract(pure = true)
      public boolean isEqualTo(final String other) {
        return EqualableString.areEqual(s, other);
      }

      @Contract(pure = true)
      public boolean isNotSameAs(final String other) {
        return !isSameAs(other);
      }

      @Contract(pure = true)
      public boolean isSameAs(final String other) {
        return EqualableString.areTheSame(s, other);
      }

    }
  }

  interface EqualableInteger {

    @Contract(pure = true)
    static boolean areNotEqual(final int a, final int b) {
      return !Equalable.EqualableInteger.areEqual(a, b);
    }

    @Contract(pure = true)
    static boolean areEqual(final int a, final int b) {
      return Equalable.EqualableInteger.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areNotTheSame(final int a, final int b) {
      return !Equalable.EqualableInteger.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(final int a, final int b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableInteger.@Unmodifiable Holder integer(final int s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableInteger.@Unmodifiable Holder of(final int s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableInteger.@Unmodifiable Holder element(final int s) {
      return EqualableInteger.Holder.builder().s(s).build();
    }

    @Value
    @Builder
    @Unmodifiable
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class Holder {// implements Equalable<@NotNull Integer> {

      int s;

      @Contract(pure = true)
      public boolean isZero() {
        return Equalable.EqualableInteger.areTheSame(s, 0);
      }

      @Contract(pure = true)
      public boolean isOne() {
        return EqualableInteger.areTheSame(s, 1);
      }

      @Contract(pure = true)
      public boolean isNegative() {
        return isLessThanZero();
      }

      @Contract(pure = true)
      public boolean isPositive() {
        return isGreaterThanZero();
      }

      @Contract(pure = true)
      public boolean isGreaterThanZero() {
        return isGreaterThan(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualToZero() {
        return isGreaterThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isLessThanZero() {
        return isLessThan(0);
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualToZero() {
        return isLessThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThan(final int other) {
        return s > other;
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualTo(final int other) {
        return s >= other;
      }

      @Contract(pure = true)
      public boolean isLessThan(final int other) {
        return s < other;
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualTo(final int other) {
        return s <= other;
      }

      @Contract(pure = true)
      public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull ... array) {
        return !isIn(array);
      }

      @Contract(pure = true)
      public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull ... array) {
        return isInArray(array);
      }

      @Contract(pure = true)
      public boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
        return !isInArray(array);
      }

      @Contract(pure = true)
      public boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
        final List<java.lang.@NotNull Integer> list = IntStream.of(array).boxed().toList();
        return isIn(list);
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
        return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Integer>copyOf(elements));
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Set<java.lang.@NotNull Integer> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Set<java.lang.@NotNull Integer> elements) {
        return elements.contains(s);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final IntStream elements) {
        return elements.anyMatch(p -> EqualableInteger.areTheSame(p, s));
      }

      @Contract(pure = true)
      public boolean isNotEqualTo(final int other) {
        return !isEqualTo(other);
      }

      @Contract(pure = true)
      public boolean isEqualTo(final int other) {
        return Equalable.EqualableInteger.areEqual(s, other);
      }

      @Contract(pure = true)
      public boolean isNotSameAs(final int other) {
        return !isSameAs(other);
      }

      @Contract(pure = true)
      public boolean isSameAs(final int other) {
        return Equalable.EqualableInteger.areTheSame(s, other);
      }

      @Contract(pure = true)
      public boolean isBetweenInclusive(final int lowerBoundary, final int higherBoundary) {
        return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
      }

      @Contract(pure = true)
      public boolean isBetweenExclusive(final int lowerBoundary, final int higherBoundary) {
        return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
      }
    }
  }

  interface EqualableLong {

    @Contract(pure = true)
    static boolean areNotEqual(final long a, final long b) {
      return !EqualableLong.areEqual(a, b);
    }

    @Contract(pure = true)
    static boolean areEqual(final long a, final long b) {
      return EqualableLong.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areNotTheSame(final long a, final long b) {
      return !EqualableLong.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(final long a, final long b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableLong.Holder of(final long s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableLong.Holder element(final long s) {
      return EqualableLong.Holder.builder().s(s).build();
    }

    @Value
    @Builder
    @Unmodifiable
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class Holder {// implements Equalable<@NotNull Long> {

      long s;

      @Contract(pure = true)
      public boolean isZero() {
        return Equalable.EqualableLong.areTheSame(s, 0);
      }

      @Contract(pure = true)
      public boolean isOne() {
        return EqualableLong.areTheSame(s, 1);
      }

      @Contract(pure = true)
      public boolean isNegative() {
        return isLessThanZero();
      }

      @Contract(pure = true)
      public boolean isPositive() {
        return isGreaterThanZero();
      }

      @Contract(pure = true)
      public boolean isGreaterThanZero() {
        return isGreaterThan(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualToZero() {
        return isGreaterThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isLessThanZero() {
        return isLessThan(0);
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualToZero() {
        return isLessThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThan(final long other) {
        return s > other;
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualTo(final long other) {
        return s >= other;
      }

      @Contract(pure = true)
      public boolean isLessThan(final long other) {
        return s < other;
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualTo(final long other) {
        return s <= other;
      }

      @Contract(pure = true)
      public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull ... array) {
        return !isIn(array);
      }

      @Contract(pure = true)
      public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull ... array) {
        return isInArray(array);
      }

      @Contract(pure = true)
      public boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
        return !isInArray(array);
      }

      @Contract(pure = true)
      public boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
        final List<java.lang.Long> list = LongStream.of(array).boxed().toList();
        return isIn(list);
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
        return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Long>copyOf(elements));
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
        return elements.contains(s);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final LongStream elements) {
        return elements.anyMatch(p -> EqualableLong.areTheSame(p, s));
      }

      @Contract(pure = true)
      public boolean isNotEqualTo(final long other) {
        return !isEqualTo(other);
      }

      @Contract(pure = true)
      public boolean isEqualTo(final long other) {
        return EqualableLong.areEqual(s, other);
      }

      @Contract(pure = true)
      public boolean isNotSameAs(final long other) {
        return !isSameAs(other);
      }

      @Contract(pure = true)
      public boolean isSameAs(final long other) {
        return EqualableLong.areTheSame(s, other);
      }

      @Contract(pure = true)
      public boolean isBetweenInclusive(final long lowerBoundary, final long higherBoundary) {
        return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
      }

      @Contract(pure = true)
      public boolean isBetweenExclusive(final long lowerBoundary, final long higherBoundary) {
        return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
      }
    }
  }

  interface EqualableFloat {

    @Contract(pure = true)
    static boolean areNotEqual(final float a, final float b) {
      return !EqualableFloat.areEqual(a, b);
    }

    @Contract(pure = true)
    static boolean areEqual(final float a, final float b) {
      return EqualableFloat.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areNotTheSame(final float a, final float b) {
      return !EqualableFloat.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(final float a, final float b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableFloat.@Unmodifiable Holder of(final float s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableFloat.@Unmodifiable Holder element(final float s) {
      return EqualableFloat.Holder.builder().s(s).build();
    }

    @Value
    @Builder
    @Unmodifiable
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class Holder { // implements Equalable<@NotNull Float> {

      float s;

      @Contract(pure = true)
      public boolean isZero() {
        return EqualableFloat.areTheSame(s, 0);
      }

      @Contract(pure = true)
      public boolean isOne() {
        return EqualableFloat.areTheSame(s, 1);
      }

      @Contract(pure = true)
      public boolean isNegative() {
        return isLessThanZero();
      }

      @Contract(pure = true)
      public boolean isPositive() {
        return isGreaterThanZero();
      }

      @Contract(pure = true)
      public boolean isGreaterThanZero() {
        return isGreaterThan(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualToZero() {
        return isGreaterThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isLessThanZero() {
        return isLessThan(0);
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualToZero() {
        return isLessThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThan(final float other) {
        return s > other;
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualTo(final float other) {
        return s >= other;
      }

      @Contract(pure = true)
      public boolean isLessThan(final float other) {
        return s < other;
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualTo(final float other) {
        return s <= other;
      }

      @Contract(pure = true)
      public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull ... array) {
        return !isIn(array);
      }

      @Contract(pure = true)
      public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull ... array) {
        return isInArray(array);
      }

      @Contract(pure = true)
      public boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] array) {
        return !isInArray(array);
      }

      @Contract(pure = true)
      public boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] floatArray) {
        DoubleStream ds = IntStream.range(0, floatArray.length)
          .mapToDouble(i -> floatArray[i]);
        return isIn(ds);
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
        return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Float>copyOf(elements));
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
        return elements.contains(s);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final DoubleStream elements) {
        return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
      }

      @Contract(pure = true)
      public boolean isNotEqualTo(final float other) {
        return !isEqualTo(other);
      }

      @Contract(pure = true)
      public boolean isEqualTo(final float other) {
        return EqualableFloat.areEqual(s, other);
      }

      @Contract(pure = true)
      public boolean isNotSameAs(final float other) {
        return !isSameAs(other);
      }

      @Contract(pure = true)
      public boolean isSameAs(final float other) {
        return EqualableFloat.areTheSame(s, other);
      }
    }
  }

  interface EqualableDouble {

    @Contract(pure = true)
    static boolean areNotEqual(final double a, final double b) {
      return !EqualableDouble.areEqual(a, b);
    }

    @Contract(pure = true)
    static boolean areEqual(final double a, final double b) {
      return EqualableDouble.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areNotTheSame(final double a, final double b) {
      return !EqualableDouble.areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(final double a, final double b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableDouble.@Unmodifiable Holder of(final double s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableDouble.@Unmodifiable Holder element(final double s) {
      return EqualableDouble.Holder.builder().s(s).build();
    }

    @Value
    @Builder
    @Unmodifiable
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class Holder { // implements Equalable<@NotNull Double> {

      double s;

      @Contract(pure = true)
      public boolean isZero() {
        return EqualableDouble.areTheSame(s, 0);
      }

      @Contract(pure = true)
      public boolean isOne() {
        return EqualableDouble.areTheSame(s, 1);
      }

      @Contract(pure = true)
      public boolean isNegative() {
        return isLessThanZero();
      }

      @Contract(pure = true)
      public boolean isPositive() {
        return isGreaterThanZero();
      }

      @Contract(pure = true)
      public boolean isGreaterThanZero() {
        return isGreaterThan(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualToZero() {
        return isGreaterThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isLessThanZero() {
        return isLessThan(0);
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualToZero() {
        return isLessThanOrEqualTo(0);
      }

      @Contract(pure = true)
      public boolean isGreaterThan(final double other) {
        return s > other;
      }

      @Contract(pure = true)
      public boolean isGreaterThanOrEqualTo(final double other) {
        return s >= other;
      }

      @Contract(pure = true)
      public boolean isLessThan(final double other) {
        return s < other;
      }

      @Contract(pure = true)
      public boolean isLessThanOrEqualTo(final double other) {
        return s <= other;
      }

      @Contract(pure = true)
      public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull ... array) {
        return !isIn(array);
      }

      @Contract(pure = true)
      public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull ... array) {
        return isInArray(array);
      }

      @Contract(pure = true)
      public boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
        return !isInArray(array);
      }

      @Contract(pure = true)
      public boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
        final List<java.lang.@NotNull Double> list = DoubleStream.of(array).boxed().toList();
        return isIn(list);
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
        return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Double>copyOf(elements));
      }

      @Contract(pure = true)
      public boolean isNotIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
        return !isIn(elements);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
        return elements.contains(s);
      }

      @Contract(pure = true)
      public boolean isIn(@NotNull final DoubleStream elements) {
        return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
      }

      @Contract(pure = true)
      public boolean isNotEqualTo(final double other) {
        return !isEqualTo(other);
      }

      @Contract(pure = true)
      public boolean isEqualTo(final double other) {
        return EqualableDouble.areEqual(s, other);
      }

      @Contract(pure = true)
      public boolean isNotSameAs(final double other) {
        return !isSameAs(other);
      }

      @Contract(pure = true)
      public boolean isSameAs(final double other) {
        return EqualableDouble.areTheSame(s, other);
      }
    }
  }
}
