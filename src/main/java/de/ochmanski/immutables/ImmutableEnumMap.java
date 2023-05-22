package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntFunction;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableEnumMap<K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
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
  IntFunction<@NotNull K[]> key = defaultConstructor();

  @NonNull
  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V[]> value = defaultValueConstructor();

  @NotNull
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static <S extends Enum<@NotNull S> & Equalable<@NotNull S>> IntFunction<@NotNull S[]> defaultConstructor()
  {
    return (IntFunction)Empty[]::new;
  }

  private static class Empty implements Equalable<@NotNull Empty>
  {
  }

  @NotNull
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static <S extends Equalable<@NotNull S>> IntFunction<@NotNull S[]> defaultValueConstructor()
  {
    return (IntFunction)EmptyValue[]::new;
  }

  private static class EmptyValue implements Equalable<ImmutableEnumMap.@NotNull EmptyValue>
  {
  }

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
  static void of()
  {
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
  static <K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V>
  ofGenerator(@NotNull final IntFunction<@NotNull K[]> key, @NotNull final IntFunction<@NotNull V[]> value)
  {
    return ImmutableEnumMap.<K, V>builder().key(key).value(value).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1)
  {
    final Map<@NonNull @NotNull K, V> map = new EnumMap<K, V>(k1, v1);
    map.put(k1, v1);
    return ImmutableEnumMap.<K, V>builder().map(map).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2)
  {
    return ImmutableEnumMap.<K, V>builder().map(EnumMap.of(k1, v1, k2, v2)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ -> new", pure = true)
  static <K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3)
  {
    return ImmutableEnumMap.<K, V>builder().map(EnumMap.of(k1, v1, k2, v2, k3, v3)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ -> new", pure = true)
  static <K extends Enum<@NotNull K> & Equalable<@NotNull K>, V extends Equalable<@NotNull V>> IMap<@NotNull K, @NotNull V> of(
      @NotNull final K k1, @NotNull final V v1,
      @NotNull final K k2, @NotNull final V v2,
      @NotNull final K k3, @NotNull final V v3,
      @NotNull final K k4, @NotNull final V v4)
  {
    return ImmutableEnumMap.<K, V>builder().map(EnumMap.of(k1, v1, k2, v2, k3, v3, k4, v4)).build();
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
  public ImmutableEnumMap<K, V> deepClone()
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
    return ISet.copyOf(toMap().keySet());
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public IList<@NotNull V> value()
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