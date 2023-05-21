package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

interface IMap<K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
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
  ImmutableMap<@NotNull K, @NotNull V> deepClone();

  @NotNull
  @Contract(value = " -> new", pure = true)
  Map<@NotNull K, @NotNull V> toMap();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  Set<Map.Entry<@NotNull K, @NotNull V>> entrySet();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull K> keySet();

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  IList<@NotNull V> value();

}
