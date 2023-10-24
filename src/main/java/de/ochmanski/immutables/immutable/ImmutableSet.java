package de.ochmanski.immutables.immutable;

import com.stadlerrail.diag.dias.diasexport.main.collection.IMap;
import com.stadlerrail.diag.dias.diasexport.main.collection.ISet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.RAWTYPES;
import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.UNCHECKED;

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
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> constructor = defaultKey();

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(value = " -> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultKey()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> ofGenerator(@NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(Set.of(), constructor);
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

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(Set.of(e1), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(Set.of(e1, e2), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(Set.of(e1, e2, e3), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(Set.of(e1, e2, e3, e4), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> copyOf(
    @NotNull final Collection<@NotNull S> values,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.of(values, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K, V> ImmutableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entries,
    @NotNull final IntFunction<IMap.@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> entry)
  {
    return entries.stream().map(ImmutableSet::toImmutableEntry).collect(ImmutableCollectors.toSet(entry));
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private static <K, V> IMap.@Unmodifiable @UnmodifiableView @NotNull Entry<@NotNull K, @NotNull V> toImmutableEntry(
    @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> entry)
  {
    return IMap.Entry.<@NotNull K, @NotNull V>builder().key(entry.getKey()).value(entry.getValue()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <E> ImmutableSet<@NotNull E> empty()
  {
    return EMPTY_SET;
  }

  private static final ImmutableSet EMPTY_SET = ImmutableSet.builder().build();

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
  public Iterator<@NotNull E> iterator()
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
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> unwrap()
  {
    return Set.copyOf(set);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.<@NotNull S>of(List.of(array), constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSet.<@NotNull S>builder().set(Set.copyOf(collection)).constructor(constructor).build();
  }

}
