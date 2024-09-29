package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.equalable.Equalable.EqualableString;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.Supplier;
import java.util.stream.Collector;

import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

public interface EqualableCollectors
{

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_CONCURRENT_ID
    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
    Collector.Characteristics.UNORDERED,
    Collector.Characteristics.IDENTITY_FINISH));

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_CONCURRENT_NOID
    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
    Collector.Characteristics.UNORDERED));

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_ID
    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_UNORDERED_ID
    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
    Collector.Characteristics.IDENTITY_FINISH));

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_NOID = Collections.emptySet();

  @NotNull
  Set<Collector.@NotNull Characteristics> CH_UNORDERED_NOID
    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

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
  static Collector<@NotNull EqualableString, @NotNull HashSet<@NotNull EqualableString>, @NotNull EqualableSortedSet<@NotNull EqualableString>> toSortedSetOfStrings() {
    return toSortedSet(EqualableString[]::new);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> toSortedSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor
  ) {
    return toSortedSet(constructor, HashSet::add);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " _,_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>> toSortedSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor,
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator)
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSortedSet<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(EqualableCollectors::comparableSetCombiner)
      .finisher(set -> EqualableSortedSet.of(set, constructor))
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

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
  @Contract(value = " _ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>> toSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor) {
    return toSet(constructor, HashSet::add);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = " _,_ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>> toSet(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor,
    @NotNull final BiConsumer<@NotNull HashSet<@NotNull T>, @NotNull T> accumulator)
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull EqualableSet<@NotNull T>>builder()
      .supplier(HashSet::new)
      .accumulator(accumulator)
      .combiner(EqualableCollectors::hashSetCombiner)
      .finisher(set -> EqualableSet.of(set, constructor))
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  private static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  HashSet<@NotNull T> comparableSetCombiner(
    @NotNull final HashSet<@NotNull T> left,
    @NotNull final HashSet<@NotNull T> right) {
    if (left.size() < right.size()) {
      right.addAll(left);
      return right;
    } else {
      left.addAll(right);
      return left;
    }
  }

  @NotNull
  private static <T extends @NotNull Equalable<@NotNull T>>
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

  @NotNull
  @Unmodifiable
  @Contract(value = " _ -> new", pure = true)
  static <T extends @NotNull Comparable<@NotNull T> & @NotNull Equalable<@NotNull T>>
  Collector<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>> toList(
    @NotNull final IntFunction<@NotNull T @NotNull []> constructor
  )
  {
    return CollectorImpl.<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull EqualableList<@NotNull T>>builder()
      .supplier(ArrayList::new)
      .accumulator(List::add)
      .combiner(EqualableCollectors::arrayListCombiner)
      .finisher(list -> EqualableList.of(list, constructor))
      .characteristics(CH_UNORDERED_NOID)
      .build();
  }

  @NotNull
  private static <T extends @NotNull Equalable<@NotNull T>>
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

  static <T extends @NotNull Equalable<@NotNull T>>
  void defaultAction(@NotNull final Set<@NotNull T> ts, @NotNull final T e) {
    takeFirst(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void takeFirst(@NotNull final Set<@NotNull T> ts, @NotNull final T e) {
    ts.add(e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void takeSecond(@NotNull final Set<@NotNull T> ts, @NotNull final T e) {
    replace(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void replace(@NotNull final Set<@NotNull T> ts, @NotNull final T e) {
    replaceDuplicates(ts, e);
  }

  static <T extends @NotNull Equalable<@NotNull T>>
  void replaceDuplicates(@NotNull final Set<@NotNull T> ts, @NotNull final T e) {
    if (ts.add(e)) {
      return;
    }
    ts.remove(e);
    ts.add(e);
  }

  @Value
  @Unmodifiable
  @RequiredArgsConstructor
  @Builder
  class CollectorImpl<T, A, R>
    implements Collector<@NotNull T, @NotNull A, @NotNull R> {
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
    @Override
    public BiConsumer<@NotNull A, @NotNull T> accumulator()
    {
      return accumulator;
    }

    @NotNull
    @Override
    public Supplier<@NotNull A> supplier()
    {
      return supplier;
    }

    @NotNull
    @Override
    public BinaryOperator<@NotNull A> combiner()
    {
      return combiner;
    }

    @NotNull
    @Override
    public Function<@NotNull A, @NotNull R> finisher()
    {
      return finisher;
    }

    @NotNull
    @Override
    public Set<@NotNull Characteristics> characteristics()
    {
      return characteristics;
    }

    @NotNull
    @Contract(pure = true)
    @SuppressWarnings(UNCHECKED)
    private static <I, R> Function<@NotNull I, @NotNull R> castingIdentity()
    {
      return i -> (R)i;
    }
  }
}
