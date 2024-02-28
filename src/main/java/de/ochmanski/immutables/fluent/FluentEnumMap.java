package de.ochmanski.immutables.fluent;


import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.IMap;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSet;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumMap;
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
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class FluentEnumMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  implements IMap<@NotNull K, @NotNull V> {

  @UnmodifiableView
  @NonNull
  @NotNull("FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "FluentEnumMap::Builder 001: Given FluentEnumMap::map cannot be null.")
  @Builder.Default
  ImmutableEnumMap<@NonNull @NotNull K, @NonNull @NotNull V> map = ImmutableEnumMap.<@NotNull K, @NotNull V>empty();

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
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = " -> new", pure = true)
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Fluent<?> @NotNull []> DEFAULT_KEY = Fluent<?> @NotNull []::new;

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = " -> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultValue() {
    return (IntFunction) DEFAULT_VALUE;
  }

  @NotNull
  private static final IntFunction<@NotNull Equalable<?> @NotNull []> DEFAULT_VALUE = Equalable<?> @NotNull []::new;
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction, IntFunction)} instead.
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
  static void of() {
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
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V>
  ofGenerator(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofGenerator(key,
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
    @NotNull final Map<@NonNull @NotNull K, @NonNull @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    if (map.isEmpty()) {
      return FluentEnumMap.ofGenerator(key, value);
    }
    final ImmutableEnumMap<@NotNull K, @NotNull V> enumMap = ImmutableEnumMap.<@NotNull K, @NotNull V>ofEnumMap(
      new EnumMap<@NotNull K, @NotNull V>(map), key, value);
    return FluentEnumMap.ofEnumMap(enumMap, key, value);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _-> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<@NotNull V>> FluentEnumMap<@NotNull K, @NotNull V> ofEnumMap(
    @NotNull final ImmutableEnumMap<@NonNull @NotNull K, @NonNull @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    return FluentEnumMap.<@NonNull @NotNull K, @NonNull @NotNull V>builder()
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
