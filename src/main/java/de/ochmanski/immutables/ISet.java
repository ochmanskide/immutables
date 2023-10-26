package de.ochmanski.immutables;

import de.ochmanski.immutables.immutable.ImmutableSet;
import org.jetbrains.annotations.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface ISet<E> extends ICollection<E>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link ImmutableSet#ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ISet.ofGenerator(Dummy[]::new);
   *   final ISet<String> actual = ISet.ofGenerator(String[]::new);
   *   final ISet<Integer> actual = ISet.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  private static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ISet.ofGenerator(String[]::new)");
  }

  int size();

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  boolean isEmpty();

  /**
   * Returns {@code true} if this set contains the specified element. More formally, returns {@code true} if and only if
   * this set contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this set is to be tested
   * @return {@code true} if this set contains the specified element
   */
  boolean contains(@NotNull final E o);

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
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
  E @NotNull [] toArray();

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Contract(pure = true)
  default Iterator<@NotNull E> iterator()
  {
    return unwrap().iterator();
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
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default Stream<@NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  Set<@NotNull E> unwrap();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
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
  @Contract(value = " -> new", pure = true)
  default Class<@NotNull E> getComponentType()
  {
    return ICollection.<@NotNull E>getComponentType(this);
  }

  @Contract(pure = true)
  void forEach(@NotNull final Consumer<? super @NotNull E> consumer);

}
