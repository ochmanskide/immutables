package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public interface ImmutableCollectors
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
  @Contract(value = " -> new", pure = true)
  static <T> Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull Set<@NotNull T>> toMutableSet()
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull Set<@NotNull T>>builder()
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
        .characteristics(CH_UNORDERED_ID)
        .build();
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
  static <T> Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ImmutableSet<@NotNull T>> toSet()
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ImmutableSet<@NotNull T>>builder()
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
        .finisher(ImmutableSet::of)
        .characteristics(CH_UNORDERED_NOID)
        .build();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  static <T extends @NotNull Enum<@NotNull T>> Collector<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ImmutableEnumSet<@NotNull T>> toEnumSet()
  {
    return CollectorImpl.<@NotNull T, @NotNull HashSet<@NotNull T>, @NotNull ImmutableEnumSet<@NotNull T>>builder()
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
        .finisher(ImmutableEnumSet::of)
        .characteristics(CH_UNORDERED_NOID)
        .build();
  }

  @NotNull
  @Contract(value = " -> new", pure = true)
  static <T> Collector<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull ImmutableList<@NotNull T>> toList()
  {
    return CollectorImpl.<@NotNull T, @NotNull ArrayList<@NotNull T>, @NotNull ImmutableList<@NotNull T>>builder()
        .supplier(ArrayList::new)
        .accumulator(List::add)
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
        .finisher(ImmutableList::of)
        .characteristics(CH_UNORDERED_NOID)
        .build();
  }

  @NotNull
  @Contract(pure = true)
  @SuppressWarnings({ "unchecked", "rawtypes" })
  static <T> IntFunction<@NotNull T @NotNull []> tGenerator()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  @Value
  @RequiredArgsConstructor
  @Builder
  class CollectorImpl<T, A, R>
      implements Collector<@NotNull T, @NotNull A, @NotNull R>
  {
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

  }

  @NotNull
  @Contract(pure = true)
  @SuppressWarnings("unchecked")
  private static <I, R> Function<@NotNull I, @NotNull R> castingIdentity()
  {
    return i -> (R)i;
  }

}