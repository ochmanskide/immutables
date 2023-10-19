package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.ISet;
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

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

/**
 * Immutable wrapper of <pre>{@code java.util.EnumSet<K,V>}</pre>
 * <p>This Read-Only implementation of <pre>{@code Set<>}</pre> interface
 * doesn't accept Null elements.
 * <p>This Set doesn't allow modifications. All mutators/setters were disabled.
 *
 * @param <E> {@code @NotNull E } key
 */
@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableEnumSet<E extends @NotNull Enum<@NotNull E>> implements ISet<E>
{

  @Getter(AccessLevel.PRIVATE)
  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  Set<@NonNull @NotNull E> set = Set.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  IntFunction<? extends @NonNull @NotNull E @NonNull @NotNull []> constructor;

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: ImmutableEnumSet.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> ofGenerator(
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final Class<? extends Enum<? extends S>> componentType = getComponentTypeFromConstructor(constructor);
    final Class<@NotNull S> componentTypeE = (Class<S>)componentType;
    return ImmutableEnumSet.<@NotNull S>builder()
      .set(EnumSet.noneOf(componentTypeE))
      .constructor(constructor)
      .build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  private static <S extends Enum<? extends @NotNull S>> Class<? extends Enum<? extends @NotNull S>> getComponentTypeFromConstructor(
    final @NotNull IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return (Class<? extends Enum<? extends @NotNull S>>)constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(@NotNull final S s1,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1)).constructor(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2)).constructor(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2, s3)).constructor(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2, s3, s4)).constructor(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public static <S extends Enum<? extends @NotNull S>> ImmutableEnumSet<? extends @NotNull S> empty()
  {
    return (ImmutableEnumSet<? extends S>)EMPTY;
  }

  private static final ImmutableEnumSet<? extends Enum<?>> EMPTY = ImmutableEnumSet.ofGenerator(Enum @NotNull []::new);

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  private static <S extends Enum<? extends @NotNull S>> IntFunction<? extends @NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final EnumSet<@NotNull S> set = EnumSet.copyOf(Arrays.asList(array));
    return ImmutableEnumSet.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.copyOf(collection)).constructor(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> noneOf(
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumSet.ofGenerator(constructor);
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
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<? extends @NotNull E> deepClone()
  {
    return toBuilder().constructor(constructor).build();
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
  @Override
  @Contract(value = "-> new", pure = true)
  public E @NotNull [] toArray()
  {
    return isEmpty()
      ? set.toArray((IntFunction<E @NotNull []>)getConstructor())
      : set.toArray(newArrayNative());
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  @Contract(value = "-> new", pure = true)
  private E @NotNull [] newArrayNative()
  {
    final Class<? extends Enum<? extends E>> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @Override
  @SuppressWarnings(UNCHECKED)
  public Class<? extends @NotNull E> getComponentType()
  {
    return isEmpty()
      ? (Class<? extends E>)getComponentTypeFromConstructor(getConstructor())
      : (Class<? extends E>)iterator().next().getClass();
  }

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Override
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
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<? extends @NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumSet<? extends @NotNull E> unwrap()
  {
    return EnumSet.<E>copyOf(getSet());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _,_ -> new", pure = true)
  public ImmutableEnumSet<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to)
  {
    return ImmutableEnumSet.of(EnumSet.range(from, to), getConstructor());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<? extends @NotNull S> copyOf(
    @NotNull final Set<@NotNull S> keySet,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final EnumSet<@NotNull S> set = EnumSet.copyOf(keySet);
    return ImmutableEnumSet.<@NotNull S>builder().set(set).constructor(constructor).build();
  }

}
