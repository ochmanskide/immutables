package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.collection.CollectorImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static de.ochmanski.immutables.collection.CollectorImpl.Constants.CH_UNORDERED_ID;

public interface FluentCollectors
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
   * @param <F> the type of the input elements
   * @return a {@code Collector} which collects all the input elements into a {@code Set}
   */
  @NotNull
  @Unmodifiable
  @Contract(value = " -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  Collector<@NotNull F, @NotNull HashSet<@NotNull F>, @NotNull Set<@NotNull F>> toMutableSet()
  {
    return CollectorImpl.<@NotNull F, @NotNull HashSet<@NotNull F>, @NotNull Set<@NotNull F>>builder()
      .supplier(HashSet::new)
      .accumulator(HashSet::add)
      .combiner(FluentCollectors::hashSetCombiner)
      .characteristics(CH_UNORDERED_ID)
      .build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. Sorted Set">

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
   * @param <F> the type of the input elements
   * @return a {@code Collector} that accumulates the input elements into an
   *     <a href="../Set.html#unmodifiable">unmodifiable Set</a>
   * @since 10
   */
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  Collector<@NotNull F, @NotNull HashSet<@NotNull F>, @NotNull FluentEnumSet<@NotNull F>> toSet(
    @NotNull final IntFunction<@NotNull F @NotNull []> constructor)
  {
    return CollectorImpl.toFluentSetCollector(set -> FluentEnumSet.ofCollection(set, constructor));
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. Set accumulators">
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  void defaultAction(@NotNull final Set<@NotNull F> ts, @NotNull final F e)
  {
    CollectorImpl.defaultAction(ts, e);
  }

  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  void takeFirst(@NotNull final Set<@NotNull F> ts, @NotNull final F e)
  {
    CollectorImpl.takeFirst(ts, e);
  }

  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  void takeSecond(@NotNull final Set<@NotNull F> ts, @NotNull final F e)
  {
    CollectorImpl.takeSecond(ts, e);
  }

  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  void replace(@NotNull final Set<@NotNull F> ts, @NotNull final F e)
  {
    CollectorImpl.replace(ts, e);
  }

  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  void replaceDuplicates(@NotNull final Set<@NotNull F> ts, @NotNull final F e)
  {
    CollectorImpl.replaceDuplicates(ts, e);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="7. Set combiners">
  @NotNull
  private static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  HashSet<@NotNull F> hashSetCombiner(
    @NotNull final HashSet<@NotNull F> left,
    @NotNull final HashSet<@NotNull F> right)
  {
    return CollectorImpl.<@NotNull F>hashSetCombiner(left, right);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="9. List">
  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  Collector<@NotNull F, @NotNull ArrayList<@NotNull F>, @NotNull FluentList<@NotNull F>> toList(
    @NotNull final IntFunction<@NotNull F @NotNull []> constructor
  )
  {
    return CollectorImpl.toFluentListCollector(set -> FluentEnumList.of(set, constructor));
  }
  //</editor-fold>
}
