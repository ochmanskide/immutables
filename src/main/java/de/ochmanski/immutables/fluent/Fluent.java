package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  extends Equalable<@NotNull Fluent<@NotNull F>>
{

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> Stream<@NotNull F> createStream(
    @NotNull final Class<? extends @NotNull F> clazz)
  {
    final @NotNull F @NotNull [] enumConstants = getEnumConstants(clazz);
    return Arrays.stream(enumConstants);
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> Stream<@NotNull F> createStream(
    @NotNull final F @NotNull [] entries)
  {
    return Arrays.stream(entries);
  }

  //@MustBeInvokedByOverriders
  @Contract(pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> void forEachHelper(
    @NotNull final Class<@NotNull F> clazz, @NotNull final Consumer<? super @NotNull F> consumer)
  {
    final @NotNull F @NotNull [] enumConstants = getEnumConstants(clazz);
    forEach(enumConstants, consumer);
  }

  @Contract(pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> void forEach(@NotNull final F @NotNull [] entries,
    @NotNull final Consumer<? super @NotNull F> consumer)
  {
    Arrays.asList(entries).forEach(consumer);
  }

  @NotNull
  @Contract(pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> F @NotNull [] getEnumConstants(
    @NotNull final Class<? extends @NotNull F> enumClass)
  {
    return enumClass.getEnumConstants();
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> boolean areNotEqual(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b)
  {
    return !areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> boolean areEqual(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b)
  {
    return Fluent.<@NotNull F>areTheSame(a, b);
  }

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Fluent<@NotNull F> other)
  {
    return isSameAs(other);
  }

  @Override
  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Fluent<@NotNull F> other)
  {
    return !isSameAs(other);
  }

  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Fluent<@NotNull F> other)
  {
    return Fluent.<@NotNull F>areTheSame(this, other);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> boolean areNotTheSame(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b)
  {
    return !Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends Enum<@NotNull F> & Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final Fluent<@NotNull F> a,
    @Nullable final Fluent<@NotNull F> b)
  {
    return Equalable.<@Nullable Fluent<@NotNull F>>areTheSame(a, b);
  }
}
