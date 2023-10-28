package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.lang.reflect.Array;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface ICollection<E>
{

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Class<@NotNull E> getComponentTypeFromKey() {
    return getComponentTypeFromConstructor(getKey());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <E> Class<@NotNull E> getComponentType(@NotNull final ICollection<@NotNull E> collection)
  {
    return ICollection.getComponentTypeFromConstructor(collection.getKey());
  }

  @NotNull
  @Contract(pure = true)
  IntFunction<@NotNull E @NotNull []> getKey();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <T> T @NotNull [] zeroLengthArray(@NotNull final IntFunction<@NotNull T @NotNull []> constructor)
  {
    return zeroLengthArray(getComponentTypeFromConstructor(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @SuppressWarnings(UNCHECKED)
  @Contract(value = "_ -> new", pure = true)
  static <T> T @NotNull [] zeroLengthArray(@NotNull final Class<@NotNull T> type)
  {
    return (@NotNull T @NotNull [])Array.newInstance(type, 0);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.getClass().getComponentType();
  }
}
