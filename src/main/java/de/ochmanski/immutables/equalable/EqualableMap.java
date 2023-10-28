package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.IList;
import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.ISet;
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

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class EqualableMap<K extends @NotNull Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
  implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("EqualableEnumMap::Builder 001 Given EqualableEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 001: Given map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, V> map = Map.of();

  @NonNull
  @NotNull("EqualableEnumMap::Builder 002: Given EqualableEnumMap::generator cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 002: Given FluentEnumMap::generator cannot be null.")
  IntFunction<@NonNull @NotNull K @NonNull @NotNull []> generator;

  @NonNull
  @NotNull("EqualableEnumMap::Builder 003: Given EqualableEnumMap::keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 003: Given FluentEnumMap::keyType cannot be null.")
  Class<@NonNull @NotNull K> keyType;

  @NonNull
  @NotNull("EqualableEnumMap::Builder 004: Given EqualableEnumMap::valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 004: Given FluentEnumMap::valueType cannot be null.")
  Class<@NonNull @NotNull V> valueType;

  @NotNull
  @Override
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return Optional.ofNullable(map.get(key));
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
        .collect(EqualableCollectors.toSet(getGenerator()));
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
    return ISet.of(toMap().keySet(), getGenerator());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public IList<@NotNull V> values()
  {
    return IList.of(toMap().values());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Map<@NotNull K, @NotNull V> toMap()
  {
    return Map.copyOf(map);
  }

}
