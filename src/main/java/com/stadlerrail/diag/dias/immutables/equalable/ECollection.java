package com.stadlerrail.diag.dias.immutables.equalable;

import com.stadlerrail.diag.dias.immutables.immutable.ImmutableCollection;
import com.stadlerrail.diag.dias.immutables.immutable.ImmutableList;
import org.jetbrains.annotations.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ECollection<E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> extends Comparable<@NotNull ECollection<@NotNull E>>, Equalable<@NotNull ECollection<@NotNull E>>, ImmutableCollection<@NotNull E>
{

  @Override
  @Contract(pure = true)
  int compareTo(@NotNull final ECollection<@NotNull E> o);

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link ImmutableList#noneOf(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = IList.noneOf(Dummy[]::new);
   *   final IList<String> actual = IList.noneOf(String[]::new);
   *   final IList<Integer> actual = IList.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: IList.noneOf(String[]::new)");
  }

  default int size() {
    return unwrap().size();
  }

  @Override
  default boolean isNotEmpty()
  {
    return !isEmpty();
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */
  @Override
  default boolean isEmpty() {
    return unwrap().isEmpty();
  }

  @Override
  default boolean doesNotContain(@NotNull final E o)
  {
    return !contains(o);
  }

  @Override
  @Contract(pure = true)
  default boolean contains(@NotNull final E o) {
    return unwrap().contains(o);
  }

  default boolean doesNotContainsEqualable(@NotNull final EqualableString equalableString) {
    return !containsEqualable(equalableString);
  }

  default boolean containsEqualable(@NotNull final EqualableString equalableString) {
    return getComponentTypeFromKey().isInstance(EqualableString.class) && contains((E)equalableString);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> this", pure = true)
  default ECollection<@NotNull E> getCollection() {
    return this;
  }

  /**
   * Returns a stream consisting of the results of applying the given
   * function to the elements of this stream.
   *
   * <p>This is an <a href="package-summary.html#StreamOps">intermediate
   * operation</a>.
   *
   * @param <R> The element type of the new stream
   * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
   *               <a href="package-summary.html#Statelessness">stateless</a>
   *               function to apply to each element
   * @return the new stream
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default <R> Stream<@NotNull R> map(@NotNull final Function<? super @NotNull E, ? extends @NotNull R> mapper) {
    return stream().map(mapper);
  }

  /**
   * Returns a stream consisting of the elements of this stream that match
   * the given predicate.
   *
   * <p>This is an <a href="package-summary.html#StreamOps">intermediate
   * operation</a>.
   *
   * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
   *                  <a href="package-summary.html#Statelessness">stateless</a>
   *                  predicate to apply to each element to determine if it
   *                  should be included
   * @return the new stream
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default Stream<@NotNull E> filter(@NotNull final Predicate<? super @NotNull E> predicate) {
    return stream().filter(predicate);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default EqualableCollection<@NotNull E> addAll(@NotNull final EqualableCollection<? extends @NotNull E> c) {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default EqualableCollection<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c) {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default EqualableCollection<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c) {
    return Stream.concat(stream(), c).collect(EqualableCollectors.toList(getKey()));
  }

  @NonNls
  @NotNull
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  default String arraysToString() {
    return Arrays.toString(toArray());
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  E @NotNull [] toArray();
}
