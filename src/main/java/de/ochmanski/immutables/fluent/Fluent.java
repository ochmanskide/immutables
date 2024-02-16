package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface Fluent<F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  extends Equalable<@NotNull Fluent<@NotNull F>>
{

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Stream<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> createStream(
    @NotNull final Class<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> clazz)
  {
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
    return Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>stream(enumConstants);
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Stream<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> createStream(
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] entries)
  {
    return Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>stream(entries);
  }

  //@MustBeInvokedByOverriders
  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> void forEachHelper(
    @NotNull final Class<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> clazz,
    @NotNull final Consumer<? super @NotNull Equalable<@NotNull Fluent<@NotNull F>>> consumer)
  {
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
    Fluent.<@NotNull F>forEach(enumConstants, consumer);
  }

  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> void forEach(
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] entries,
    @NotNull final Consumer<? super @NotNull Equalable<@NotNull Fluent<@NotNull F>>> consumer)
  {
    Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>asList(entries).forEach(consumer);
  }

  @NotNull
  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] getEnumConstants(
    @NotNull final Class<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> enumClass)
  {
    return enumClass.getEnumConstants();
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotEqual(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    return !Fluent.<@NotNull F>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areEqual(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    return Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Override
  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isNotIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array)
  {
    return isNotInArray(array);
  }


  @Override
  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array)
  {
    return isInArray(array);
  }

  @Override
  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return 0 == array.length || isNotIn(Fluent.<@NotNull F>toEnumSet(array));
  }

  @Override
  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return 0 != array.length && isIn(Fluent.<@NotNull F>toEnumSet(array));
  }

  @Override
  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return elements.isEmpty() || isNotIn(Fluent.<@NotNull F>toEnumSet(elements));
  }

  @Override
  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return !elements.isEmpty() && isIn(Fluent.<@NotNull F>toEnumSet(elements));
  }

  /**
   * Creates an enum set initialized from the specified collection.
   * If the specified collection is an {@code EnumSet} instance, this static
   * factory method behaves identically to {@link Fluent#toEnumSet(Collection)}.
   * Otherwise, the specified collection must contain at least one element
   * (in order to determine the new enum set's element type).
   *
   * @param array the collection from which to initialize this enum set
   * @return An enum set initialized from the given collection.
   * @throws IllegalArgumentException if {@code c} is not an
   *     {@code EnumSet} instance and contains no elements
   * @throws NullPointerException if {@code c} is null
   */
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  private static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> toEnumSet(
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return Fluent.<@NotNull F>toEnumSet(List.of(array));
  }

  /**
   * Creates an enum set initialized from the specified collection.
   * If the specified collection is an {@code EnumSet} instance, this static
   * factory method behaves identically to {@link EnumSet#copyOf(EnumSet)}.
   * Otherwise, the specified collection must contain at least one element
   * (in order to determine the new enum set's element type).
   *
   * @param <F>        The class of the elements in the collection
   * @param collection the collection from which to initialize this enum set
   * @return An enum set initialized from the given collection.
   * @throws IllegalArgumentException if {@code c} is not an
   *     {@code EnumSet} instance and contains no elements
   * @throws NullPointerException if {@code c} is null
   */
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  private static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> toEnumSet(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> collection)
  {
    //noinspection unchecked,rawtypes
    return EnumSet.<@NotNull Enum>copyOf((Collection)collection);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements) {
    return elements.isEmpty() || EnumSet.complementOf(elements).contains(this);
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return elements.contains(this);
  }

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    return isSameAs(other);
  }

  @Override
  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    return !isSameAs(other);
  }

  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    return Fluent.<@NotNull F>areTheSame(this, other);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotTheSame(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    return !Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotTheSame(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b) {
    return !Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotTheSame(
    @Nullable final F a,
    @Nullable final F b) {
    return !Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    return Equalable.<@Nullable Equalable<@NotNull Fluent<@NotNull F>>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b) {
    return Equalable.<@Nullable Fluent<@NotNull F>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final F a,
    @Nullable final F b) {
    return Equalable.<@Nullable F>areTheSame(a, b);
  }

  @Value
  @Builder
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class FluentHolder<F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
    implements Equalable<@NotNull Fluent<@NotNull F>> {

    @NotNull
    Equalable<@NotNull Fluent<@NotNull F>> s;

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array) {
      return !isIn(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array) {
      return isIn(List.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>of(array));
    }

    @Contract(pure = true)
    public boolean isNotIn(final @NotNull Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(final @NotNull Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements) {
      return !elements.isEmpty() && isIn(Set.copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotIn(final @NotNull Set<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(final @NotNull Set<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements) {
      return elements.contains(s);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotEqualTo(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other) {
      return !isEqualTo(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isEqualTo(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other) {
      return Fluent.<@NotNull F>areEqual(s, other);
    }

    @Contract(value = "null -> true", pure = true)
    public boolean isNotSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other) {
      return !isSameAs(other);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean isSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other) {
      return Fluent.<@NotNull F>areTheSame(s, other);
    }
  }
}
