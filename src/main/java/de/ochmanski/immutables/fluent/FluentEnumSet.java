package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.ISet;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
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
public class FluentEnumSet<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>>
  implements ISet<@NotNull E>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableEnumSet<@NonNull @NotNull E> set = ImmutableEnumSet.<@NotNull E>empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given constructor cannot be null.")
  IntFunction<? extends @NonNull @NotNull E @NonNull @NotNull []> constructor;

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = FluentEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = FluentEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = FluentEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: FluentEnumSet.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = FluentEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = FluentEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = FluentEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> ofGenerator(
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return FluentEnumSet.<@NotNull S>of(ImmutableEnumSet.<@NotNull S>noneOf(constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> Class<? extends @NotNull Fluent<@NotNull S>> getComponentTypeFromConstructor(
    final @NotNull IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return (Class<? extends @NotNull Fluent<S>>)ImmutableEnumSet.getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, constructor);
    return FluentEnumSet.<@NotNull S>of(of, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, constructor);
    return FluentEnumSet.<@NotNull S>of(of, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, s3, constructor);
    return FluentEnumSet.<@NotNull S>of(of, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, s3, s4, constructor);
    return FluentEnumSet.<@NotNull S>of(of, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> empty()
  {
    return (FluentEnumSet<? extends S>)EMPTY;
  }

  private static final FluentEnumSet<? extends @NotNull Fluent<?>> EMPTY = FluentEnumSet.ofGenerator(
    (IntFunction)Enum @NotNull []::new);

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  private static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> IntFunction<? extends @NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> set = ImmutableEnumSet.ofArray(array, constructor);
    return FluentEnumSet.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<? extends @NotNull S> ofCollection(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> set = ImmutableEnumSet.<@NotNull S>of(collection, constructor);
    return FluentEnumSet.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final ImmutableEnumSet<@NotNull S> set,
    @NotNull final IntFunction<? extends @NotNull S @NotNull []> constructor)
  {
    return FluentEnumSet.<@NotNull S>builder().set(set).constructor(constructor).build();
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
  public FluentEnumSet<? extends @NotNull E> deepClone()
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
  @Override
  @Contract(value = "-> new", pure = true)
  public E @NotNull [] toArray()
  {
    return set.toArray();
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
    return set.iterator();
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
    return set.stream();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumSet<? extends @NotNull E> unwrap()
  {
    return set.unwrap();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public FluentEnumSet<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to)
  {
    return FluentEnumSet.ofCollection(EnumSet.range(from, to), getConstructor());
  }

}
