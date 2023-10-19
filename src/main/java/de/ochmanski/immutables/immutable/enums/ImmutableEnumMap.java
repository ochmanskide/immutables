package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.IMap;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;
import java.util.*;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableEnumMap<K extends @NotNull Enum<@NotNull K>, V> implements IMap<@NotNull K, V>
{

  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, @NonNull @NotNull V> map = Map.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<? extends @NonNull @NotNull K @NonNull @NotNull []> generator = defaultConstructor();

  @NonNull
  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  Class<? extends @NonNull @NotNull V> valueType;

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(value = " -> new", pure = true)
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction, Class)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ImmutableEnumMap<Dummy> actual = ImmutableEnumMap.ofGenerator(Dummy[]::new);
   *   final ImmutableEnumMap<String> actual = ImmutableEnumMap.ofGenerator(String[]::new);
   *   final ImmutableEnumMap<Integer> actual = ImmutableEnumMap.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  public static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: ImmutableEnumMap.ofGenerator(String[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ImmutableEnumMap<Dummy> actual = ImmutableEnumMap.ofGenerator(Dummy[]::new);
   *   final ImmutableEnumMap<String> actual = ImmutableEnumMap.ofGenerator(String[]::new);
   *   final ImmutableEnumMap<Integer> actual = ImmutableEnumMap.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V>
  ofGenerator(
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
      @NotNull final Class<@NotNull V> valueType)
  {
    final Class<? extends K> keyType = getComponentTypeFromConstructor(constructor);
    final EnumMap<K, V> map = new EnumMap<>(keyType);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    final Class<? extends K> keyType = getComponentTypeFromConstructor(constructor);
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    final Class<? extends K> keyType = getComponentTypeFromConstructor(constructor);
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    final Class<? extends K> keyType = getComponentTypeFromConstructor(constructor);
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final K k4, @NotNull final V v4,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    final Class<? extends K> keyType = getComponentTypeFromConstructor(constructor);
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    map.put(k4, v4);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NonNull @NotNull K, @NonNull @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    final EnumMap<@NonNull @NotNull K, @NonNull @NotNull V> enumMap = new EnumMap<>(map);
    return ImmutableEnumMap.ofEnumMap(enumMap, constructor, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _-> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> ofEnumMap(
    @NotNull final EnumMap<@NonNull @NotNull K, @NonNull @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
    @NotNull final Class<@NotNull V> valueType)
  {
    return ImmutableEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
      .generator(constructor)
      .valueType(valueType)
      .map(map)
      .build();
  }

  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return Optional.ofNullable(map.get(key));
  }

  //    @NotNull
  //    @UnmodifiableView
  //    public ImmutableEnumSet<@NotNull K> findByValue(@NotNull final V value)
  //    {
  //      return stream()
  //          .filter(p -> p.getValue().isEqualTo(value))
  //          .map(Entry::getKey)
  //          .collect(FluentCollectors.toSet());
  //    }

  //  @NotNull
  //  @UnmodifiableView
  //  public Stream<ImmutableEnumMap.@NotNull Entry<@NotNull K, @NotNull V>> stream()
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
  public ImmutableEnumMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableEnumSet<ImmutableEnumMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  //  {
  //    return ImmutableEnumSet.copyOfEntries(toMap().entrySet());
  //  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableEnumSet<@NotNull K> keySet()
  //  {
  //    return ImmutableEnumSet.copyOf(toMap().keySet());
  //  }

  //  @NotNull
  //  @UnmodifiableView
  //  @Contract(value = " -> new", pure = true)
  //  public ImmutableEnumList<@NotNull V> values()
  //  {
  //    return ImmutableEnumList.copyOf(toMap().values());
  //  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumMap<K, @NotNull V> toMap()
  {
    return new EnumMap<K, V>(map);
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true)
  public static class Entry<K, V>
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
    static <K extends Comparable<? super @NotNull K>, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByKey()
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
    static <K, V extends Comparable<? super @NotNull V>> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByValue()
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
    static <K, V> Comparator<@NotNull Entry<@NotNull K, @NotNull V>> comparingByKey(
      @NotNull final Comparator<? super @NotNull K> cmp)
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
    public static <K, V> Comparator<Entry<@NotNull K, @NotNull V>> comparingByValue(
      @NotNull final
      Comparator<? super @NotNull V> cmp)
    {
      Objects.requireNonNull(cmp);
      return (Comparator<Entry<@NotNull K, @NotNull V>> & Serializable)
        (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
    }

  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  private static <S extends Enum<@NotNull S>> Class<? extends @NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<? extends @NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

}
