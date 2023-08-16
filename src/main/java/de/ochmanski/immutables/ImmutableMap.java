package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.IntFunction;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableMap<K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
    implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given map cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given map cannot be null.")
  @Builder.Default
  Map<@NonNull @NotNull K, V> map = Map.of();

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
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static <S extends Equalable<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Empty @NotNull []::new;
  }

  private static class Empty implements Equalable<@NotNull Empty>
  {
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
  public ImmutableMap<@NotNull K, @NotNull V> deepClone()
  {
    return toBuilder().build();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ISet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet()
  {
    return ISet.copyOfEntries(toMap().entrySet());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ISet<@NotNull K> keySet()
  {
    return ISet.copyOf(toMap().keySet());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public IList<@NotNull V> values()
  {
    return IList.copyOf(toMap().values());
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
