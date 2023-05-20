package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Value
@Unmodifiable
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableList<E> implements IList<E>, Equalable<E>
{

  @NonNull
  @NotNull
  @javax.validation.constraints.NotNull
  @PositiveOrZero
  @Builder.Default
  ArrayList<E> list = new ArrayList<>();

  public static <S> IList<S> of(S s1, S... s)
  {
    final ImmutableListBuilder<S> sImmutableListBuilder = ImmutableList.builder();
    return sImmutableListBuilder.build();
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
  public boolean contains(E o)
  {
    return list.contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  @Override
  public int indexOf(E o)
  {
    return list.indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
  @Override
  public int lastIndexOf(E o)
  {
    return list.lastIndexOf(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayList} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayList} instance
   */
  @Override
  public ImmutableList<E> deepClone()
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
   * @return an array containing all of the elements in this list in proper sequence
   */
  @Override
  public E[] toArray()
  {
    return (E[])list.toArray();
  }

  /**
   * Returns an array containing all of the elements in this list in proper sequence (from first to last element); the
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
  public E[] toArray(E[] a)
  {
    return list.toArray(a);
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public E get(int index)
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
  public Stream<E> stream()
  {
    return List.copyOf(list).stream();
  }

}