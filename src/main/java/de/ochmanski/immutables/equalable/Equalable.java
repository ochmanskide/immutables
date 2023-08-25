package de.ochmanski.immutables.equalable;

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

}
