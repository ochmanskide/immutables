package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableSet<E extends Equalable<@NotNull E>> implements ISet<@NotNull E>, Equalable<@NotNull E>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  Set<@NonNull @NotNull E> set = Set.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E[]> generator = defaultConstructor();

  @NotNull
  private static <S extends Equalable<@NotNull S>> IntFunction<@NotNull S[]> defaultConstructor()
  {
    return (IntFunction)Empty[]::new;
  }

  private static class Empty implements Equalable<@NotNull Empty>
  {
  }

  private boolean isKeyTypeEmpty()
  {
    return Equalable.areEqual(generator, defaultConstructor());
  }

  /**
   * Returns the number of elements in this set.
   *
   * @return the number of elements in this set
   */
  @Override
  public int size()
  {
    return set.size();
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  @Override
  public boolean isEmpty()
  {
    return set.isEmpty();
  }

  /**
   * Returns {@code true} if this set contains the specified element. More formally, returns {@code true} if and only if
   * this set contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this set is to be tested
   * @return {@code true} if this set contains the specified element
   */
  @Override
  public boolean contains(@NotNull final E o)
  {
    return set.contains(o);
  }

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull E> deepClone()
  {
    return toBuilder().build();
  }

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
  @Override
  @NotNull
  @Contract(value = " -> new", pure = true)
  public Optional<@Nullable E[]> toArray()
  {
    return isKeyTypeEmpty() && set.isEmpty()
        ? Optional.empty()
        : Optional.of(toArray(set));
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private E[] toArray(@NotNull final Set<@NotNull E> e)
  {
    return e.isEmpty()
        ? getGenerator().apply(0)
        : e.toArray(newArrayNative(e));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = "_ -> new", pure = true)
  private E[] newArrayNative(@NotNull @NotEmpty final Set<@NotNull E> e)
  {
    final Class<E> componentType = (Class<E>)e.iterator().next().getClass();
    final int size = e.size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  /**
   * Returns an array containing all the elements in this set in proper sequence (from first to last element); the
   * runtime type of the returned array is that of the specified array.  If the set fits in the specified array, it is
   * returned therein.  Otherwise, a new array is allocated with the runtime type of the specified array and the size of
   * this set.
   *
   * <p>If the set fits in the specified array with room to spare
   * (i.e., the array has more elements than the set), the element in the array immediately following the end of the
   * collection is set to {@code null}.  (This is useful in determining the length of the set <i>only</i> if the caller
   * knows that the set does not contain any null elements.)
   *
   * @param a the array into which the elements of the set are to be stored, if it is big enough; otherwise, a new
   *     array of the same runtime type is allocated for this purpose.
   * @return an array containing the elements of the set
   * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type
   *     of every element in this set
   * @throws NullPointerException if the specified array is null
   */
  @Override
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  public E[] toArray(@NotNull final E[] a)
  {
    return set.toArray(a);
  }

  /**
   * Returns an array containing all the elements in this collection, using the provided {@code generator} function to
   * allocate the returned array.
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
    return set.toArray(generator);
  }

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @Override
  @NotNull
  @Contract(pure = true)
  public Iterator<@NotNull E> get(final int index)
  {
    return Set.copyOf(set).iterator();
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
    return toSet().stream();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> toSet()
  {
    return Set.copyOf(set);
  }

}
