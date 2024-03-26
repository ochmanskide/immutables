package de.ochmanski.immutables.collection;

import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableMap;
import lombok.*;
import org.jetbrains.annotations.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface IMap<K extends @NotNull Comparable<? super @NotNull K>, V> {

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link ImmutableMap#noneOf(IntFunction, IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IMap<Dummy> actual = IMap.noneOf(Dummy[]::new);
   *   final IMap<String> actual = IMap.noneOf(String[]::new);
   *   final IMap<Integer> actual = IMap.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: IMap.noneOf(String[]::new)");
  }

  @NotNull
  @Contract(pure = true)
  default Optional<@Nullable V> get(@NotNull final K key) {
    return getMap().get(key);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  ISet<@NotNull K> findByValue(@NotNull final V value);

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  default Stream<IMap.@NotNull Entry<@NotNull K, @NotNull V>> stream() {
    return getMap().stream();
  }

  default int size() {
    return getMap().size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  default boolean isEmpty() {
    return getMap().isEmpty();
  }

  /**
   * Returns {@code true} if this map contains the specified element. More formally, returns {@code true} if and only if
   * this map contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this map is to be tested
   * @return {@code true} if this map contains the specified element
   */
  default boolean containsKey(@NotNull final K o) {
    return getMap().containsKey(o);
  }

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
  default Map<@NotNull K, @NotNull V> unwrap() {
    return getMap().unwrap();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default ISet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return getMap().entrySet();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default ISet<@NotNull K> keySet() {
    return getMap().keySet();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  default IList<@NotNull V> values() {
    return getMap().values();
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
  class Entry<K extends @NotNull Comparable<? super @NotNull K>, V> implements Equalable<@NotNull Entry<@NotNull K, @NotNull V>>, Comparable<@NotNull Entry<@NotNull K, @NotNull V>> {

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
    @NotNull
    @Contract(pure = true)
    static <K extends @NotNull Comparable<? super @NotNull K>, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByKey() {
      return (@NotNull Comparator<@NotNull Entry<@NotNull K, @NotNull V>> & @NotNull Serializable)
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
    @NotNull
    @Contract(pure = true)
    static <K extends @NotNull Comparable<? super @NotNull K>, V extends @NotNull Comparable<? super @NotNull V>> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByValue() {
      return (@NotNull Comparator<@NotNull Entry<@NotNull K, @NotNull V>> & @NotNull Serializable)
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
    @NotNull
    @Contract(pure = true)
    static <K extends @NotNull Comparable<? super @NotNull K>, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByKey(
      @NotNull final Comparator<? super @NotNull K> cmp) {
      Objects.requireNonNull(cmp);
      return (@NotNull Comparator<@NotNull Entry<@NotNull K, @NotNull V>> & @NotNull Serializable)
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
    @NotNull
    @Contract(pure = true)
    public static <K extends @NotNull Comparable<? super @NotNull K>, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByValue(
      @NotNull final Comparator<? super @NotNull V> cmp) {
      Objects.requireNonNull(cmp);
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
        (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <K extends @NotNull Comparable<? super @NotNull K>, V> IMap.@Unmodifiable @NotNull Entry<@NotNull K, @NotNull V> of(
      @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> entry) {
      return IMap.Entry.<@NotNull K, @NotNull V>builder().key(entry.getKey()).value(entry.getValue()).build();
    }

    @NotNull
    @Contract(value = "-> new", pure = true)
    public IMap.@Unmodifiable @NotNull Entry<@NotNull K, @NotNull V> deepClone() {
      return toBuilder().build();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    @Contract(pure = true)
    public int compareTo(@NotNull final Entry<@NotNull K, @NotNull V> o) {
      return orderAsc(this, o);
    }

    private static <K extends @NotNull Comparable<? super @NotNull K>, V> int orderAsc(@Nullable final Entry<@NotNull K, @NotNull V> a, @Nullable final Entry<@NotNull K, @NotNull V> b) {
      if (a == b) {
        return 0;
      }
      return a != null ? b != null ? comparingByKey().compare((Entry) a.getKey(), (Entry) b.getKey()) : -1 : 1;
    }

  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  IMap<@NotNull K, @NotNull V> getMap();
}
