package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.collection.CollectorImpl;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumList;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static de.ochmanski.immutables.collection.CollectorImpl.Constants.CH_UNORDERED_ID;

public interface ImmutableCollectors
{

  //<editor-fold defaultstate="collapsed" desc="1. Mutable Set">
  /**
   * Returns a {@code Collector} that accumulates the input elements into a new {@code Set}. There are no guarantees on
   * the type, mutability, serializability, or thread-safety of the {@code Set} returned; if more control over the
   * returned {@code Set} is required, use {@link java.util.stream.Collectors#toCollection(Supplier)}.
   *
   * <p>This is an {@link Collector.Characteristics#UNORDERED unordered}
   * Collector.
   *
   * @param <E> the type of the input elements
   * @return a {@code Collector} which collects all the input elements into a {@code Set}
   */
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static <E>
  Collector<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull Set<@NotNull E>> toMutableSet()
  {
    return CollectorImpl.<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull Set<@NotNull E>>builder()
      .supplier(HashSet::new)
      .accumulator(HashSet::add)
      .combiner(ImmutableCollectors::hashSetCombiner)
      .characteristics(CH_UNORDERED_ID)
      .build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. Sorted Set">
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <E extends @NotNull Comparable<@NotNull E>>
  Collector<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSortedSet<@NotNull E>> toSortedSet(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor
  )
  {
    return toSortedSet(constructor, HashSet::add);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  static <E extends @NotNull Comparable<@NotNull E>>
  Collector<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSortedSet<@NotNull E>> toSortedSet(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor,
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull E>, @NotNull E> accumulator)
  {
    return CollectorImpl.toImmutableSortedSetCollector(accumulator, set -> ImmutableSortedSet.of(set, constructor));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. Set">

  /**
   * Returns a {@code Collector} that accumulates the input elements into an
   * <a href="../Set.html#unmodifiable">unmodifiable Set</a>. The returned
   * Collector disallows null values and will throw {@code NullPointerException} if it is presented with a null value.
   * If the input contains duplicate elements, an arbitrary element of the duplicates is preserved.
   *
   * <p>This is an {@link Collector.Characteristics#UNORDERED unordered}
   * Collector.
   *
   * @param <E> the type of the input elements
   * @return a {@code Collector} that accumulates the input elements into an
   *     <a href="../Set.html#unmodifiable">unmodifiable Set</a>
   * @since 10
   */
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <E>
  CollectorImpl<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSet<@NotNull E>> toSet(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor)
  {
    return CollectorImpl.toImmutableSetCollector(set -> ImmutableSet.<@NotNull E>of(set, constructor));
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " _ -> new", pure = true)
  static <E extends @NotNull Enum<@NotNull E>> Collector<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableEnumSet<@NotNull E>> toEnumSet(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor)
  {
    return CollectorImpl.toImmutableEnumSetCollector(set -> ImmutableEnumSet.<@NotNull E>of(set, constructor));
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. Set accumulators">
  static <E>
  void defaultAction(@NotNull final Set<@NotNull E> ts, @NotNull final E e)
  {
    CollectorImpl.defaultAction(ts, e);
  }

  static <E>
  void takeFirst(@NotNull final Set<@NotNull E> ts, @NotNull final E e)
  {
    CollectorImpl.takeFirst(ts, e);
  }

  static <E>
  void takeSecond(@NotNull final Set<@NotNull E> ts, @NotNull final E e)
  {
    CollectorImpl.takeSecond(ts, e);
  }

  static <E>
  void replace(@NotNull final Set<@NotNull E> ts, @NotNull final E e)
  {
    CollectorImpl.replace(ts, e);
  }

  static <E>
  void replaceDuplicates(@NotNull final Set<@NotNull E> ts, @NotNull final E e)
  {
    CollectorImpl.replaceDuplicates(ts, e);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="7. Set combiners">
  @NotNull
  private static <E>
  HashSet<@NotNull E> hashSetCombiner(
    @NotNull final HashSet<@NotNull E> left,
    @NotNull final HashSet<@NotNull E> right)
  {
    return CollectorImpl.<@NotNull E>hashSetCombiner(left, right);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="9. List">
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <E>
  Collector<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableList<@NotNull E>> toList(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor
  )
  {
    return CollectorImpl.toImmutableListCollector(set -> ImmutableList.of(set, constructor));
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " _ -> new", pure = true)
  static <E extends @NotNull Enum<@NotNull E>>
  CollectorImpl<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableEnumList<@NotNull E>> toEnumList(
    @NotNull final IntFunction<@NotNull E @NotNull []> constructor)
  {
    return CollectorImpl.toImmutableEnumListCollector(set -> ImmutableEnumList.<@NotNull E>of(set, constructor));
  }
  //</editor-fold>
}
