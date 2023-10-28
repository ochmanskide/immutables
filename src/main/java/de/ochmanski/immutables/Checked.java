package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface Checked<E> {

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return (Class<@NotNull S>) constructor.getClass().getComponentType();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Class<@NotNull E> getComponentTypeFromKey() {
    return getComponentTypeFromConstructor(getKey());
  }


  @NotNull
  @Contract(pure = true)
  IntFunction<@NotNull E @NotNull []> getKey();
}
