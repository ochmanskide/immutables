package de.ochmanski.immutables;

import de.ochmanski.immutables.immutable.ImmutableList;
import org.jetbrains.annotations.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface IList<E> extends ICollection<E>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link ImmutableList#ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = IList.ofGenerator(Dummy[]::new);
   *   final IList<String> actual = IList.ofGenerator(String[]::new);
   *   final IList<Integer> actual = IList.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: IList.ofGenerator(String[]::new)");
  }

  int size();

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */
  boolean isEmpty();

  /**
   * Returns {@code true} if this list contains the specified element. More formally, returns {@code true} if and only
   * if this list contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this list is to be tested
   * @return {@code true} if this list contains the specified element
   */
  boolean contains(@NotNull final E o);

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  int indexOf(@NotNull final E o);

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
  int lastIndexOf(@NotNull final E o);

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
  E @NotNull [] toArray();

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  E get(final int index);


  @Contract(pure = true)
  default void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    unwrap().forEach(consumer);
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
  @Contract(pure = true)
  default Iterator<@NotNull E> iterator() {
    return unwrap().iterator();
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
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Stream<@NotNull E> stream() {
    return unwrap().stream();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  List<@NotNull E> unwrap();

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
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  default <R> Stream<@NotNull R> map(@NotNull final Function<? super @NotNull E, ? extends @NotNull R> mapper) {
    return stream().map(mapper);
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findFirst()
  {
    return stream().findFirst();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  default Optional<@Nullable E> findAny()
  {
    return stream().findAny();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Class<@NotNull E> getComponentType()
  {
    return getComponentTypeFromKey();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull E> toSet();
}
