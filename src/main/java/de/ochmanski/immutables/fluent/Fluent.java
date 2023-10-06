package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<E extends Enum<@NotNull E>> extends Equalable<@NotNull Fluent<@NotNull E>>
{

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Fluent<@NotNull E> other)
  {
    return this == other;
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> Stream<@NotNull F> createStream(
    @NotNull final Class<@NotNull F> clazz)
  {
    final @NotNull F @NotNull [] enumConstants = getEnumConstants(clazz);
    return Arrays.stream(enumConstants);
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> Stream<@NotNull E> createStream(
    @NotNull final E @NotNull [] entries)
  {
    return Arrays.stream(entries);
  }

  //@MustBeInvokedByOverriders
  @Contract(pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> void forEachHelper(
    @NotNull final Class<@NotNull F> clazz, @NotNull final Consumer<@NotNull F> consumer)
  {
    final @NotNull F @NotNull [] enumConstants = getEnumConstants(clazz);
    forEach(enumConstants, consumer);
  }

  @Contract(pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> void forEach(@NotNull final F @NotNull [] entries,
    @NotNull final Consumer<@NotNull F> consumer)
  {
    Arrays.asList(entries).forEach(consumer);
  }

  @NotNull
  @Contract(pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> F @NotNull [] getEnumConstants(
    @NotNull final Class<@NotNull F> enumClass)
  {
    return enumClass.getEnumConstants();
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> boolean areNotEqual(@Nullable final F a,
    @Nullable final F b)
  {
    return !areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <E extends Enum<@NotNull E>, F extends Fluent<@NotNull E>> boolean areEqual(@Nullable final F a,
    @Nullable final F b)
  {
    return Objects.equals(a, b);
  }

}
