package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.collection.CollectorImpl;
import de.ochmanski.immutables.equalable.Equalable.EqualableString;
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
import static de.ochmanski.immutables.collection.CollectorImpl.Constants.CH_UNORDERED_NOID;

public interface EqualableCollectors
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
   * @param <T> the type of the input elements
   * @return a {@code Collector} which collects all the input elements into a {@code Set}
   */
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static <T extends @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull Set<@NotNull T>> toMutableSet()
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull Set<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(HashSet::add)
      .combiner(EqualableCollectors::hashSetCombiner)
      .characteristics(CH_UNORDERED_ID)
      .build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. Sorted Set">
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> toSortedSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor
  )
  {
    return toSortedSet(constructor, HashSet::add);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> toSortedSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor,
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator)
  {
    return CollectorImpl.toEqualableSortedSetCollector(accumulator, set -> EqualableSortedSet.of(set, constructor));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. Sorted Set of Strings">
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull String, @NotNull HashSet<@NotNull String>, @NotNull EqualableSortedSet<@NotNull EqualableString>> toSortedSet()
  {
    return CollectorImpl.<@NotNull String, @NotNull HashSet<@NotNull String>, @NotNull EqualableSortedSet<@NotNull EqualableString>>builder()
      .supplier(HashSet::new)
      .accumulator(Set::add)
      .combiner((left, right) ->
      {
        if(left.size() < right.size())
        {
          right.addAll(left);
          return right;
        }
        else
        {
          left.addAll(right);
          return left;
        }
      })
      .finisher(EqualableSortedSet::of)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull EqualableString, @NotNull HashSet<@NotNull EqualableString>, @NotNull EqualableSortedSet<@NotNull EqualableString>> toSortedSetOfStrings()
  {
    return toSortedSet(EqualableString[]::new);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Set of Strings">
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull String, @NotNull HashSet<@NotNull String>, @NotNull EqualableSet<@NotNull EqualableString>> toSet()
  {
    return CollectorImpl.<@NotNull String, @NotNull HashSet<@NotNull String>, @NotNull EqualableSet<@NotNull EqualableString>>builder()
      .supplier(HashSet::new)
      .accumulator(Set::add)
      .combiner((left, right) ->
      {
        if(left.size() < right.size())
        {
          right.addAll(left);
          return right;
        }
        else
        {
          left.addAll(right);
          return left;
        }
      })
      .finisher(EqualableSet::of)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull EqualableString, @NotNull HashSet<@NotNull EqualableString>, @NotNull EqualableSet<@NotNull EqualableString>> toSetOfStrings()
  {
    return toSet(EqualableString[]::new);
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
   * @param <T> the type of the input elements
   * @return a {@code Collector} that accumulates the input elements into an
   *     <a href="../Set.html#unmodifiable">unmodifiable Set</a>
   * @since 10
   */
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>> toSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor)
  {
    return toSet(constructor, HashSet::add);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, EqualableSet<@NotNull T>> toSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor,
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator)
  {
    return CollectorImpl.toEqualableSetCollector(accumulator, set -> EqualableSet.of(set, constructor));
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. Set accumulators">
  static <T extends @NotNull Equalable<@NotNull T>>
  void defaultAction(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    CollectorImpl.defaultAction(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void takeFirst(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    CollectorImpl.takeFirst(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void takeSecond(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    CollectorImpl.takeSecond(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void replace(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    CollectorImpl.replace(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void replaceDuplicates(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    CollectorImpl.replaceDuplicates(ts, e);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="7. Set combiners">

  @NotNull
  private static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  HashSet<@NotNull T> comparableSetCombiner(
    @NotNull final HashSet<@NotNull T> left,
    @NotNull final HashSet<@NotNull T> right)
  {
    return EqualableCollectors.<@NotNull T>hashSetCombiner(left, right);
  }

  @NotNull
  private static <T extends @NotNull Equalable<@NotNull T>>
  HashSet<@NotNull T> hashSetCombiner(
    @NotNull final HashSet<@NotNull T> left,
    @NotNull final HashSet<@NotNull T> right)
  {
    return CollectorImpl.<@NotNull T>hashSetCombiner(left, right);
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="8. List of String">
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull String, @NotNull ArrayList<@NotNull String>, @NotNull EqualableList<@NotNull EqualableString>> toList()
  {
    return CollectorImpl.<@NotNull String, @NotNull ArrayList<@NotNull String>, @NotNull EqualableList<@NotNull EqualableString>>builder()
      .supplier(ArrayList::new)
      .accumulator(ArrayList::add)
      .combiner((left, right) ->
      {
        if(left.size() < right.size())
        {
          right.addAll(left);
          return right;
        }
        else
        {
          left.addAll(right);
          return left;
        }
      })
      .finisher(EqualableList::of)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static Collector<@NotNull EqualableString, @NotNull ArrayList<@NotNull EqualableString>, @NotNull EqualableList<@NotNull EqualableString>> toListOfStrings()
  {
    return toList(EqualableString[]::new);
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="9. List">
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>> toList(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor
  )
  {
    return CollectorImpl.toEqualableListCollector(set -> EqualableList.of(set, constructor));
  }
  //</editor-fold>
}
