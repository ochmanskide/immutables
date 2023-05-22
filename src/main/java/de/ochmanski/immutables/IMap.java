package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

interface IMap<K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction,IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IMap<Dummy> actual = IMap.ofGenerator(Dummy[]::new);
   *   final IMap<String> actual = IMap.ofGenerator(String[]::new);
   *   final IMap<Integer> actual = IMap.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: IMap.ofGenerator(String[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IMap<Dummy> actual = IMap.ofGenerator(Dummy[]::new);
   *   final IMap<String> actual = IMap.ofGenerator(String[]::new);
   *   final IMap<Integer> actual = IMap.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V>
  ofGenerator(@NotNull final IntFunction<@NotNull K[]> key, @NotNull final IntFunction<@NotNull V[]> value)
  {
    return ImmutableMap.<K, V>builder().key(key).value(value).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1)
  {
    return ImmutableMap.<K, V>builder().map(Map.of(k1, v1)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2)
  {
    return ImmutableMap.<K, V>builder().map(Map.of(k1, v1, k2, v2)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3)
  {
    return ImmutableMap.<K, V>builder().map(Map.of(k1, v1, k2, v2, k3, v3)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3,
      @NotNull final K k4, @NotNull final V v4)
  {
    return ImmutableMap.<K, V>builder().map(Map.of(k1, v1, k2, v2, k3, v3, k4, v4)).build();
  }

  int size();

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  boolean isEmpty();

  /**
   * Returns {@code true} if this map contains the specified element. More formally, returns {@code true} if and only if
   * this map contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this map is to be tested
   * @return {@code true} if this map contains the specified element
   */
  boolean containsKey(@NotNull final K o);

  /**
   * Returns a deep copy of this {@code ArrayMap} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayMap} instance
   */
  @NotNull
  @Contract(value = " -> new", pure = true)
  IMap<@NotNull K, @NotNull V> deepClone();

  @NotNull
  @Contract(value = " -> new", pure = true)
  Map<@NotNull K, @NotNull V> toMap();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull K> keySet();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  IList<@NotNull V> value();

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true)
  class Entry<@NotNull K, @NotNull V> implements Equalable<@NotNull Entry<@NotNull K, @NotNull V>>
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
     * Returns a comparator that compares {@link IMap.Entry} in natural order on key.
     *
     * <p>The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with a null key.
     *
     * @param <K> the {@link Comparable} type of then map keys
     * @param <V> the type of the map values
     * @return a comparator that compares {@link IMap.Entry} in natural order on key.
     * @see Comparable
     * @since 1.8
     */
    static <K extends Comparable<? super K>, V> Comparator<IMap.Entry<K, V>> comparingByKey()
    {
      return (Comparator<IMap.Entry<K, V>> & Serializable)
          (c1, c2) -> c1.getKey().compareTo(c2.getKey());
    }

    /**
     * Returns a comparator that compares {@link IMap.Entry} in natural order on value.
     *
     * <p>The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with null values.
     *
     * @param <K> the type of the map keys
     * @param <V> the {@link Comparable} type of the map values
     * @return a comparator that compares {@link IMap.Entry} in natural order on value.
     * @see Comparable
     * @since 1.8
     */
    static <K, V extends Comparable<? super V>> Comparator<IMap.Entry<K, V>> comparingByValue()
    {
      return (Comparator<IMap.Entry<K, V>> & Serializable)
          (c1, c2) -> c1.getValue().compareTo(c2.getValue());
    }

    /**
     * Returns a comparator that compares {@link IMap.Entry} by key using the given {@link Comparator}.
     *
     * <p>The returned comparator is serializable if the specified comparator
     * is also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the key {@link Comparator}
     * @return a comparator that compares {@link IMap.Entry} by the key.
     * @since 1.8
     */
    static <K, V> Comparator<IMap.Entry<K, V>> comparingByKey(Comparator<? super K> cmp)
    {
      Objects.requireNonNull(cmp);
      return (Comparator<IMap.Entry<K, V>> & Serializable)
          (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
    }

    /**
     * Returns a comparator that compares {@link IMap.Entry} by value using the given {@link Comparator}.
     *
     * <p>The returned comparator is serializable if the specified comparator
     * is also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the value {@link Comparator}
     * @return a comparator that compares {@link IMap.Entry} by the value.
     * @since 1.8
     */
    public static <K, V> Comparator<IMap.Entry<K, V>> comparingByValue(Comparator<? super V> cmp)
    {
      Objects.requireNonNull(cmp);
      return (Comparator<IMap.Entry<K, V>> & Serializable)
          (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

  }

}
