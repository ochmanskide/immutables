package de.ochmanski.immutables.fluent;
import org.jboss.logging.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Fluent<F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  extends Equalable<@NotNull Fluent<@NotNull F>>
{

  Logger log = Logger.getLogger(Fluent.class);

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Stream<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> createStream(
    @NotNull final Class<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> clazz)
  {
    log.info("31.");
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
    return Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>stream(enumConstants);
  }

  //@MustBeInvokedByOverriders
  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Stream<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> createStream(
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] entries)
  {
    log.info("32.");
    return Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>stream(entries);
  }

  //@MustBeInvokedByOverriders
  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> void forEachHelper(
    @NotNull final Class<@NotNull Equalable<@NotNull Fluent<@NotNull F>>> clazz,
    @NotNull final Consumer<? super @NotNull Equalable<@NotNull Fluent<@NotNull F>>> consumer)
  {
    log.info("33.");
    final @NotNull Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] enumConstants = getEnumConstants(clazz);
    forEach(enumConstants, consumer);
  }

  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> void forEach(
    @NotNull final Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] entries,
    @NotNull final Consumer<? super @NotNull Equalable<@NotNull Fluent<@NotNull F>>> consumer)
  {
    log.info("34.");
    Arrays.<@NotNull Equalable<@NotNull Fluent<@NotNull F>>>asList(entries).forEach(consumer);
  }

  @NotNull
  @Contract(pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> Equalable<@NotNull Fluent<@NotNull F>> @NotNull [] getEnumConstants(
    @NotNull final Class<? extends @NotNull Equalable<@NotNull Fluent<@NotNull F>>> enumClass)
  {
    log.info("35.");
    return enumClass.getEnumConstants();
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotEqual(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    log.info("36.");
    return !Fluent.<@NotNull F>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areEqual(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    log.info("37.");
    return Fluent.<@NotNull F>areTheSame(a, b);
  }

  /**
   * Fast implementation of isEqualTo() for enums only
   */
  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    //log.tracev("38.1. Fluent::isEqualTo this: {0}; other: {1}", this, other);
    final boolean b = isSameAs(other);
    log.tracev("38.2. Fluent.<Equalable<@NotNull Fluent<@NotNull F>>>isEqualTo({1}) = {2} ; {0}.isEqualTo({1}) = {2}",
      this, other, b);
    return b;
  }

  @Override
  @Contract(value = "null -> true", pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    log.info("39.");
    return !isSameAs(other);
  }

  @Override
  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull Fluent<@NotNull F>> other)
  {
    //log.tracev("40.1. Fluent::isSameAs this: {0}; other: {1}", this, other);
    final boolean b = Fluent.<@NotNull F>areTheSame(this, other);
    //log.tracev("40.2. {0}",b);
    log.tracev("40.2. Fluent.<Equalable<@NotNull Fluent<@NotNull F>>>isSameAs({1}) = {2} ; {0}.isSameAs({1}) = {2}",
      this, other, b);
    return b;
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areNotTheSame(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    log.info("41.");
    return !Fluent.<@NotNull F>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>> boolean areTheSame(
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> a,
    @Nullable final Equalable<@NotNull Fluent<@NotNull F>> b)
  {
    //log.tracev("42.1. Fluent::isSameAs a: {0}; b: {1}", a, b);
    final boolean b1 = Equalable.<@NotNull Fluent<@NotNull F>>areTheSame(a, b);
    //log.tracev("42.2. {0}", b1);
    log.tracev("42.2. Fluent::areTheSame({0}, {1}) ; Fluent.areTheSame({0}, {1}) = {2}", a, b, b1);
    return b1;
  }
}
