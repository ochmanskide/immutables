package de.ochmanski.immutables.equalable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

import static de.ochmanski.immutables.constants.Constants.BLANK;
import static de.ochmanski.immutables.constants.Constants.Warning.NULLABLE_PROBLEMS;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface Equalable<T extends @NotNull Equalable<@NotNull T>> {

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull S> s,
                                        @NotNull final Function<? super @NotNull S, @NotNull String> a,
                                        @NotNull final Collection<@NotNull String> b) {
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
  static <S extends @NotNull Equalable<@NotNull S>> Equalable.Holder<@NotNull S> element(@Nullable final S s) {
    return Holder.<@NotNull S>builder().s(s).build();
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@Nullable final E s) {
    return EqualableEnum.<@NotNull E>element(s);
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E s) {
    return EqualableEnum.<@NotNull E>of(s);
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

  @Contract(pure = true)
  default boolean isNotEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areEqual(this, other);
  }

  @Contract(pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull T> other) {
    return !isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areTheSame(this, other);
  }

  //<editor-fold defaultstate="collapsed" desc="2. implements Holder<Equalable>">
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Holder<S extends @NotNull Equalable<@NotNull S>> implements Not<@NotNull S> {

    @Nullable S s;

    @Override
    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final S @NotNull ... array) {
      return isInArray(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<? extends @NotNull S> elements) {
      return !elements.isEmpty() && isIn(Set.<@NotNull S>copyOf(elements));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<? extends @NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Stream<? extends @NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.anyMatch(p -> areEqual(p, s));
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>of(s);
    }
  }
  //</editor-fold>

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableEnum<S extends @NotNull Enum<@NotNull S>> {// implements Not<@NotNull S> { // & @NotNull Equalable<@NotNull S>> implements Equalable<S> {

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static <E extends @NotNull Enum<@NotNull E>> boolean areNotEqual(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b) {
      return !EqualableEnum.<@NotNull E>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    static <E extends @NotNull Enum<@NotNull E>> boolean areEqual(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b) {
      return EqualableEnum.<@NotNull E>areTheSame(a, b);
    }

    @Contract(pure = true)
    static <E extends @NotNull Enum<@NotNull E>> boolean areNotTheSame(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b) {
      return !EqualableEnum.<@NotNull E>areTheSame(a, b);
    }

    @Contract(pure = true)
    static <E extends @NotNull Enum<@NotNull E>> boolean areTheSame(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b) {
      return Equalable.<@NotNull Enum<@NotNull E>>areTheSame(a, b);
    }

    //<editor-fold defaultstate="collapsed" desc="2. implements EqualableHolder<Enum>">

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
    public final boolean isNotInArray(@NotNull final S @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Contract(pure = true)
    public final boolean isNotIn(final @NotNull Stream<? extends @NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(final @NotNull Stream<? extends @NotNull S> elements) {
      return elements.anyMatch(p -> EqualableEnum.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<@NotNull S> elements) {
      return !elements.isEmpty() && isIn(EnumSet.<@NotNull S>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final EnumSet<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<@NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final S other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final S other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>builder().s(s).build();
    }
    //</editor-fold>
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableString implements Equalable<@NotNull EqualableString>, Not<@NotNull Equalable<@NotNull EqualableString>> {

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqual(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    static boolean areEqual(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return Equalable.<@NotNull EqualableString>areEqual(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqual(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    static boolean areEqual(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areEqual(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqualIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areEqualIgnoreCase(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqualIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return EqualableString.<@NotNull EqualableString>areTheSame(a, b) || EqualableString.<@NotNull EqualableString>bothAreBlank(a, b) || (a != null && ((EqualableString) a).isEqualToIgnoreCase(b));
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    static boolean areNotEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areEqualIgnoreCase(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return EqualableString.<@NotNull String>areTheSame(a, b) || EqualableString.<@NotNull String>bothAreBlank(a, b) || (a != null && a.equalsIgnoreCase(b));
    }

    @Contract(value = "null, null -> true", pure = true)
    static boolean bothAreNotBlank(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>bothAreBlank(a, b);
    }

    @Contract(value = "null, null -> false", pure = true)
    static boolean bothAreBlank(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return a != null && b != null && ((EqualableString) a).isBlank() && ((EqualableString) b).isBlank();
    }

    @Contract(value = "null, null -> true", pure = true)
    static boolean bothAreNotBlank(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>bothAreBlank(a, b);
    }

    @Contract(value = "null, null -> false", pure = true)
    static boolean bothAreBlank(@Nullable final String a, @Nullable final String b) {
      return a != null && b != null && a.isBlank() && b.isBlank();
    }

    @Contract(pure = true)
    static boolean areNotTheSame(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return Equalable.<@NotNull EqualableString>areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areNotTheSame(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areTheSame(a, b);
    }

    @Contract(pure = true)
    static boolean areTheSame(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areTheSame(a, b);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableString of(@Nullable final String s) {
      return EqualableString.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableString element(@Nullable final String s) {
      return EqualableString.builder().plain(s).build();
    }

    @Contract(value = "null -> false", pure = true)
    static boolean isNotNullAndNotBlank(@Nullable final Equalable<@NotNull EqualableString> s) {
      return !EqualableString.<@NotNull EqualableString>isNullOrBlank(s);
    }

    @Contract(value = "null -> true", pure = true)
    static boolean isNullOrBlank(@Nullable final Equalable<@NotNull EqualableString> s) {
      return null == s || ((EqualableString) s).isBlank();
    }

    @Contract(value = "null -> false", pure = true)
    static boolean isNotNullAndNotBlank(@Nullable final String s) {
      return !EqualableString.<@NotNull String>isNullOrBlank(s);
    }

    @Contract(value = "null -> true", pure = true)
    static boolean isNullOrBlank(@Nullable final String s) {
      return null == s || s.isBlank();
    }

    //<editor-fold defaultstate="collapsed" desc="3. implements EqualableHolder<String>">

    @Nullable String plain;

    @Override
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return !isIn(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final String @NotNull ... array) {
      return !isIn(array);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final String @NotNull ... array) {
      return isInArray(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return !isInIgnoreCase(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final String @NotNull ... array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final String @NotNull ... array) {
      return isInArrayIgnoreCase(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      return !isInArray(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isInArray(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      final List<@NotNull Equalable<@NotNull EqualableString>> list = Arrays.stream(array).toList();
      return isIn(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final String @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@NotNull final String @NotNull [] array) {
      final List<@NotNull String> list = Arrays.stream(array).toList();
      return isInPlain(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArrayIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInArrayIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      final List<@NotNull Equalable<@NotNull EqualableString>> list = Arrays.stream(array).toList();
      return isInIgnoreCase(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArrayIgnoreCase(@NotNull final String @NotNull [] array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInArrayIgnoreCase(@NotNull final String @NotNull [] array) {
      final List<@NotNull String> list = Arrays.stream(array).toList();
      return isInIgnoreCasePlain(list);
    }

    @Override
    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isIn(elements);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !elements.isEmpty() && isIn(Set.<@NotNull Equalable<@NotNull EqualableString>>copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final Collection<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Collection<@NotNull String> elements) {
      return !elements.isEmpty() && isInPlain(Set.<@NotNull String>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !elements.isEmpty() && isInIgnoreCase(Set.<@NotNull Equalable<@NotNull EqualableString>>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final Collection<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final Collection<@NotNull String> elements) {
      return !elements.isEmpty() && isInIgnoreCasePlain(Set.<@NotNull String>copyOf(elements));
    }

    @Override
    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isIn(elements);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.contains(this);
    }

    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final Set<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Set<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.contains(plain);
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> ((EqualableString) p).isEqualToIgnoreCase(plain));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final Set<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final Set<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> p.equalsIgnoreCase(plain));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Stream<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return elements.anyMatch(p -> ((EqualableString) p).isEqualTo(plain));
    }

    @Contract(pure = true)
    public final boolean isNotInPlain(@NotNull final Stream<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Stream<@NotNull String> elements) {
      return elements.anyMatch(p -> EqualableString.areEqual(p, plain));
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableString> other) {
      return isEqualTo(null == other ? null : getPlain(other));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final String other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final String other) {
      return EqualableString.areEqual(plain, other);
    }

    @Contract(pure = true)
    public final boolean isNotEqualToIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean equalsIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean isEqualToIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return isEqualToIgnoreCase(null == other ? null : getPlain(other));
    }

    @Contract(pure = true)
    public final boolean isNotEqualToIgnoreCase(@Nullable final String other) {
      return !isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean equalsIgnoreCase(@Nullable final String other) {
      return isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean isEqualToIgnoreCase(@Nullable final String other) {
      return EqualableString.<@NotNull String>areEqualIgnoreCase(plain, other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isSameAs(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final Equalable<@NotNull EqualableString> other) {
      return EqualableString.<@NotNull String>areTheSame(plain, null == other ? null : getPlain(other));
    }

    @NonNls
    @Nullable
    @Unmodifiable
    @Contract(pure = true)
    private static String getPlain(@NotNull final Equalable<@NotNull EqualableString> other) {
      return ((EqualableString) other).getPlain();
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final String other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final String other) {
      return EqualableString.<@NotNull String>areTheSame(plain, other);
    }

    @JsonIgnore
    public final boolean isNotBlank() {
      return !isBlank();
    }

    @JsonIgnore
    public final boolean isBlank() {
      return Equalable.<@NotNull String>areTheSame(plain, BLANK) || EqualableString.<@NotNull String>isNullOrBlank(plain);
    }
    //</editor-fold>
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableInteger {

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
    static Equalable.EqualableInteger integer(final int s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableInteger of(final int s) {
      return EqualableInteger.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableInteger element(final int s) {
      return EqualableInteger.builder().s(s).build();
    }

    //<editor-fold defaultstate="collapsed" desc="4. implements EqualableHolder<int>">

    int s;

    @Contract(pure = true)
    public final boolean isNotZero() {
      return !isZero();
    }

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableInteger.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableInteger.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableInteger.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final int other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final int other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final int other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final int other) {
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
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
      final List<java.lang.@NotNull Integer> list = IntStream.of(array).boxed().toList();
      return isIn(list);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Integer>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Integer> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Integer> elements) {
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final IntStream elements) {
      return elements.anyMatch(p -> EqualableInteger.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final int other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final int other) {
      return Equalable.EqualableInteger.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final int other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final int other) {
      return Equalable.EqualableInteger.areTheSame(s, other);
    }

    @Contract(pure = true)
    public final boolean isBetweenInclusive(final int lowerBoundary, final int higherBoundary) {
      return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
    }

    @Contract(pure = true)
    public final boolean isBetweenExclusive(final int lowerBoundary, final int higherBoundary) {
      return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
    }
    //</editor-fold>
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableLong {

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
    static Equalable.EqualableLong of(final long s) {
      return EqualableLong.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableLong element(final long s) {
      return EqualableLong.builder().s(s).build();
    }

    //<editor-fold defaultstate="collapsed" desc="5. implements EqualableHolder<long>">

    long s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableLong.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableLong.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableLong.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final long other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final long other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final long other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final long other) {
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
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
      final List<java.lang.Long> list = LongStream.of(array).boxed().toList();
      return isIn(list);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Long>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final LongStream elements) {
      return elements.anyMatch(p -> EqualableLong.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final long other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final long other) {
      return EqualableLong.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final long other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final long other) {
      return EqualableLong.areTheSame(s, other);
    }

    @Contract(pure = true)
    public final boolean isBetweenInclusive(final long lowerBoundary, final long higherBoundary) {
      return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
    }

    @Contract(pure = true)
    public final boolean isBetweenExclusive(final long lowerBoundary, final long higherBoundary) {
      return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
    }
    //</editor-fold>
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableFloat {

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
    static Equalable.EqualableFloat of(final float s) {
      return EqualableFloat.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Equalable.EqualableFloat element(final float s) {
      return EqualableFloat.builder().s(s).build();
    }

    //<editor-fold defaultstate="collapsed" desc="6. implements EqualableHolder<float>">

    float s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableFloat.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableFloat.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableFloat.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final float other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final float other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final float other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final float other) {
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
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] floatArray) {
      DoubleStream ds = IntStream.range(0, floatArray.length)
        .mapToDouble(i -> floatArray[i]);
      return isIn(ds);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Float>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final DoubleStream elements) {
      return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final float other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final float other) {
      return EqualableFloat.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final float other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final float other) {
      return EqualableFloat.areTheSame(s, other);
    }
    //</editor-fold>
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableDouble {

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
    static EqualableDouble of(final double s) {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static EqualableDouble element(final double s) {
      return EqualableDouble.builder().s(s).build();
    }

    //<editor-fold defaultstate="collapsed" desc="7. implements EqualableHolder<double>">

    double s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableDouble.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableDouble.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableDouble.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final double other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final double other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final double other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final double other) {
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
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
      final List<java.lang.@NotNull Double> list = DoubleStream.of(array).boxed().toList();
      return isIn(list);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Double>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final DoubleStream elements) {
      return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final double other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final double other) {
      return EqualableDouble.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final double other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final double other) {
      return EqualableDouble.areTheSame(s, other);
    }

    //</editor-fold>
  }

  @Unmodifiable
  enum Dummy implements Equalable<@NotNull Dummy> {
    DUMMY_ENUM_ITEM
  }
}
