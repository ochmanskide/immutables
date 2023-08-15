package de.ochmanski.immutables;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public interface ImmutableCollectors
{

  Set<Collector.Characteristics> CH_CONCURRENT_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
      Collector.Characteristics.UNORDERED,
      Collector.Characteristics.IDENTITY_FINISH));
  Set<Collector.Characteristics> CH_CONCURRENT_NOID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
      Collector.Characteristics.UNORDERED));
  Set<Collector.Characteristics> CH_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
  Set<Collector.Characteristics> CH_UNORDERED_ID
      = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
      Collector.Characteristics.IDENTITY_FINISH));
  Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
  Set<Collector.Characteristics> CH_UNORDERED_NOID
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
  static <T> Collector<T, ?, Set<T>> toMutableSet()
  {
    return new CollectorImpl<>(HashSet::new, Set::add,
        (left, right) ->
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
        },
        CH_UNORDERED_ID);
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
  @SuppressWarnings("unchecked")
  static <T> Collector<T, ?, Set<T>> toSet()
  {
    return new ImmutableCollectors.CollectorImpl<>(HashSet::new, Set::add,
        (left, right) ->
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
        },
        set -> (Set<T>)Set.of(set.toArray()),
        CH_UNORDERED_NOID);
  }

  class CollectorImpl<T, A, R> implements Collector<T, A, R>
  {
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Characteristics> characteristics;

    CollectorImpl(Supplier<A> supplier,
        BiConsumer<A, T> accumulator,
        BinaryOperator<A> combiner,
        Function<A, R> finisher,
        Set<Characteristics> characteristics)
    {
      this.supplier = supplier;
      this.accumulator = accumulator;
      this.combiner = combiner;
      this.finisher = finisher;
      this.characteristics = characteristics;
    }

    CollectorImpl(Supplier<A> supplier,
        BiConsumer<A, T> accumulator,
        BinaryOperator<A> combiner,
        Set<Characteristics> characteristics)
    {
      this(supplier, accumulator, combiner, castingIdentity(), characteristics);
    }

    @Override
    public BiConsumer<A, T> accumulator()
    {
      return accumulator;
    }

    @Override
    public Supplier<A> supplier()
    {
      return supplier;
    }

    @Override
    public BinaryOperator<A> combiner()
    {
      return combiner;
    }

    @Override
    public Function<A, R> finisher()
    {
      return finisher;
    }

    @Override
    public Set<Characteristics> characteristics()
    {
      return characteristics;
    }

  }

  @SuppressWarnings("unchecked")
  private static <I, R> Function<I, R> castingIdentity()
  {
    return i -> (R)i;
  }

}
