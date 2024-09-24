package de.ochmanski.immutables.immutable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.equalable.EqualableCollection;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.*;
import static java.util.Collections.emptySortedSet;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@EqualsAndHashCode(doNotUseGetters = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class ImmutableSortedSet<E extends Comparable<@NotNull E>> implements ISet<@NotNull E>
{

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  SortedSet<@NotNull E> set = emptySortedSet();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E @NotNull []> key = Equalable.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> empty()
  {
    return (ImmutableSortedSet) EMPTY_SET;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final ImmutableSortedSet<Equalable.@NotNull Dummy> EMPTY_SET = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static ImmutableSortedSet<Equalable.@NotNull Dummy> createConstant()
  {
    return ImmutableSortedSet.<Equalable.@NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> noneOf(@NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.of(Set.of(), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static ImmutableSortedSet<@NotNull String> of(
    @NotNull final String e1) {
    return ImmutableSortedSet.<@NotNull String>of(e1, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.of(Set.copyOf(List.of(e1)), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static ImmutableSortedSet<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2) {
    return ImmutableSortedSet.<@NotNull String>of(e1, e2, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.of(Set.copyOf(List.of(e1, e2)), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static ImmutableSortedSet<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3) {
    return ImmutableSortedSet.<@NotNull String>of(e1, e2, e3, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.of(Set.copyOf(List.of(e1, e2, e3)), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static ImmutableSortedSet<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4) {
    return ImmutableSortedSet.<@NotNull String>of(e1, e2, e3, e4, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.of(Set.copyOf(List.of(e1, e2, e3, e4)), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Comparable<? super @NotNull K>, V> ImmutableSortedSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entries,
    @NotNull final IntFunction<IMap.@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> entry)
  {
    return entries.stream().map(ImmutableSortedSet::toImmutableEntry).collect(ImmutableCollectors.toSortedSet(entry));
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
  @Contract(value = "_ -> new", pure = true)
  public static ImmutableSortedSet<@NotNull String> ofArray(
    @NotNull final String @NotNull [] array) {
    return ImmutableSortedSet.<@NotNull String>ofArray(array, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableSortedSet.<@NotNull S>of(List.of(array), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final SortedSet<@NotNull S> checkedSet = Collections.checkedSortedSet(new TreeSet<>(collection), getComponentTypeFromConstructor(constructor));
    return ImmutableSortedSet.<@NotNull S>builder().set(checkedSet).key(constructor).build();
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
  public static <S extends Comparable<@NotNull S>> ImmutableSortedSet<@NotNull S> of(ImmutableList<@NotNull S> immutableList) {
    return ImmutableSortedSet.<@NotNull S>of(immutableList.unwrap(), immutableList.getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ISet interface">

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
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> deepClone() {
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
  public E @NotNull [] toArray() {
    return set.toArray(getKey());
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
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public ImmutableSortedSet<@NotNull E> getSet()
  {
    return this;
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

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> add(@NotNull final E c)
  {
    return addAll(c);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> addAll(@NotNull final EqualableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableSortedSet<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(ImmutableCollectors.toSortedSet(getKey()));
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
  public ImmutableList<@NotNull E> toList() {
    return ImmutableList.<@NotNull E>of(unwrap(), key);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public ImmutableSortedSet<@NotNull E> toSet() {
    return this;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Set<@NotNull E> unwrap() {
    return set.isEmpty()
      ? Collections.checkedSortedSet(Collections.emptySortedSet(), getComponentTypeFromKey())
      : Collections.checkedSortedSet(Collections.unmodifiableSortedSet(set), getComponentTypeFromKey());
  }
  //</editor-fold>

  @NotNull
  @Override
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public String toString() {
    try {
      final String s = new ObjectMapper().writeValueAsString(set);
      return limit(s, 1000);
    } catch (JsonProcessingException e) {
      return Arrays.toString(toArray());
    }
  }

  @NotNull
  @Unmodifiable
  @SuppressWarnings(SAME_PARAMETER_VALUE)
  @Contract(value = "_, _ -> new", pure = true)
  private String limit(@NotNull final String s, final int limit)
  {
    final int end = Math.min(s.length(), Math.abs(limit));
    return s.substring(0, end);
  }
}
