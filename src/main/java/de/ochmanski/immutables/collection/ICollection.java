package de.ochmanski.immutables.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface ICollection<E> extends Checked<@NotNull E>, Iterable<@NotNull E> {

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <@Unmodifiable E> Class<@NotNull E> getComponentType(@NotNull final ICollection<@NotNull E> collection) {
    return getComponentTypeFromConstructor(collection.getKey());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <T> T @NotNull [] zeroLengthArray(@NotNull final IntFunction<@NotNull T @NotNull []> constructor) {
    return zeroLengthArray(getComponentTypeFromConstructor(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <@Unmodifiable S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return Checked.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @SuppressWarnings(UNCHECKED)
  @Contract(value = "_ -> new", pure = true)
  static <T> T @NotNull [] zeroLengthArray(@NotNull final Class<@NotNull T> type) {
    return (@NotNull T @NotNull []) Array.newInstance(type, 0);
  }

  @Contract(pure = true)
  default boolean isNotEmpty() {
    return !isEmpty();
  }

  @Contract(pure = true)
  boolean isEmpty();

  @Contract(pure = true)
  default boolean doesNotContain(@NotNull E o) {
    return !contains(o);
  }

  @Contract(pure = true)
  boolean contains(@NotNull E o);

  @Contract(pure = true)
  void forEachOrdered(@NotNull Consumer<? super @NotNull E> consumer, @NotNull Comparator<? super @NotNull E> comparator);

  @Contract(pure = true)
  default void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer) {
    sortedStream().forEachOrdered(consumer);
  }

  @Override
  @Contract(pure = true)
  void forEach(@NotNull final Consumer<? super @NotNull E> consumer);

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default Stream<@NotNull E> sortedStream(@NotNull Comparator<? super @NotNull E> comparator) {
    return stream().sorted(comparator);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Stream<@NotNull E> sortedStream() {
    return stream().sorted();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  Stream<@NotNull E> stream();
}
