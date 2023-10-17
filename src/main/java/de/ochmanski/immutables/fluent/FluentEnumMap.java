package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.IList;
import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.ISet;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class FluentEnumMap<K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>>
    implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, @NonNull @NotNull V> map = Map.of();

  @NonNull
  @NotNull("FluentEnumMap::Builder 002: Given FluentEnumMap::generator cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 002: Given FluentEnumMap::generator cannot be null.")
  IntFunction<@NonNull @NotNull K @NonNull @NotNull []> generator;

  @NonNull
  @NotNull("FluentEnumMap::Builder 003: Given FluentEnumMap::keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 003: Given FluentEnumMap::keyType cannot be null.")
  Class<@NonNull @NotNull K> keyType;

  @NonNull
  @NotNull("FluentEnumMap::Builder 004: Given FluentEnumMap::valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 004: Given FluentEnumMap::valueType cannot be null.")
  Class<@NonNull @NotNull V> valueType;

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction, Class, Class)} instead.
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
  public static void of()
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
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V>
  ofGenerator(
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
      @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(new EnumMap<>(keyType))
        .valueType(valueType)
        .build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ , _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor, @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(map)
        .valueType(valueType)
        .build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor, @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(map)
        .valueType(valueType)
        .build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor, @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(map)
        .valueType(valueType)
        .build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3,
      @NotNull final K k4, @NotNull final V v4,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor, @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    final Map<@NonNull @NotNull K, @NonNull @NotNull V> map = new EnumMap<>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    map.put(k4, v4);
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(map)
        .valueType(valueType)
        .build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
      @NotNull final Map<@NonNull @NotNull K, @NonNull @NotNull V> map,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
      @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    final EnumMap<@NonNull @NotNull K, @NonNull @NotNull V> enumMap = new EnumMap<>(map);
    return FluentEnumMap.ofEnumMap(enumMap, constructor, keyType, valueType);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _-> new", pure = true)
  public static <K extends Enum<@NotNull K> & Fluent<@NotNull K>, V extends Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> ofEnumMap(
      @NotNull final EnumMap<@NonNull @NotNull K, @NonNull @NotNull V> map,
      @NotNull final IntFunction<@NotNull K @NotNull []> constructor,
      @NotNull final Class<@NotNull K> keyType,
      @NotNull final Class<@NotNull V> valueType)
  {
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
        .generator(constructor)
        .keyType(keyType)
        .map(map)
        .valueType(valueType)
        .build();
  }

  @Override
  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return Optional.ofNullable(map.get(key));
  }

  @Override
  @NotNull
  @UnmodifiableView
  public FluentEnumSet<@NotNull K> findByValue(@NotNull final V value)
  {
    return stream()
        .filter(p -> p.getValue().isEqualTo(value))
        .map(Entry::getKey)
        .collect(FluentCollectors.toSet(getGenerator()));
  }

  @Override
  @NotNull
  @UnmodifiableView
  public Stream<@NotNull Entry<@NotNull K, @NotNull V>> stream()
  {
    return entrySet().stream();
  }

  /**
   * Returns the number of elements in this map.
   *
   * @return the number of elements in this map
   */
  @Override
  public int size()
  {
    return map.size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  @Override
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
  @Override
  public boolean containsKey(@NotNull final K o)
  {
    return map.containsKey(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayMap} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayMap} instance
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ISet<@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  {
    return ISet.copyOfEntries(toMap().entrySet());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ISet<@NotNull K> keySet()
  {
    return ISet.copyOf(toMap().keySet(), getGenerator());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public IList<@NotNull V> values()
  {
    return IList.copyOf(toMap().values());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumMap<@NotNull K, @NotNull V> toMap()
  {
    return new EnumMap<>(map);
  }

}