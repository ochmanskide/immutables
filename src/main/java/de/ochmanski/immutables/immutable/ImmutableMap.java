package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.IMap;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
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
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class ImmutableMap<K, V> implements IMap<@NotNull K, @NotNull V> {

  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NotNull K, @NotNull V> map = Map.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = defaultConstructor();

  @NonNull
  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = defaultConstructor();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings(Constants.Warning.UNCHECKED)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> empty() {
    return EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @SuppressWarnings(RAWTYPES)
  private static final ImmutableMap EMPTY = create();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static <K, V> ImmutableMap<@NotNull K, @NotNull V> create() {
    return ImmutableMap.<@NotNull K, @NotNull V>builder().build();
  }

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Object @NotNull []> DEFAULT_KEY = Object @NotNull []::new;
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_ -> new", pure = true)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> ofGenerator(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    return ImmutableMap.<@NotNull K, @NotNull V>of(Map.of(), key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    final Map<@NotNull K, @NotNull V> checkedMap = Collections.checkedMap(Map.copyOf(map), getComponentTypeFromConstructor(key), getComponentTypeFromConstructor(value));
    return ImmutableMap.<@NotNull K, @NotNull V>builder().map(checkedMap).key(key).value(value).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IMap interface">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return ImmutableSet.<@NotNull K, @NotNull V>copyOfEntries(unwrap().entrySet(), Entry @NotNull []::new);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableSet<@NotNull K> findByValue(@NotNull final V value) {
    return stream()
      .filter(p -> Equalable.<@NotNull V>areEqual(p.getValue(), value))
      .map(Entry::getKey)
      .collect(ImmutableCollectors.toSet(getKey()));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public Stream<IMap.@NotNull Entry<@NotNull K, @NotNull V>> stream() {
    return entrySet().stream();
  }

  /**
   * Returns the number of elements in this map.
   *
   * @return the number of elements in this map
   */
  public int size() {
    return map.size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  public boolean isEmpty() {
    return map.isEmpty();
  }

  /**
   * Returns {@code true} if this map contains the specified element. More formally, returns {@code true} if and only if
   * this map contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this map is to be tested
   * @return {@code true} if this map contains the specified element
   */
  public boolean containsKey(@NotNull final K o) {
    return map.containsKey(o);
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
  public ImmutableMap<@NotNull K, @NotNull V> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public IMap<@NotNull K, @NotNull V> getMap() {
    return this;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">
  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key) {
    return Optional.ofNullable(map.get(key));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters to family classes">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull K> keySet() {
    return ImmutableSet.of(unwrap().keySet(), getKey());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values() {
    return ImmutableList.of(unwrap().values(), getValue());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public Map<@NotNull K, @NotNull V> unwrap() {
    return map;
  }
  //</editor-fold>
}
