package de.ochmanski.immutables.equalable;

import org.jboss.logging.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface Equalable<T extends @NotNull Equalable<@NotNull T>>
{

  Logger log = Logger.getLogger(Equalable.class);

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Equalable<@NotNull T>... array)
  {
    log.info("01");
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("02");
    return isIn(elements);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> b,
    @NotNull final Function<? super @NotNull Equalable<@NotNull T>, @NotNull String> a)
  {
    log.info("03");
    final String s = a.apply(this);
    return Equalable.<@NotNull String>anyMatchIgnoreCase(b, s);
  }

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull S> s,
    @NotNull final Function<? super @NotNull S,
      @NotNull String> a, @NotNull final Collection<@NotNull String> b)
  {
    log.info("04");
    return Equalable.<@NotNull S>anyMatchIgnoreCase(s.stream().map(a), b);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Function<? super @NotNull Equalable<@NotNull T>,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b)
  {
    log.info("05");
    return Equalable.<@NotNull Equalable<@NotNull T>>anyMatchIgnoreCase(this, a, b);
  }

  @Contract(pure = true)
  static <S> boolean anyMatchIgnoreCase(@NotNull final S s, @NotNull final Function<? super @NotNull S,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b)
  {
    log.info("06");
    return Equalable.<@NotNull S>anyMatchIgnoreCase(a.apply(s), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Collection<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b)
  {
    log.info("09");
    return Equalable.<@NotNull String>anyMatchIgnoreCase(a.stream(), b);
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(
    @NotNull final Stream<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b)
  {
    log.info("07");
    final Set<@NotNull String> s = a
      .map(String::toUpperCase)
      .collect(Collectors.toUnmodifiableSet());
    return Equalable.<@NotNull String>anyMatchT(b, c -> s.contains(c.toUpperCase()));
  }

  @Contract(pure = true)
  static boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> elements, @NotNull final String text)
  {
    log.info("10");
    return Equalable.<@NotNull String>anyMatchT(elements, p -> p.equalsIgnoreCase(text));
  }

  @Contract(pure = true)
  static <T> boolean anyMatchT(
    @NotNull final Collection<? extends @NotNull T> elements,
    final Predicate<? super @NotNull T> predicate)
  {
    log.info("11");
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean anyMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    log.info("11");
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    log.info("12");
    return allMatchArray(array);
  }

  @Contract(pure = true)
  default boolean allMatchArray(@NotNull final Equalable<@NotNull T> @NotNull [] array)
  {
    log.info("12");
    return allMatch(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("13");
    return allMatch(elements, this::isEqualTo); //TODO: test if this can be converted into elements.containsAll(this)
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean allMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    log.info("13");
    return elements.stream().allMatch(predicate);
  }

  @Contract(pure = true)
  default boolean noneMatchElements(@NotNull final Equalable<@NotNull T>... array)
  {
    log.info("14");
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("15");
    return isNotIn(elements);
  }

  @Contract(pure = true)
  static <T> boolean noneMatchT(@NotNull final Collection<? extends @NotNull T> elements,
    final Predicate<? super @NotNull T> predicate)
  {
    log.info("16");
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  static <T extends @NotNull Equalable<@NotNull T>> boolean noneMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    final Predicate<? super @NotNull Equalable<@NotNull T>> predicate)
  {
    log.info("16");
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Equalable<@NotNull T> @NotNull ... array)
  {
    //log.info("17.1");
    final boolean b = isNotInArray(array);
    log.tracev("17.2. Equalable::isNotIn({1}) = {2} ; {0}.isNotIn({1}) = {2}", this, Arrays.toString(array), b);
    return b;
  }

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array)
  {
    //log.info("17.2");
    final boolean b = !isIn(array);
    log.tracev("17.2. Equalable::isNotInArray({1}) = {2} ; {0}.isNotInArray({1}) = {2}", this, Arrays.toString(array),
      b);
    return b;
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("18");
    return isNotIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("19");
    return !isIn(elements);
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Equalable<@NotNull T>... array)
  {
    //log.info("20.1");
    final boolean b = isInArray(array);
    log.tracev("20.2. Equalable::isIn({1}) = {2} ; {0}.isIn({1}) = {2}", this, Arrays.toString(array), b);
    return b;
  }

  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull T>[] array)
  {
    //log.info("21.2");
    final boolean b = isIn(List.<@NotNull Equalable<@NotNull T>>of(array));
    log.tracev("21.2. Equalable::isInArray({1}) = {2} ; {0}.isInArray({1}) = {2}", this, Arrays.toString(array), b);
    return b;
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements)
  {
    log.info("22");
    return isIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Set<@NotNull Equalable<@NotNull T>> elements)
  {
    log.tracev("23.1.: this: {0} elements: {1}", this, Arrays.toString(elements.toArray()));
    final boolean b = elements.contains(this);
    log.tracev("23.2. Equalable::isIn({1}) = {2} ; {0}.isIn({1}) = {2}", this, Arrays.toString(elements.toArray()), b);
    return b;
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final T other)
  {
    //log.info("24");
    final boolean b = !isEqualTo(other);
    log.tracev("24.2. Equalable.<<@NotNull T>isNotEqualTo({1}) = {2} ; {0}.isNotEqualTo({1}) = {2}", this, other, b);
    return b;
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull T> other)
  {
    log.info("25");
    return Equalable.<@NotNull Equalable<@NotNull T>>areEqual(this, other);
  }

  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull T> other)
  {
    log.info("26");
    return !isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull T> other)
  {
    log.info("27");
    return Equalable.<@NotNull Equalable<@NotNull T>>areTheSame(this, other);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotEqual(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    log.info("28");
    return Equalable.<@NotNull Equalable<@NotNull S>>areNotEqual(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b)
  {
    log.info("29");
    return !Equalable.<@NotNull S>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areEqual(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    log.info("30");
    return Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areEqual(@Nullable final S a, @Nullable final S b)
  {
    log.info("31");
    return Objects.equals(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areNotTheSame(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    log.info("32");
    return Equalable.<@NotNull Equalable<@NotNull S>>areNotTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotTheSame(@Nullable final S a, @Nullable final S b)
  {
    log.info("33");
    return !Equalable.<@NotNull S>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S extends @NotNull Equalable<@NotNull S>> boolean areTheSame(@Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b)
  {
    //log.info("34");
    final boolean b1 = Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
    log.tracev("34.2. Equalable::areTheSame({0},{1}) = {2}", a, b, b1);
    return b1;
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areTheSame(@Nullable final S a, @Nullable final S b)
  {
    //log.info("35");
    log.tracev("35.2. Equalable::areTheSame({0},{1}) = {2}", a, b, a == b);
    return a == b;
  }
}
