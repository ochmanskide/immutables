package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.ISet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableSet<E> implements ISet<@NotNull E>
{

  @Getter(AccessLevel.PRIVATE)
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
  @Contract(value = "_ -> new", pure = true)
  static <S> ImmutableSet<@NotNull S> ofGenerator(@NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.<@NotNull S>builder().constructor(constructor).set(Set.of()).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  private static <S> Class<? extends @NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return (Class<? extends @NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

  /**
   * Returns the number of elements in this set.
   *
   * @return the number of elements in this set
   */
  public int size()
  {
    return set.size();
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
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
  public boolean contains(@NotNull final E o)
  {
    return set.contains(o);
  }

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
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
    final Class<? extends @NotNull E> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  public Class<? extends @NotNull E> getComponentType()
  {
    return isEmpty()
      ? getComponentTypeFromConstructor(getConstructor())
      : (Class<? extends @NotNull E>)iterator().next().getClass();
  }

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Contract(pure = true)
  public Iterator<? extends @NotNull E> iterator()
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
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<? extends @NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<? extends @NotNull E> unwrap()
  {
    return Set.copyOf(set);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableSet<? extends @NotNull S> of(
    @NotNull final Collection<? extends @NotNull S> collection)
  {
    return ImmutableSet.<@NotNull S>builder().set(Set.copyOf(collection)).build();
  }

}
