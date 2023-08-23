package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface Equalable<T>
{

  @Contract(value = "null -> true", pure = true)
  default boolean isNotEqualTo(@Nullable final T other)
  {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final T other)
  {
    return areEqual(this, other);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b)
  {
    return !areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  static <S> boolean areEqual(@Nullable final S a, @Nullable final S b)
  {
    return Objects.equals(a, b);
  }

  @Contract(pure = true)
  static boolean areNotEqual(final double a, final double b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final double a, final double b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final float a, final float b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final float a, final float b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final long a, final long b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final long a, final long b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final int a, final int b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final int a, final int b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final short a, final short b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final short a, final short b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final byte a, final byte b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final byte a, final byte b)
  {
    return a == b;
  }

  @Contract(pure = true)
  static boolean areNotEqual(final char a, final char b)
  {
    return !areEqual(a, b);
  }

  @Contract(pure = true)
  static boolean areEqual(final char a, final char b)
  {
    return a == b;
  }

}
