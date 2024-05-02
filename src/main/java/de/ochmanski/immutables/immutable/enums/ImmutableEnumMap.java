package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.IMap;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.fluent.Fluent.Dummy;
import de.ochmanski.immutables.immutable.ImmutableCollectors;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableMap;
import de.ochmanski.immutables.immutable.ImmutableSet;
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
public class ImmutableEnumMap<K extends @NotNull Enum<@NotNull K>, V> implements IMap<@NotNull K, @NotNull V> {

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  ImmutableMap<@NotNull K, @NotNull V> map = (ImmutableMap) ImmutableMap.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = Fluent.defaultKey();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = Fluent.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static ImmutableEnumMap<? extends @NotNull Fluent<?>, ? extends @NotNull Fluent<?>> empty() {
    return EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final ImmutableEnumMap<? extends @NotNull Fluent<?>, ? extends @NotNull Fluent<?>> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static ImmutableEnumMap<? extends @NotNull Fluent<?>, ? extends @NotNull Fluent<?>> createConstant() {
    return ImmutableEnumMap.<@NotNull Dummy, @NotNull Fluent<?>>builder().build();
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
   *   final ImmutableEnumMap<Dummy> actual = ImmutableEnumMap.noneOf(Dummy[]::new);
   *   final ImmutableEnumMap<String> actual = ImmutableEnumMap.noneOf(String[]::new);
   *   final ImmutableEnumMap<Integer> actual = ImmutableEnumMap.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @SuppressWarnings(Constants.Warning.UNUSED)
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ImmutableEnumMap.noneOf(String[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ImmutableEnumMap<Dummy> actual = ImmutableEnumMap.noneOf(Dummy[]::new);
   *   final ImmutableEnumMap<String> actual = ImmutableEnumMap.noneOf(String[]::new);
   *   final ImmutableEnumMap<Integer> actual = ImmutableEnumMap.noneOf(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Enum<@NotNull K>, V> ImmutableEnumMap<@NotNull K, @NotNull V>
  noneOf(
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
    //noinspection DuplicatedCode
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
    //noinspection DuplicatedCode
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
    //noinspection DuplicatedCode
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
      @SuppressWarnings(Constants.Warning.MISMATCHED_QUERY_AND_UPDATE_OF_COLLECTION)
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
  @Contract(pure = true)
  public ImmutableMap<@NotNull K, @NotNull V> getMap() {
    return map;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return getMap().entrySet();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public EnumMap<@NotNull K, @NotNull V> unwrap() {
    if (map.isEmpty()) {
      return new EnumMap<@NotNull K, @NotNull V>(getComponentTypeFromConstructor(getKey()));
    }
    return new EnumMap<@NotNull K, @NotNull V>(map.unwrap());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<@NotNull K> keySet() {
    return ImmutableEnumSet.of(unwrap().keySet(), getKey());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values() {
    return ImmutableList.of(unwrap().values(), getValue());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">


  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>
}
