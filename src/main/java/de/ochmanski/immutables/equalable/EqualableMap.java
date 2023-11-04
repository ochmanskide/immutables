package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.immutable.ImmutableMap;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
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
public class EqualableMap<K extends @NotNull Equalable<@NotNull K>, V extends @NotNull Equalable<@NotNull V>>
  implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("EqualableEnumMap::Builder 001 Given EqualableEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 001: Given map cannot be null.")
  @Builder.Default
  ImmutableMap<@NonNull @NotNull K, @NotNull V> map = ImmutableMap.empty();

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

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor() {
    return (IntFunction) Equalable @NotNull []::new;
  }

  @NotNull
  @Override
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return map.get(key);
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public EqualableSet<@NotNull K> findByValue(@NotNull final V value)
  {
    return stream()
        .filter(p -> p.getValue().isEqualTo(value))
        .map(Entry::getKey)
      .collect(EqualableCollectors.toSet(getKey()));
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
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
  public EqualableMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  {
    return EqualableSet.<@NotNull K, @NotNull V>copyOfEntries(unwrap().entrySet(), Entry[]::new);
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<@NotNull K> keySet()
  {
    return EqualableSet.of(unwrap().keySet(), getKey());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableList<@NotNull V> values()
  {
    return EqualableList.of(unwrap().values(), getValue());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Map<@NotNull K, @NotNull V> unwrap()
  {
    return map.unwrap();
  }

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IMap interface">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters to family classes">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">

  //</editor-fold>
}
