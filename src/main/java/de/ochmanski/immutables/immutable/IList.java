package de.ochmanski.immutables.immutable;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface IList<E> extends ImmutableCollection<@NotNull E> {

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
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: IList.noneOf(String[]::new)");
  }

  default int size() {
    return getList().size();
  }

  @Override
  default boolean isNotEmpty() {
    return !isEmpty();
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */
  @Override
  default boolean isEmpty() {
    return getList().isEmpty();
  }

  @Override
  default boolean doesNotContain(@NotNull final E o) {
    return !contains(o);
  }

  /**
   * Returns {@code true} if this list contains the specified element. More formally, returns {@code true} if and only
   * if this list contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this list is to be tested
   * @return {@code true} if this list contains the specified element
   */
  @Override
  default boolean contains(@NotNull final E o) {
    return getList().contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  default int indexOf(@NotNull final E o) {
    return getList().indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
  default int lastIndexOf(@NotNull final E o) {
    return getList().lastIndexOf(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayList} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayList} instance
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  IList<@NotNull E> deepClone();

  /**
   * Returns an array containing all the elements in this list in proper sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this list.  (In other words, this method must allocate a new array).  The caller is thus free to
   * modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all the elements in this list in proper sequence
   */
  @NotNull
  @Contract(value = " -> new", pure = true)
  default E @NotNull [] toArray() {
    return getList().toArray();
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  default E get(final int index) {
    return getList().get(index);
  }

  @Override
  @Contract(pure = true)
  default void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer, @NotNull final Comparator<? super @NotNull E> comparator) {
    getList().forEachOrdered(consumer, comparator);
  }

  @Override
  @Contract(pure = true)
  default void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer) {
    getList().forEachOrdered(consumer);
  }

  @Override
  @Contract(pure = true)
  default void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    getList().forEach(consumer);
  }

  @Contract(pure = true)
  default void forEachRemaining(@NotNull final Consumer<? super @NotNull E> consumer) {
    iterator().forEachRemaining(consumer);
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
    return getList().iterator();
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   * {@code Spliterator}.
   * @since 1.8
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Stream<@NotNull E> stream() {
    return getList().stream();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default List<@NotNull E> unwrap() {
    return getList().unwrap();
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
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findFirst() {
    return getList().findFirst();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findLast() {
    return getList().findLast();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findAny() {
    return getList().findAny();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default IList<@NotNull E> add(@NotNull final E c) {
    return addAll(c);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default IList<@NotNull E> addAll(@NotNull final E c) {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default IList<@NotNull E> addAll(@NotNull final ImmutableCollection<? extends @NotNull E> c) {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default IList<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c) {
    return addAll(c.stream());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default IList<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c) {
    return Stream.concat(stream(), c).collect(ImmutableCollectors.toList(getKey()));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull E> toSet();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  IList<@NotNull E> getList();
}
