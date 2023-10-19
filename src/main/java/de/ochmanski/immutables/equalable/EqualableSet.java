package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.ISet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.Collection;
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
public class EqualableSet<E extends @NotNull Equalable<@NotNull E>> implements ISet<@NotNull E>
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
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> constructor;

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  private static <S extends Equalable<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
      final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.apply(0).getClass().getComponentType();
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
  public EqualableSet<@NotNull E> deepClone()
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
  @Contract(value = "-> new", pure = true)
  public E @NotNull [] toArray()
  {
    return isEmpty()
        ? set.toArray(getConstructor())
        : set.toArray(newArrayNative());
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  @Contract(value = "-> new", pure = true)
  public E @NotNull [] newArrayNative()
  {
    final Class<E> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  private Class<@NotNull E> getComponentType()
  {
    return isEmpty() ? getComponentTypeFromConstructor(getConstructor()) : (Class<E>)iterator().next().getClass();
  }

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Contract(pure = true)
  public Iterator<@NotNull E> iterator()
  {
    return unwrap().iterator();
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
    return unwrap().stream();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> unwrap()
  {
    return Set.copyOf(set);
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst()
  {
    return stream().findFirst();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_,_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<S> of(
      @NotNull final Collection<@NotNull S> collection,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(Set.copyOf(collection)).constructor(constructor).build();
  }

}
