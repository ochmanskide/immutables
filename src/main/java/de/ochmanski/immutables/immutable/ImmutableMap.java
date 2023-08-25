package de.ochmanski.immutables.immutable;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableMap<K, V>// extends Immutable<@NotNull K>, V extends Immutable<@NotNull V>> implements ImmutableMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, V> map = Map.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = defaultConstructor();

  @NonNull
  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = defaultConstructor();

  @NotNull
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return Optional.ofNullable(map.get(key));
  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(pure = true)
  //  public ImmutableSet<@NotNull K> findByValue(@NotNull final V value)
  //  {
  //    return stream()
  //        .filter(p -> p.getValue().isEqualTo(value))
  //        .map(Entry::getKey)
  //        .collect(ImmutableCollectors.toSet());
  //  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(pure = true)
  //  public Stream<@NotNull Entry<@NotNull K, @NotNull V>> stream()
  //  {
  //    return entrySet().stream();
  //  }

  /**
   * Returns the number of elements in this map.
   *
   * @return the number of elements in this map
   */
  public int size()
  {
    return map.size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  public boolean isEmpty()
  {
    return map.isEmpty();
  }

  /**
   * Returns {@code true} if this map contains the specified element. More formally, returns {@code true} if and only if
   * this map contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this map is to be tested
   * @return {@code true} if this map contains the specified element
   */
  public boolean containsKey(@NotNull final K o)
  {
    return map.containsKey(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayMap} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayMap} instance
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableSet<@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  //  {
  //    return ImmutableSet.copyOfEntries(toMap().entrySet());
  //  }
  //
  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableSet<@NotNull K> keySet()
  //  {
  //    return ImmutableSet.copyOf(toMap().keySet());
  //  }
  //
  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableList<@NotNull V> values()
  //  {
  //    return ImmutableList.copyOf(toMap().values());
  //  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Map<@NotNull K, @NotNull V> toMap()
  {
    return Map.copyOf(map);
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true)
  public static class Entry<@NotNull K, @NotNull V>
  {

    @NonNull
    @NotNull("Given keyType cannot be null.")
    @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
    K key;

    @NonNull
    @NotNull("Given valueType cannot be null.")
    @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
    V value;

    /**
     * Returns a comparator that compares {@link Entry} in natural order on key.
     *
     * <p>The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with a null key.
     *
     * @param <K> the {@link Comparable} type of then map keys
     * @param <V> the type of the map values
     * @return a comparator that compares {@link Entry} in natural order on key.
     * @see Comparable
     * @since 1.8
     */
    static <K extends Comparable<? super K>, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByKey()
    {
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
          (c1, c2) -> c1.getKey().compareTo(c2.getKey());
    }

    /**
     * Returns a comparator that compares {@link Entry} in natural order on value.
     *
     * <p>The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with null values.
     *
     * @param <K> the type of the map keys
     * @param <V> the {@link Comparable} type of the map values
     * @return a comparator that compares {@link Entry} in natural order on value.
     * @see Comparable
     * @since 1.8
     */
    static <K, V extends Comparable<? super V>> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByValue()
    {
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
          (c1, c2) -> c1.getValue().compareTo(c2.getValue());
    }

    /**
     * Returns a comparator that compares {@link Entry} by key using the given {@link Comparator}.
     *
     * <p>The returned comparator is serializable if the specified comparator
     * is also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the key {@link Comparator}
     * @return a comparator that compares {@link Entry} by the key.
     * @since 1.8
     */
    static <@NotNull K, @NotNull V> Comparator<Entry<@NotNull K, @NotNull V>> comparingByKey(
        Comparator<? super K> cmp)
    {
      Objects.requireNonNull(cmp);
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
          (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
    }

    /**
     * Returns a comparator that compares {@link Entry} by value using the given {@link Comparator}.
     *
     * <p>The returned comparator is serializable if the specified comparator
     * is also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the value {@link Comparator}
     * @return a comparator that compares {@link Entry} by the value.
     * @since 1.8
     */
    public static <@NotNull K, @NotNull V> Comparator<Entry<@NotNull K, @NotNull V>> comparingByValue(
        Comparator<? super V> cmp)
    {
      Objects.requireNonNull(cmp);
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
          (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

  }

}
