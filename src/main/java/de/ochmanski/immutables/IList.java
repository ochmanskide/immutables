package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

interface IList<E extends Equalable<@NotNull E>>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
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

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = IList.ofGenerator(Dummy[]::new);
   *   final IList<String> actual = IList.ofGenerator(String[]::new);
   *   final IList<Integer> actual = IList.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends Equalable<S>> IList<@NotNull S> ofGenerator(@NotNull final IntFunction<@NotNull S[]> keyType)
  {
    return ImmutableList.<S>builder().constructor(keyType).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S extends Equalable<S>> IList<@NotNull S> of(@NotNull final S s1)
  {
    return ImmutableList.<S>builder().list(List.of(s1)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Equalable<S>> IList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2)
  {
    return ImmutableList.<S>builder().list(List.of(s1, s2)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends Equalable<S>> IList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3)
  {
    return ImmutableList.<S>builder().list(List.of(s1, s2, s3)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends Equalable<S>> IList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3,
      @NotNull final S s4)
  {
    return ImmutableList.<S>builder().list(List.of(s1, s2, s3, s4)).build();
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
  @Contract(value = " -> new", pure = true)
  ImmutableList<@NotNull E> deepClone();

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
  Optional<@Nullable E[]> toArray();

  /**
   * Returns an array containing all the elements in this list in proper sequence (from first to last element); the
   * runtime type of the returned array is that of the specified array.  If the list fits in the specified array, it is
   * returned therein.  Otherwise, a new array is allocated with the runtime type of the specified array and the size of
   * this list.
   *
   * <p>If the list fits in the specified array with room to spare
   * (i.e., the array has more elements than the list), the element in the array immediately following the end of the
   * collection is set to {@code null}.  (This is useful in determining the length of the list <i>only</i> if the caller
   * knows that the list does not contain any null elements.)
   *
   * @param a the array into which the elements of the list are to be stored, if it is big enough; otherwise, a new
   *     array of the same runtime type is allocated for this purpose.
   * @return an array containing the elements of the list
   * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type
   *     of every element in this list
   * @throws NullPointerException if the specified array is null
   */
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  E[] toArray(@NotNull final E[] a);

  /**
   * Returns an array containing all the elements in this collection, using the provided {@code generator} function
   * to allocate the returned array.
   *
   * <p>If this collection makes any guarantees as to what order its elements
   * are returned by its iterator, this method must return the elements in the same order.
   *
   * @param generator a function which produces a new array of the desired type and the provided length
   * @return an array containing all the elements in this collection
   * @throws ArrayStoreException if the runtime type of any element in this collection is not assignable to the
   *     {@linkplain Class#getComponentType runtime component type} of the generated array
   * @throws NullPointerException if the generator function is null
   * @apiNote This method acts as a bridge between array-based and collection-based APIs. It allows creation of an
   *     array of a particular runtime type. Use {@link #toArray()} to create an array whose runtime type is
   *     {@code Object[]}, or use {@link #toArray(E[])} to reuse an existing array.
   *
   *     <p>Suppose {@code x} is a collection known to contain only strings.
   *     The following code can be used to dump the collection into a newly allocated array of {@code String}:
   *
   *     <pre>
   *         String[] y = x.toArray(String[]::new);</pre>
   * @implSpec The default implementation calls the generator function with zero and then passes the resulting array
   *     to {@link #toArray(E[])}.
   * @since 11
   */
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  E[] toArray(@NotNull final IntFunction<@NotNull E[]> generator);

  // Positional Access Operations

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  E get(final int index);

  @NotNull
  @Contract(value = " -> new", pure = true)
  Stream<@NotNull E> stream();

  @NotNull
  @Contract(value = " -> new", pure = true)
  List<@NotNull E> toList();

}
