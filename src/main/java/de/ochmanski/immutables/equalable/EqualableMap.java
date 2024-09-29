package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.equalable.Equalable.Dummy;
import de.ochmanski.immutables.immutable.IMap;
import de.ochmanski.immutables.immutable.ImmutableMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collector;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableMap<K extends @NotNull Comparable<@NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<@NotNull V> & @NotNull Equalable<@NotNull V>>
  implements EMap<@NotNull K, @NotNull V>
{

  @Unmodifiable
  @UnmodifiableView
  @NotNull("EqualableEnumMap::Builder 001 Given EqualableEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 001: Given map cannot be null.")
  @Builder.Default
  ImmutableMap<@NotNull K, @NotNull V> map = ImmutableMap.empty();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <K extends @NotNull Comparable<@NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<@NotNull V> & @NotNull Equalable<@NotNull V>> EqualableMap<@NotNull K, @NotNull V> empty()
  {
    return (EqualableMap) EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final EqualableMap<@NotNull Dummy, @NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static EqualableMap<@NotNull Dummy, @NotNull Dummy> createConstant()
  {
    return EqualableMap.<@NotNull Dummy, @NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_ -> new", pure = true)
  public static <K extends @NotNull Comparable<@NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<@NotNull V> & @NotNull Equalable<@NotNull V>> EqualableMap<@NotNull K, @NotNull V> noneOf(
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    return EqualableMap.<@NotNull K, @NotNull V>of(Map.of(), key, value);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <K extends @NotNull Comparable<@NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<@NotNull V> & @NotNull Equalable<@NotNull V>> EqualableMap<@NotNull K, @NotNull V> of(
    @NotNull final Map<@NotNull K, @NotNull V> map,
    @NotNull final IntFunction<@NotNull K @NotNull []> key,
    @NotNull final IntFunction<@NotNull V @NotNull []> value)
  {
    return EqualableMap.<@NotNull K, @NotNull V>of(ImmutableMap.of(map, key, value));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <K extends @NotNull Comparable<@NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<@NotNull V> & @NotNull Equalable<@NotNull V>> EqualableMap<@NotNull K, @NotNull V> of(
    @NotNull final ImmutableMap<@NotNull K, @NotNull V> map)
  {
    return EqualableMap.<@NotNull K, @NotNull V>builder().map(map).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of EMap interface">

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
  public EqualableMap<@NotNull K, @NotNull V> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public EqualableSet<@NotNull K> findByValue(@NotNull final V value) {
    return stream()
      .filter(p -> p.getValue().isEqualTo(value))
      .map(IMap.Entry::getKey)
      .collect(EqualableCollectors.toSet(getKey()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<EMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet2()
  {
    return EqualableSet.of(getMap().entrySet())
      .map(EMap.Entry::of2)
      .collect(toEqualableSet());
  }

  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  private Collector<@NotNull Entry<@NotNull K, @NotNull V>, @NotNull HashSet<@NotNull Entry<@NotNull K, @NotNull V>>, @NotNull EqualableSet<@NotNull Entry<@NotNull K, @NotNull V>>> toEqualableSet()
  {
    final IntFunction<@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> constructor = EMap.Entry[]::new;
    return EqualableCollectors.<@NotNull Entry<@NotNull K, @NotNull V>>toSet(constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<@NotNull K> keySet() {
    return EqualableSet.of(getMap().keySet());
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableList<@NotNull V> values() {
    return EqualableList.of(getMap().values());
  }
  //</editor-fold>
}
