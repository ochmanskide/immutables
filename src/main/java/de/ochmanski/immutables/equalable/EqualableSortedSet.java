package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.immutable.IMap;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSortedSet;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotBlank;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.*;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@EqualsAndHashCode(doNotUseGetters = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableSortedSet<E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> implements ESet<@NotNull E>
{

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableSortedSet<@NotNull E> set = ImmutableSortedSet.empty();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> empty()
  {
    return (EqualableSortedSet) EMPTY_SET;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final EqualableSortedSet<Dummy> EMPTY_SET = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static EqualableSortedSet<Dummy> createConstant()
  {
    return EqualableSortedSet.<@NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> noneOf(@NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return of(Set.of(), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> of(
    @NotNull final EqualableString e1) {
    return EqualableSortedSet.<@NotNull EqualableString>of(e1, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return of(Set.of(e1), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> of(
    @NotNull final EqualableString e1,
    @NotNull final EqualableString e2) {
    return EqualableSortedSet.<@NotNull EqualableString>of(e1, e2, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return of(Set.of(e1, e2), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> of(
    @NotNull final EqualableString e1,
    @NotNull final EqualableString e2,
    @NotNull final EqualableString e3) {
    return EqualableSortedSet.<@NotNull EqualableString>of(e1, e2, e3, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return of(Set.of(e1, e2, e3), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> of(
    @NotNull final EqualableString e1,
    @NotNull final EqualableString e2,
    @NotNull final EqualableString e3,
    @NotNull final EqualableString e4) {
    return EqualableSortedSet.<@NotNull EqualableString>of(e1, e2, e3, e4, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return of(Set.of(e1, e2, e3, e4), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Comparable<? super @NotNull K> & @NotNull Equalable<? super @NotNull K>, V> EqualableSortedSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entries,
    @NotNull final IntFunction<IMap.@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> entry)
  {
    return entries.stream().map(EqualableSortedSet::toImmutableEntry).collect(EqualableCollectors.toSortedSet(entry));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> of(
    @NotNull final Collection<@NotNull String> collection) {
    final IntFunction<String[]> constructor = String[]::new;
    ImmutableSortedSet<@NotNull String> set = ImmutableSortedSet.of(collection, constructor);
    return EqualableSortedSet.<@NotNull EqualableString>ofString(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> ofString(
    @NotNull final ImmutableSortedSet<@NotNull String> immutableSet) {
    final List<EqualableString> list = immutableSet.map(EqualableString::of).toList();
    final ImmutableSortedSet<@NotNull EqualableString> wrappers = ImmutableSortedSet.<@NotNull EqualableString>of(list, EqualableString @NotNull []::new);
    return EqualableSortedSet.<@NotNull EqualableString>of(wrappers);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final EqualableCollection<@NotNull S> collection) {
    return EqualableSortedSet.<@NotNull S>of(collection.unwrap(), collection.getKey());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final ImmutableSortedSet<@NotNull S> immutableSet) {
    return EqualableSortedSet.<@NotNull S>builder().set(immutableSet).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableSortedSet<@NotNull EqualableString> ofArray(
    @NotNull final EqualableString @NotNull [] array) {
    return EqualableSortedSet.<@NotNull EqualableString>ofArray(array, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSortedSet.<@NotNull S>of(List.of(array), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSortedSet.<@NotNull S>builder().set(ImmutableSortedSet.of(collection, constructor)).build();
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private static <K extends @NotNull Comparable<? super @NotNull K>, V> IMap.@Unmodifiable @UnmodifiableView @NotNull Entry<@NotNull K, @NotNull V> toImmutableEntry(
    @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> entry) {
    return IMap.Entry.<@NotNull K, @NotNull V>of(entry);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSortedSet<@NotNull S> of(
    @NotNull final ImmutableList<@NotNull S> immutableList)
  {
    return EqualableSortedSet.<@NotNull S>of(immutableList.unwrap(), immutableList.getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ESet interface">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public <R> Stream<@NotNull R> map(@NotNull final Function<? super @NotNull E, ? extends @NotNull R> mapper)
  {
    return stream().map(mapper);
  }

  /**
   * Returns the number of elements in this set.
   *
   * @return the number of elements in this set
   */
  @Override
  public int size() {
    return set.size();
  }

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  @Override
  public boolean isEmpty() {
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
  public boolean contains(@NotNull final E o) {
    return set.contains(o);
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
  public E @NotNull [] toArray() {
    return set.toArray();
  }

  @Override
  @Contract(pure = true)
  public void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    set.forEach(consumer);
  }

  @Override
  @Contract(pure = true)
  @SuppressWarnings(SIMPLIFY_STREAM_API_CALL_CHAINS)
  public void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer)
  {
    set.stream().forEachOrdered(consumer);
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
  public Iterator<@NotNull E> iterator() {
    return set.iterator();
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   * {@code Spliterator}.
   * @since 1.8
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream() {
    return set.stream();
  }

  @NotNull
  @Override
  @Contract(value = " -> new", pure = true)
  public Optional<@Nullable E> findFirst()
  {
    final Comparator<? super E> c = Comparator.nullsLast(Comparator.naturalOrder());
    return min(c);
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public Optional<@Nullable E> min(@NotNull final Comparator<? super @NotNull E> c)
  {
    return set.stream().min(c);
  }

  @NotNull
  @Override
  @Contract(value = " -> new", pure = true)
  public Optional<@Nullable E> findLast()
  {
    final Comparator<? super E> c = Comparator.nullsFirst(Comparator.naturalOrder());
    return max(c);
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public Optional<@Nullable E> max(@NotNull final Comparator<? super @NotNull E> c)
  {
    return set.stream().max(c);
  }

  @NotNull
  @Override
  @Contract(value = " -> new", pure = true)
  public Optional<@Nullable E> findAny()
  {
    return set.stream().findAny();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ESet interface">

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSortedSet<? extends @NotNull E> deepClone()
  {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @Contract(pure = true)
  public IntFunction<@NotNull E @NotNull []> getKey()
  {
    return getSet().getKey();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSortedSet<@NotNull E> add(@NotNull final E c)
  {
    return addAll(c);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSortedSet<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSortedSet<@NotNull E> addAll(@NotNull final EqualableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSortedSet<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSortedSet<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(EqualableCollectors.toSortedSet(getKey()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters to family classes">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableList<@NotNull E> toList() {
    return EqualableList.<@NotNull E>of(this);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public EqualableSortedSet<@NotNull E> toSet() {
    return this;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> unwrap() {
    return set.unwrap();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. implementation of Comparable<EqualableSet> interface">

  /**
   * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
   * as this object is less than, equal to, or greater than the specified object.
   *
   * <p>The implementor must ensure {@link Integer#signum
   * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
   * {@code x.compareTo(y)} must throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive:
   * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
   *
   * <p>Finally, the implementor must ensure that {@code
   * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
   *
   * @param o the object to be compared.
   *
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
   *   specified object.
   *
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
   * @apiNote It is strongly recommended, but <i>not</i> strictly required that
   *   {@code (x.compareTo(y)==0) == (x.equals(y))}. Generally speaking, any class that implements the
   *   {@code Comparable} interface and violates this condition should clearly indicate this fact.  The recommended
   *   language is "Note: this class has a natural ordering that is inconsistent with equals."
   */
  @Override
  public int compareTo(@NotNull final EqualableSet<@NotNull E> o)
  {
    return EqualableSortedSet.orderAsc(this, o);
  }

  @Override
  public int compareTo(@NotNull final ESet<@NotNull E> o)
  {
    return EqualableSortedSet.orderAsc(this, o);
  }

  @Contract(pure = true)
  public int compareTo(@NotNull final EqualableCollection<@NotNull E> o)
  {
    return EqualableSortedSet.orderAsc(this, o);
  }

  @Override
  @Contract(pure = true)
  public int compareTo(@NotNull final ECollection<@NotNull E> o)
  {
    return EqualableSortedSet.orderAsc(this, o);
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableSet::orderAsc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableSet.orderAsc());
   *   }
   * </pre>
   * Compares two objects for order. Returns a negative integer, zero, or a positive integer if the first object is less
   * than, equal to, or greater than the second object. Any of the objects can be null. A null value is considered to be
   * less than a non-null value.
   *
   * @param a the first object.
   * @param b the second object.
   *
   * @return an integer value.
   */
  @Contract(pure = true)
  public static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> int orderAsc(@Nullable final ECollection<@NotNull E> a, @Nullable final ECollection<@NotNull E> b)
  {
    return a == b ? 0 : a != null ? b != null ? EqualableSet.orderAsc().compare(a, b) : -1 : 1;
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableSet::orderDesc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableSet.orderDesc());
   *   }
   * </pre>
   * Compares two objects for order. Returns a negative integer, zero, or a positive integer if the first object is less
   * than, equal to, or greater than the second object. Any of the objects can be null. A null value is considered to be
   * less than a non-null value.
   *
   * @param a the first object.
   * @param b the second object.
   *
   * @return an integer value.
   */
  @Contract(pure = true)
  public static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> int orderDesc(@Nullable final ECollection<@NotNull E> a, @Nullable final ECollection<@NotNull E> b)
  {
    return a == b ? 0 : a != null ? b != null ? orderDesc().compare(a, b) : 1 : -1;
  }

  @NotNull
  @Contract(pure = true)
  public static <S> Comparator<@NotNull S> orderAsc()
  {
    return (Comparator)ORDER_BY_ID_COMPARATOR_ASCENDING;
  }

  @NotNull
  @Contract(pure = true)
  public static <S> Comparator<@NotNull S> orderDesc()
  {
    return (Comparator)ORDER_BY_ID_COMPARATOR_DESCENDING;
  }

  @NotNull
  private static final Comparator<? extends @NotNull EqualableSet<?>> ORDER_BY_ID_COMPARATOR_ASCENDING = createComparatorAsc();

  @NotNull
  private static final Comparator<? extends @NotNull EqualableSet<?>> ORDER_BY_ID_COMPARATOR_DESCENDING = createComparatorDesc();

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableSet<@NotNull E>> createComparatorAsc()
  {
    return Comparator.nullsLast(
      Comparator.comparingInt(EqualableSet<@NotNull E>::size)
        .thenComparing(EqualableSet<@NotNull E>::getClassName, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(EqualableSet::hashCode));
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableSet<@NotNull E>> createComparatorDesc()
  {
    return Comparator.nullsFirst(
      Comparator.comparingInt(EqualableSet<@NotNull E>::size)
        .thenComparing(EqualableSet<@NotNull E>::getClassName, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparingInt(EqualableSet::hashCode));
  }

  @NonNls
  @NotNull
  @NotBlank
  @Unmodifiable
  @Contract(pure = true)
  public String getClassName()
  {
    return this.getClass().getName();
  }
  //</editor-fold>

  @NotNull
  @Override
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public String toString() {
    return getSet().toString();
  }
}
