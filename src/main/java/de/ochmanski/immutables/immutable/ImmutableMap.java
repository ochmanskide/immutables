package de.ochmanski.immutables.immutable;

import com.stadlerrail.diag.dias.servicestate.enums.Equalable;
import de.ochmanski.immutables.IMap;
import lombok.*;
import org.jetbrains.annotations.*;

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
public class ImmutableMap<K, V> implements IMap<@NotNull K, @NotNull V>
{

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

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Object @NotNull []::new;
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_,_ -> new", pure = true)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> ofGenerator(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    return ImmutableMap.<@NotNull K, @NotNull V>builder().key(key).value(value).map(Map.of()).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    return ImmutableMap.<@NotNull K, @NotNull V>builder().map(map).key(key).value(value).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <K, V> ImmutableMap<@NotNull K, @NotNull V> empty()
  {
    return EMPTY;
  }

  private static final ImmutableMap EMPTY = ImmutableMap.builder().build();

  @NotNull
  public Optional<@Nullable V> get(@NotNull final K key)
  {
    return Optional.ofNullable(map.get(key));
  }

  @NotNull
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableSet<@NotNull K> findByValue(@NotNull final V value)
  {
    return stream()
      .filter(p -> Equalable.<@NotNull V>areEqual(p.getValue(), value))
      .map(Entry::getKey)
      .collect(ImmutableCollectors.toSet(getKey()));
  }

  @NotNull
  @UnmodifiableView
  @Contract(pure = true)
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
  public ImmutableMap<@NotNull K, @NotNull V> deepClone()
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
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull K> keySet()
  {
    return ImmutableSet.copyOf(toMap().keySet(), getKey());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull V> values()
  {
    return ImmutableList.copyOf(toMap().values(), getValue());
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Map<@NotNull K, @NotNull V> toMap()
  {
    return Map.copyOf(map);
  }

}
