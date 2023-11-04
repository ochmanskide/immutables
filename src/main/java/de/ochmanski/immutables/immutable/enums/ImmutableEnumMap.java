package de.ochmanski.immutables.immutable.enums;


import de.ochmanski.immutables.ICollection;
import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableCollectors;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableMap;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntFunction;

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

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> empty() {
    return EMPTY;
  }

  private static final ImmutableEnumMap EMPTY = ImmutableEnumMap.builder().build();

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = " -> new", pure = true)
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) Enum @NotNull []::new;
  }

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = " -> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultValue() {
    return (IntFunction) Object @NotNull []::new;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

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
  static void of() {
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
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V>
  ofGenerator(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(new EnumMap<@NotNull K, @NotNull V>(keyType), key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ , _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final Class<K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>of(map, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ , _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
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
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
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
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final K k4, @NotNull final V v4,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
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
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    if (map.isEmpty()) {
      final Class<K> keyType = getComponentTypeFromConstructor(key);
      final EnumMap<@NotNull K, @NotNull V> enumMap = new EnumMap<>(keyType);
      return ImmutableEnumMap.ofEnumMap(enumMap, key, value);
    }
    final EnumMap<@NotNull K, @NotNull V> enumMap = new EnumMap<>(map);
    return ImmutableEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _-> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V> ofEnumMap(
    @NotNull final EnumMap<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final ImmutableMap<@NotNull K, @NotNull V> immutableMap = ImmutableMap.of(map, key, value);
    return ImmutableEnumMap.<@NotNull K, @NotNull V>builder()
      .key(key)
      .value(value)
      .map(immutableMap)
      .build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IMap interface">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableEnumSet<@NotNull K> findByValue(@NotNull final V value) {
    return stream()
      .filter(p -> Equalable.<@NotNull V>areTheSame(p.getValue(), value))
      .map(Entry::getKey)
      .collect(ImmutableCollectors.toEnumSet(getKey()));
  }

  /**
   * Returns a deep copy of this {@code ArrayMap} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayMap} instance
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumMap<@NotNull K, @NotNull V> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return ImmutableSet.<@NotNull K, @NotNull V>copyOfEntries(toMap().entrySet(), Entry[]::new);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public Map<@NotNull K, @NotNull V> unwrap() {
    return map.unwrap();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<@NotNull K> keySet() {
    return ImmutableEnumSet.of(toMap().keySet(), getKey());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values() {
    return ImmutableList.of(toMap().values(), getValue());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumMap<K, @NotNull V> toMap() {
    if (map.isEmpty()) {
      return new EnumMap<@NotNull K, @NotNull V>(getComponentTypeFromConstructor(getKey()));
    }
    return new EnumMap<@NotNull K, @NotNull V>(map.unwrap());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends Enum<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>
}
