package de.ochmanski.immutables.collection;

import de.ochmanski.immutables.equalable.*;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.fluent.FluentEnumSet;
import de.ochmanski.immutables.fluent.FluentList;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSet;
import de.ochmanski.immutables.immutable.ImmutableSortedSet;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumList;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static de.ochmanski.immutables.collection.CollectorImpl.Constants.CH_UNORDERED_NOID;

@Value
@Getter
@Accessors(fluent = true)
@Unmodifiable
@RequiredArgsConstructor
@Builder
public class CollectorImpl<T, A, R> implements Collector<@NotNull T, @NotNull A, @NotNull R> {

  @NotNull
  Supplier<@NotNull A> supplier;

  @NotNull
  BiConsumer<@NotNull A, @NotNull T> accumulator;

  @NotNull
  BinaryOperator<@NotNull A> combiner;

  @NotNull
  @Builder.Default
  Function<@NotNull A, @NotNull R> finisher = castingIdentity();

  @NotNull
  Set<@NotNull Characteristics> characteristics;

  @NotNull
  @Contract(pure = true)
  public static <I, R> Function<@NotNull I, @NotNull R> castingIdentity() {
    return i -> (R) i;
  }

  //<editor-fold defaultstate="collapsed" desc="1. static factory methods">
  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  public static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  CollectorImpl<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ESet<@NotNull T>> toESetCollector(
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator,
    @NotNull final Function<@NotNull HashSet<@NotNull T>, @NotNull ESet<@NotNull T>> finisher)
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ESet<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  public static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  CollectorImpl<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>> toEqualableSetCollector(
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator,
    @NotNull final Function<@NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>> finisher)
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  public static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  CollectorImpl<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> toEqualableSortedSetCollector(
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator,
    @NotNull final Function<@NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> finisher)
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  CollectorImpl<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EList<@NotNull T>> toEListCollector(
    @NotNull final Function<@NotNull ArrayList<@NotNull T>, @NotNull EList<@NotNull T>> finisher)
  {
    return CollectorImpl.<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EList<@NotNull T>>builder()
      .supplier(ArrayList::new)
      .combiner(CollectorImpl::arrayListCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  CollectorImpl<@NotNull F, @NotNull HashSet<@NotNull F>, @NotNull FluentEnumSet<@NotNull F>> toFluentSetCollector(
    @NotNull final Function<@NotNull HashSet<@NotNull F>, @NotNull FluentEnumSet<@NotNull F>> finisher)
  {
    return CollectorImpl.<@NotNull F, @NotNull HashSet<@NotNull F>, @NotNull FluentEnumSet<@NotNull F>>builder()
      .supplier(HashSet::new)
      .accumulator(Set::add)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E>
  CollectorImpl<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSet<@NotNull E>> toImmutableSetCollector(
    @NotNull final Function<@NotNull HashSet<@NotNull E>, @NotNull ImmutableSet<@NotNull E>> finisher)
  {
    return CollectorImpl.<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSet<@NotNull E>>builder()
      .supplier(HashSet::new)
      .accumulator(Set::add)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>>
  CollectorImpl<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableEnumSet<@NotNull E>> toImmutableEnumSetCollector(
    @NotNull final Function<@NotNull HashSet<@NotNull E>, @NotNull ImmutableEnumSet<@NotNull E>> finisher)
  {
    return CollectorImpl.<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableEnumSet<@NotNull E>>builder()
      .supplier(HashSet::new)
      .accumulator(Set::add)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_, _ -> new", pure = true)
  public static <E extends @NotNull Comparable<@NotNull E>>
  CollectorImpl<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSortedSet<@NotNull E>> toImmutableSortedSetCollector(
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull E>, @NotNull E> accumulator,
    @NotNull final Function<@NotNull HashSet<@NotNull E>, @NotNull ImmutableSortedSet<@NotNull E>> finisher)
  {
    return CollectorImpl.<@NotNull E, @NotNull HashSet<@NotNull E>, @NotNull ImmutableSortedSet<@NotNull E>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(CollectorImpl::hashSetCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  CollectorImpl<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>> toEqualableListCollector(
    @NotNull final Function<@NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>> finisher)
  {
    return CollectorImpl.<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>>builder()
      .supplier(ArrayList::new)
      .accumulator(List::add)
      .combiner(CollectorImpl::arrayListCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E>
  CollectorImpl<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableList<@NotNull E>> toImmutableListCollector(
    @NotNull final Function<@NotNull ArrayList<@NotNull E>, @NotNull ImmutableList<@NotNull E>> finisher)
  {
    return CollectorImpl.<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableList<@NotNull E>>builder()
      .supplier(ArrayList::new)
      .accumulator(List::add)
      .combiner(CollectorImpl::arrayListCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>>
  CollectorImpl<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableEnumList<@NotNull E>> toImmutableEnumListCollector(
    @NotNull final Function<@NotNull ArrayList<@NotNull E>, @NotNull ImmutableEnumList<@NotNull E>> finisher)
  {
    return CollectorImpl.<@NotNull E, @NotNull ArrayList<@NotNull E>, @NotNull ImmutableEnumList<@NotNull E>>builder()
      .supplier(ArrayList::new)
      .accumulator(List::add)
      .combiner(CollectorImpl::arrayListCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <F extends @NotNull Enum<@NotNull F> & @NotNull Fluent<? extends @NotNull F>>
  CollectorImpl<@NotNull F, @NotNull ArrayList<@NotNull F>, @NotNull FluentList<@NotNull F>> toFluentListCollector(
    @NotNull final Function<@NotNull ArrayList<@NotNull F>, @NotNull FluentList<@NotNull F>> finisher)
  {
    return CollectorImpl.<@NotNull F, @NotNull ArrayList<@NotNull F>, @NotNull FluentList<@NotNull F>>builder()
      .supplier(ArrayList::new)
      .accumulator(List::add)
      .combiner(CollectorImpl::arrayListCombiner)
      .finisher(finisher)
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. Set accumulators">
  public static <T>
  void defaultAction(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    takeFirst(ts, e);
  }

  public static <T>
  void takeFirst(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    ts.add(e);
  }

  public static <T>
  void takeSecond(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    replace(ts, e);
  }

  public static <T>
  void replace(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    replaceDuplicates(ts, e);
  }

  public static <T>
  void replaceDuplicates(@NotNull final Set<@NotNull T> ts, @NotNull final T e)
  {
    if(ts.add(e))
    {
      return;
    }
    ts.remove(e);
    ts.add(e);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. Set combiners">
  @NotNull
  public static <T>
  HashSet<@NotNull T> hashSetCombiner(
    @NotNull final HashSet<@NotNull T> left,
    @NotNull final HashSet<@NotNull T> right)
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
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. List accumulators">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. List combiners">
  @NotNull
  public static <T>
  ArrayList<@NotNull T> arrayListCombiner(
    @NotNull final ArrayList<@NotNull T> left,
    @NotNull final ArrayList<@NotNull T> right)
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
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. Constants">
  public interface Constants {
    @NotNull
    Set<Collector.@NotNull Characteristics>
      CH_CONCURRENT_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
      Collector.Characteristics.UNORDERED,
      Collector.Characteristics.IDENTITY_FINISH)),

    CH_CONCURRENT_NOID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
      Collector.Characteristics.UNORDERED)),

    CH_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH)),

    CH_UNORDERED_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
      Collector.Characteristics.IDENTITY_FINISH)),

    CH_NOID = Collections.emptySet(),

    CH_UNORDERED_NOID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));
  }
  //</editor-fold>
}
