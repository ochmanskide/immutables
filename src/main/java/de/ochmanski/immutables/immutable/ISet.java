package de.ochmanski.immutables.immutable;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface ISet<E> extends ImmutableCollection<@NotNull E>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link ImmutableSet#noneOf(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ISet.noneOf(Dummy[]::new);
   *   final ISet<String> actual = ISet.noneOf(String[]::new);
   *   final ISet<Integer> actual = ISet.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  private static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ISet.noneOf(String[]::new)");
  }

  default int size() {
    return getSet().size();
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  @Override
  default boolean isEmpty() {
    return getSet().isEmpty();
  }

  /**
   * Returns {@code true} if this set contains the specified element. More formally, returns {@code true} if and only if
   * this set contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this set is to be tested
   * @return {@code true} if this set contains the specified element
   */
  @Override
  default boolean contains(@NotNull final E o) {
    return getSet().contains(o);
  }

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<? extends @NotNull E> deepClone();

  /**
   * Returns an array containing all the elements in this set in proper sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this set.  (In other words, this method must allocate a new array).  The caller is thus free to
   * modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all the elements in this set in proper sequence
   */
  @NotNull
  @Contract(value = "-> new", pure = true)
  default E @NotNull [] toArray() {
    return getSet().toArray();
  }

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Override
  @Contract(pure = true)
  default Iterator<@NotNull E> iterator() {
    return getSet().iterator();
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   *   {@code Spliterator}.
   * @since 1.8
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Stream<@NotNull E> stream()
  {
    return getSet().stream();
  }

  /**
   * Returns a stream consisting of the results of applying the given
   * function to the elements of this stream.
   *
   * <p>This is an <a href="package-summary.html#StreamOps">intermediate
   * operation</a>.
   *
   * @param <R>    The element type of the new stream
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

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Set<@NotNull E> unwrap() {
    return getSet().unwrap();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findFirst()
  {
    return getSet().findFirst();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findLast() {
    return getSet().findLast();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findAny()
  {
    return getSet().findAny();
  }

  @Override
  @Contract(pure = true)
  default void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer, @NotNull final Comparator<? super @NotNull E> comparator)
  {
    getSet().forEachOrdered(consumer, comparator);
  }

  @Override
  @Contract(pure = true)
  default void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer) {
    getSet().forEachOrdered(consumer);
  }

  @Override
  @Contract(pure = true)
  default void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    getSet().forEach(consumer);
  }

  @Contract(pure = true)
  default void forEachRemaining(@NotNull final Consumer<? super @NotNull E> consumer) {
    iterator().forEachRemaining(consumer);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default ISet<@NotNull E> add(@NotNull final E c)
  {
    return addAll(c);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default ISet<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default ISet<@NotNull E> addAll(@NotNull final ImmutableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default ISet<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default ISet<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(ImmutableCollectors.toSet(getKey()));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  IList<@NotNull E> toList();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull E> getSet();
}
