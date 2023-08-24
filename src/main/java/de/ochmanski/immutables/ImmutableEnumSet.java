package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

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
public class ImmutableEnumSet<E extends @NotNull Enum<@NotNull E> & Fluent<@NotNull E>>
    implements ISet<@NotNull E>, Fluent<@NotNull E>
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
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> constructor = defaultConstructor();

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
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> ImmutableEnumSet<@NotNull S> ofGenerator(
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Class<@NotNull S> componentType = getComponentTypeFromConstructor(constructor);
    return ImmutableEnumSet.<@NotNull S>builder().constructor(constructor).set(EnumSet.noneOf(componentType)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings("unchecked")
  private static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
      final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> ImmutableEnumSet<@NotNull S> of(@NotNull final S s1)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2, s3)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3,
      @NotNull final S s4)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.of(s1, s2, s3, s4)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> ImmutableEnumSet<S> of(
      @NotNull final S @NotNull [] array)
  {
    final List<@NotNull S> list = Arrays.asList(array);
    return ImmutableEnumSet.<@NotNull S>of(list);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> ImmutableEnumSet<S> of(
      @NotNull final Collection<@NotNull S> collection)
  {
    return ImmutableEnumSet.<@NotNull S>builder().set(EnumSet.copyOf(collection)).build();
  }

  @NotNull
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  private enum Empty implements Fluent<ImmutableEnumSet.@NotNull Empty>
  {
    A,
    B,
    C;
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
  public ImmutableEnumSet<@NotNull E> deepClone()
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
   return set.toArray(getConstructor());
  }

  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = "-> new", pure = true)
  private E @NotNull [] newArrayNative()
  {
    final Class<@NotNull E> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @SuppressWarnings("unchecked")
  private Class<@NotNull E> getComponentType()
  {
    return (Class<E>)iterator().next().getClass();
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
    return toSet().iterator();
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
  public EnumSet<@NotNull E> toSet()
  {
    return EnumSet.copyOf(set);
  }

}
