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
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableMap<K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
    implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, V> map = Map.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K[]> key = defaultKey();

  @NonNull
  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V[]> value = defaultValue();

  @NotNull
  private static <S extends Equalable<@NotNull S>> IntFunction<@NotNull S[]> defaultConstructor()
  {
    return (IntFunction)Empty[]::new;
  }

  private static class Empty implements Equalable<@NotNull Empty>
  {
  }

  private boolean isConstructorEmpty()
  {
    return Equalable.areEqual(constructor, defaultConstructor());
  }

  /**
   * Returns the number of elements in this map.
   *
   * @return the number of elements in this map
   */
  @Override
  public int size()
  {
    return map.size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  @Override
  public boolean isEmpty()
  {
    return map.isEmpty();
  }

  /**
   * Returns {@code true} if this map contains the specified element. More formally, returns {@code true} if and only if
   * this map contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this map is to be tested
   * @return {@code true} if this map contains the specified element
   */
  @Override
  public boolean contains(@NotNull final E o)
  {
    return map.contains(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayMap} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayMap} instance
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  /**
   * Returns an array containing all the elements in this map in proper sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this map.  (In other words, this method must allocate a new array).  The caller is thus free to
   * modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all the elements in this map in proper sequence
   */
  @Override
  @NotNull
  @Contract(value = " -> new", pure = true)
  public Optional<@Nullable E[]> toArray()
  {
    return isConstructorEmpty() && map.isEmpty()
        ? Optional.empty()
        : Optional.of(toArray(map));
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private E[] toArray(@NotNull final Map<@NotNull K, @NotNull V> e)
  {
    return e.isEmpty()
        ? createEmptyArray()
        : e.toArray(newArrayNative(e));
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private E[] createEmptyArray()
  {
    return getConstructor().apply(0);
  }

  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = "_ -> new", pure = true)
  private E[] newArrayNative(@NotNull @NotEmpty final Map<@NotNull K, @NotNull V> e)
  {
    final Class<E> componentType = (Class<E>)e.iterator().next().getClass();
    final int size = e.size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  /**
   * Returns an array containing all the elements in this map in proper sequence (from first to last element); the
   * runtime type of the returned array is that of the specified array.  If the map fits in the specified array, it is
   * returned therein.  Otherwise, a new array is allocated with the runtime type of the specified array and the size of
   * this map.
   *
   * <p>If the map fits in the specified array with room to spare
   * (i.e., the array has more elements than the map), the element in the array immediately following the end of the
   * collection is map to {@code null}.  (This is useful in determining the length of the map <i>only</i> if the caller
   * knows that the map does not contain any null elements.)
   *
   * @param a the array into which the elements of the map are to be stored, if it is big enough; otherwise, a new
   *     array of the same runtime type is allocated for this purpose.
   * @return an array containing the elements of the map
   * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type
   *     of every element in this map
   * @throws NullPointerException if the specified array is null
   */
  @Override
  @NotNull
  @Contract(value = " _ -> new", pure = true)
  public E[] toArray(@NotNull final E[] a)
  {
    return map.toArray(a);
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
    return map.toArray(generator);
  }

  /**
   * Returns an iterator over the elements in this map.  The elements are returned in no particular order (unless this
   * map is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this map
   */
  @Override
  @NotNull
  @Contract(pure = true)
  public Iterator<@NotNull K, @NotNull V> get(final int index)
  {
    return Map.copyOf(map).iterator();
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
  public Stream<@NotNull K, @NotNull V> stream()
  {
    return toMap().stream();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Map<@NotNull K, @NotNull V> toMap()
  {
    return Map.copyOf(map);
  }

}
