package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.equalable.Equalable.Dummy;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@EqualsAndHashCode(doNotUseGetters = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class ImmutableMap<K, V> implements IMap<@NotNull K, @NotNull V>
{

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NotNull K, @NotNull V> map = (Map) Collections.checkedMap(Map.of(), Equalable.Dummy.class, Equalable.Dummy.class);

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = Equalable.defaultKey();

  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = Equalable.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <K extends @NotNull Comparable<? super @NotNull K>, V> ImmutableMap<@NotNull K, @NotNull V> empty()
  {
    return (ImmutableMap) EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final ImmutableMap<@NotNull Dummy, @NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static ImmutableMap<@NotNull Dummy, @NotNull Dummy> createConstant()
  {
    return ImmutableMap.<@NotNull Dummy, @NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_ -> new", pure = true)
  public static <K extends @NotNull Comparable<? super @NotNull K>, V> ImmutableMap<@NotNull K, @NotNull V> noneOf(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value) {
    return ImmutableMap.<@NotNull K, @NotNull V>of(Map.of(), key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <K extends @NotNull Comparable<? super @NotNull K>, V> ImmutableMap<@NotNull K, @NotNull V> of(
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
    return ImmutableSet.<@NotNull K, @NotNull V>copyOfEntries(map.entrySet(), Entry[]::new);
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

  /**
   * Returns the number of elements in this map.
   *
   * @return the number of elements in this map
   */
  @Override
  public int size() {
    return map.size();
  }

  /**
   * Returns {@code true} if this map contains no elements.
   *
   * @return {@code true} if this map contains no elements
   */
  @Override
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
  @Override
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
  public ImmutableMap<@NotNull K, @NotNull V> getMap() {
    return this;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">
  @NotNull
  @Override
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
    return ImmutableSet.of(map.keySet(), getKey());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values() {
    return ImmutableList.of(map.values(), getValue());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public Map<@NotNull K, @NotNull V> unwrap() {
    return map.isEmpty()
           ? Collections.checkedMap(Map.of(), getComponentTypeFromKey(), getComponentTypeFromValue())
           : Collections.checkedMap(Map.copyOf(map), getComponentTypeFromKey(), getComponentTypeFromValue());
  }
  //</editor-fold>
}
