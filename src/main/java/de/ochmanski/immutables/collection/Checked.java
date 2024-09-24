package de.ochmanski.immutables.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface Checked<E> {

  @NotNull
  @Unmodifiable
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return (Class<@NotNull S>) constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  default Class<@NotNull E> getComponentTypeFromKey() {
    return getComponentTypeFromConstructor(getKey());
  }

  @NotNull
  @Contract(pure = true)
  IntFunction<@NotNull E @NotNull []> getKey();
}
