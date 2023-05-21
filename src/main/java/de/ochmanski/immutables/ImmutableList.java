package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableList<E extends Equalable<@NotNull E>> implements IList<@NotNull E>, Equalable<@NotNull E>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given list cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  List<@NonNull @NotNull E> list = List.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  Class<? extends @NonNull @NotNull Equalable<?>> keyType = Empty.class;


  private interface Empty extends Equalable<Empty>
  {
  }

  public boolean isKeyTypeEmpty()
  {
    return Empty.class == keyType;
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in this list
   */
  @Override
  public int size()
  {
    return list.size();
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */
  @Override
  public boolean isEmpty()
  {
    return list.isEmpty();
  }

  /**
   * Returns {@code true} if this list contains the specified element. More formally, returns {@code true} if and only
   * if this list contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this list is to be tested
   * @return {@code true} if this list contains the specified element
   */
  @Override
  public boolean contains(@NotNull final E o)
  {
    return list.contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  @Override
  public int indexOf(@NotNull final E o)
  {
    return list.indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
  @Override
  public int lastIndexOf(@NotNull final E o)
  {
    return list.lastIndexOf(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayList} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayList} instance
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull E> deepClone()
  {
    return toBuilder().build();
  }

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
  @Override
  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = " -> new", pure = true)
  public Optional<E[]> toArray()
  {
    if(isKeyTypeEmpty())
    {
      return Optional.empty();
    }
    if(list.isEmpty())
    {
      return Optional.of((E[])Array.newInstance(getKeyType(), 0));
    }
    return Optional.of(toArray(list));

  }

  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = "_ -> new", pure = true)
  private E[] toArray(final @NotNull List<E> e)
  {
    final E[] array = (E[])Array.newInstance(e.getClass().getComponentType(), e.size());
    return e.toArray(array);
  }

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
  @Override
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  public E[] toArray(@NotNull final E[] a)
  {
    return list.toArray(a);
  }

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
   *     <pre>String[] y = x.toArray(String[]::new);</pre>
   * @implSpec The default implementation calls the generator function with zero and then passes the resulting array
   *     to {@link #toArray(E[])}.
   * @since 11
   */
  @Override
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  public E[] toArray(@NotNull final IntFunction<@NotNull E[]> generator)
  {
    return list.toArray(generator);
  }

  // Positional Access Operations

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @Override
  @NotNull
  @Contract(pure = true)
  public E get(final int index)
  {
    return list.get(index);
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   *     {@code Spliterator}.
   * @since 1.8
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream()
  {
    return toList().stream();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> toList()
  {
    return List.copyOf(list);
  }

}
