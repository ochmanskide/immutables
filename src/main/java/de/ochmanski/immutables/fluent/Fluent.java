package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  extends Equalable<@NotNull Fluent<@NotNull F>>
{

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Stream<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> createStream(
    @NotNull final Class<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> clazz)
  {
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
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
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
    forEach(enumConstants, consumer);
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
  default boolean isNotIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array)
  {
    return isNotInArray(array);
  }

  @Override
  @Contract(pure = true)
  default boolean isIn(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull ... array)
  {
    return isInArray(array);
  }

  @Override
  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return isNotIn(Fluent.<@NotNull F>toEnumSet(array));
  }

  @Override
  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return isIn(Fluent.<@NotNull F>toEnumSet(array));
  }

  @Override
  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return isNotIn(Fluent.<@NotNull F>toEnumSet(elements));
  }

  @Override
  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return isIn(Fluent.<@NotNull F>toEnumSet(elements));
  }

  @NotNull
  @Contract(value = " _ -> new", pure = true)
  private static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> toEnumSet(
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] array)
  {
    return Fluent.<@NotNull F>toEnumSet(List.of(array));
  }

  @NotNull
  @Contract(value = " _ -> new", pure = true)
  private static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> toEnumSet(
    final @NotNull Collection<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> collection)
  {
    return EnumSet.<@NotNull Enum>copyOf((Collection)collection);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final EnumSet<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> elements)
  {
    return EnumSet.complementOf(elements).contains(this);
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

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    return Equalable.<@NotNull Fluent<@NotNull F>>areTheSame(a, b);
  }
}
