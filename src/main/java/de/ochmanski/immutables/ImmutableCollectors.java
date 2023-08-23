package de.ochmanski.immutables;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
  @NotNull
  @Contract(" -> new")
  static <T> Collector<@NotNull T, ?, @NotNull Set<@NotNull T>> toMutableSet()
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
  @NotNull
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings("unchecked")
  static <T> Collector<@NotNull T, ?, @NotNull Set<@NotNull T>> toSet()
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

  @Value
  @RequiredArgsConstructor
  class CollectorImpl<T, A, R> implements Collector<@NotNull T, @NotNull A, @NotNull R>
  {
    @NotNull
    Supplier<@NotNull A> supplier;

    @NotNull
    BiConsumer<@NotNull A, @NotNull T> accumulator;

    @NotNull
    BinaryOperator<@NotNull A> combiner;

    @NotNull
    Function<@NotNull A, @NotNull R> finisher;

    @NotNull
    Set<@NotNull Characteristics> characteristics;

    CollectorImpl(@NotNull final Supplier<@NotNull A> supplier,
        @NotNull final BiConsumer<@NotNull A, @NotNull T> accumulator,
        @NotNull final BinaryOperator<@NotNull A> combiner,
        @NotNull final Set<@NotNull Characteristics> characteristics)
    {
      this(supplier, accumulator, combiner, castingIdentity(), characteristics);
    }

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

  }

  @NotNull
  @Contract(pure = true)
  @SuppressWarnings("unchecked")
  private static <I, R> Function<@NotNull I, @NotNull R> castingIdentity()
  {
    return i -> (R)i;
  }

}
