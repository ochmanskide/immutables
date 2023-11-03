package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.ICollection;
import de.ochmanski.immutables.IList;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
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
public class ImmutableEnumList<E extends @NotNull Enum<@NotNull E>> implements IList<@NotNull E>
{

  @NonNull
  @NotNull("Given set cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableList<@NonNull @NotNull E> list = ImmutableList.<@NotNull E>empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Enum<?> @NotNull []> DEFAULT_KEY = Enum @NotNull []::new;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({UNCHECKED})
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> empty() {
    return EMPTY;
  }

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  private static final ImmutableEnumList EMPTY = ImmutableEnumList.builder().build();
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="static factory methods">
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
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Class<@NotNull S> componentTypeE = getComponentTypeFromConstructor(constructor);
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(EnumSet.<@NotNull S>noneOf(componentTypeE), constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableList<@NotNull S> set = ImmutableList.of(array, constructor);
    return ImmutableEnumList.<@NotNull S>of(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final Collection<? extends @NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    if (collection.isEmpty()) {
      return ImmutableEnumList.<@NotNull S>ofGenerator(constructor);
    }
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(collection, constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> ofEnumSet(
    @NotNull final EnumSet<@NotNull S> enumSet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(enumSet, constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableList<@NotNull S> immutableList) {
    return ImmutableEnumList.<@NotNull S>builder().list(immutableList).key(immutableList.getKey()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> ofAll(
    @NotNull final IntFunction<@NotNull S @NotNull []> key)
  {
    return ImmutableEnumList.<@NotNull S>allOf(key);
  }


  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> allOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> key) {
    final Class<@NotNull S> componentType = getComponentTypeFromConstructor(key);
    return ImmutableEnumList.<@NotNull S>ofEnumSet(EnumSet.<@NotNull S>allOf(componentType), key);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>ofGenerator(constructor);
  }
  //</editor-fold>

  /**
   * Returns the number of elements in this set.
   *
   * @return the number of elements in this set
   */
  @Override
  public int size()
  {
    return list.size();
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  @Override
  public boolean isEmpty()
  {
    return list.isEmpty();
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
    return list.contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   *
   * @param o
   */
  @Override
  public int indexOf(@NotNull final E o) {
    return list.indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   *
   * @param o
   */
  @Override
  public int lastIndexOf(@NotNull final E o) {
    return list.lastIndexOf(o);
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
  public ImmutableEnumList<@NotNull E> deepClone()
  {
    return toBuilder().key(key).build();
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
    return list.toArray();
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Override
  public E get(final int index) {
    return list.get(index);
  }

  @Override
  @Contract(pure = true)
  public void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    list.forEach(consumer);
  }

  @Override
  @Contract(pure = true)
  public void forEachRemaining(@NotNull final Consumer<? super @NotNull E> consumer) {
    list.forEachRemaining(consumer);
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
    return list.iterator();
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
    return list.stream();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> unwrap()
  {
    return list.unwrap();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _,_ -> new", pure = true)
  public ImmutableEnumList<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to)
  {
    return ImmutableEnumList.<@NotNull E>ofEnumSet(EnumSet.<@NotNull E>range(from, to), getKey());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableEnumSet<@NotNull S> set) {
    return set.toList();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableSet<@NotNull S> set) {
    return ImmutableEnumList.<@NotNull S>builder().list(set.toList()).key(set.getKey()).build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<@NotNull E> toSet() {
    return ImmutableEnumSet.of(this);
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">

  //</editor-fold>
}
