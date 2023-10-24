package de.ochmanski.immutables.equalable;

import com.stadlerrail.diag.dias.diasexport.main.collection.ISet;
import com.stadlerrail.diag.dias.diasexport.main.collection.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.RAWTYPES;
import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class EqualableSet<E extends @NotNull Equalable<@NotNull E>> implements ISet<@NotNull E>
{

  @Getter(AccessLevel.PRIVATE)
  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableSet<@NonNull @NotNull E> set = ImmutableSet.empty();

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
    return (IntFunction)Enum @NotNull []::new;
  }

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = EqualableSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: EqualableSet.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = EqualableSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>ofGenerator(constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  public static <S extends @NotNull Equalable<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableSet<@NotNull S> set = ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, constructor);
    return EqualableSet.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <E extends @NotNull Equalable<@NotNull E>> EqualableSet<@NotNull E> empty()
  {
    return EMPTY_SET;
  }

  private static final EqualableSet EMPTY_SET = EqualableSet.builder().build();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S extends @NotNull Equalable<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(ImmutableSet.ofArray(array, constructor), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>copyOf(collection, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>ofGenerator(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>of(array, constructor);
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
  public EqualableSet<? extends @NotNull E> deepClone()
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
      ? newArrayNative()
      : set.toArray();
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
  public Class<@NotNull E> getComponentType()
  {
    return isEmpty()
      ? getComponentTypeFromConstructor(getConstructor())
      : (Class<@NotNull E>)iterator().next().getClass();
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
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> unwrap()
  {
    return getSet().unwrap();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> copyOf(
    @NotNull final Collection<@NotNull S> keySet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableSet<@NotNull S> immutableSet = ImmutableSet.<@NotNull S>copyOf(keySet, constructor);
    return EqualableSet.<@NotNull S>of(immutableSet, constructor);
  }

  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final ImmutableSet<@NotNull S> immutableSet,
    @NotNull final IntFunction<S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(immutableSet).constructor(constructor).build();
  }

}
