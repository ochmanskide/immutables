package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.IMap;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSet;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntFunction;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class FluentEnumMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  implements IMap<@NotNull K, @NotNull V> {

  @UnmodifiableView
  @NotNull("FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @Builder.Default
  ImmutableEnumMap<@NotNull K, @NotNull V> map = (ImmutableEnumMap) ImmutableEnumMap.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = defaultKey();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings({Constants.Warning.UNCHECKED, Constants.Warning.RAWTYPES})
  private static <S> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) EMPTY_KEY;
  }

  @NotNull
  @Unmodifiable
  private static final IntFunction<@NotNull Dummy @NotNull []> EMPTY_KEY = createConstantKey();

  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  private static IntFunction<@NotNull Dummy @NotNull []> createConstantKey() {
    return Dummy @NotNull []::new;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static FluentEnumMap<? extends @NotNull Fluent<?>, ? extends @NotNull Fluent<?>> empty() {
    return EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final FluentEnumMap<? extends @NotNull Fluent<?>, ? extends @NotNull Fluent<?>> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static FluentEnumMap<@NotNull Dummy, ? extends @NotNull Fluent<?>> createConstant() {
    return FluentEnumMap.<@NotNull Dummy, @NotNull Fluent<?>>builder().key(defaultKey()).build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #noneOf(IntFunction, IntFunction)} instead.
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

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IMap<Dummy> actual = IMap.noneOf(Dummy[]::new);
   *   final IMap<String> actual = IMap.noneOf(String[]::new);
   *   final IMap<Integer> actual = IMap.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V>
  noneOf(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>noneOf(key,
      value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    Class<@NotNull K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    Class<@NotNull K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    Class<@NotNull K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _ , _, _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final K k1, @NotNull final V v1,
    @NotNull final K k2, @NotNull final V v2,
    @NotNull final K k3, @NotNull final V v3,
    @NotNull final K k4, @NotNull final V v4,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    Class<@NotNull K> keyType = getComponentTypeFromConstructor(key);
    final Map<@NotNull K, @NotNull V> map = new EnumMap<@NotNull K, @NotNull V>(keyType);
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    map.put(k4, v4);
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    if (map.isEmpty()) {
      return FluentEnumMap.noneOf(key, value);
    }
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _-> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> ofEnumMap(
    @NotNull final ImmutableEnumMap<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    return FluentEnumMap.<@NotNull K, @NotNull V>builder()
      .key(key)
      .map(map)
      .value(value)
      .build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IMap interface">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public FluentEnumSet<@NotNull K> findByValue(@NotNull final V value) {
    @NotNull final IntFunction<@NotNull K @NotNull []> k = getKey();
    return stream()
      .filter(p -> Equalable.<@NotNull V>areTheSame(p.getValue(), value))
      .map(Entry::getKey)
      .collect(FluentCollectors.toSet(k));
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
  public FluentEnumMap<@NotNull K, @NotNull V> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableEnumMap<@NotNull K, @NotNull V> getMap() {
    return map;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return map.entrySet();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumSet<@NotNull K> keySet() {
    return FluentEnumSet.<@NotNull K>of(map.keySet());
  }


  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values() {
    return map.values();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumMap<@NotNull K, @NotNull V> unwrap() {
    return map.unwrap();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. converters to family classes">

  //</editor-fold>
}
