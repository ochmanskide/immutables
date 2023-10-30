package de.ochmanski.immutables.immutable.enums;


import de.ochmanski.immutables.ICollection;
import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableCollectors;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableMap;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableEnumMap<K extends @NotNull Enum<@NotNull K>, V> implements IMap<@NotNull K, @NotNull V>
{

  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  ImmutableMap<@NonNull @NotNull K, @NonNull @NotNull V> map = ImmutableMap.empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull K @NonNull @NotNull []> key = defaultKey();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull V @NonNull @NotNull []> value = defaultValue();

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(value = " -> new", pure = true)
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(value = " -> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultValue()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction, IntFunction)} instead.
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
  static void of()
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
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V>
  ofGenerator(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(new EnumMap<@NotNull K, @NotNull V>(keyType), key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final K k4, @NotNull final V v4,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    map.put(k4, v4);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    if(map.isEmpty())
    {
      return ImmutableEnumMap.ofGenerator(key, value);
    }
    final EnumMap<@NotNull K, @NotNull V> enumMap = new EnumMap<>(map);
    return ImmutableEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _-> new", pure = true)
  public static <K extends Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> ofEnumMap(
    @NotNull final EnumMap<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    final ImmutableMap<@NotNull K, @NotNull V> immutableMap = ImmutableMap.of(map, key, value);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>builder()
      .key(key)
      .value(value)
      .map(immutableMap)
      .build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> empty()
  {
    return EMPTY;
  }

  private static final ImmutableEnumMap EMPTY = ImmutableEnumMap.builder().build();

  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return map.get(key);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableEnumSet<@NotNull K> findByValue(@NotNull final V value)
  {
    return stream()
      .filter(p -> Equalable.<@NotNull V>areTheSame(p.getValue(), value))
      .map(Entry::getKey)
      .collect(ImmutableCollectors.toEnumSet(getKey()));
  }

  @NotNull
  @UnmodifiableView
  public Stream<IMap.@NotNull Entry<@NotNull K, @NotNull V>> stream()
  {
    return entrySet().stream();
  }

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

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  {
    return ImmutableSet.<@NotNull K, @NotNull V>copyOfEntries(toMap().entrySet(), Entry[]::new);
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public Map<@NotNull K, @NotNull V> unwrap() {
    return map.unwrap();
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<@NotNull K> keySet()
  {
    return ImmutableEnumSet.of(toMap().keySet(), getKey());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values()
  {
    return ImmutableList.of(toMap().values(), getValue());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumMap<K, @NotNull V> toMap()
  {
    if(map.isEmpty())
    {
      return new EnumMap<@NotNull K, @NotNull V>(getComponentTypeFromConstructor(getKey()));
    }
    return new EnumMap<@NotNull K, @NotNull V>(map.unwrap());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends Enum<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

}
