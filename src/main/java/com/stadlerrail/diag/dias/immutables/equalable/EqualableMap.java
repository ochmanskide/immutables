package com.stadlerrail.diag.dias.immutables.equalable;

import com.stadlerrail.diag.dias.immutables.collection.IMap;
import com.stadlerrail.diag.dias.immutables.fluent.Fluent;
import com.stadlerrail.diag.dias.immutables.immutable.ImmutableMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.IntFunction;

import static com.stadlerrail.diag.dias.immutables.constants.Constants.Warning.RAWTYPES;
import static com.stadlerrail.diag.dias.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableMap<K extends @NotNull Equalable<@NotNull K> & @NotNull Comparable<? super @NotNull K>, V extends @NotNull Equalable<@NotNull V>>
  implements IMap<@NotNull K, @NotNull V>
{

  @UnmodifiableView
  @NotNull("EqualableEnumMap::Builder 001 Given EqualableEnumMap::map cannot be null.")
  @javax.validation.constraints.NotNull(message = "EqualableEnumMap::Builder 001: Given map cannot be null.")
  @Builder.Default
  ImmutableMap<@NotNull K, @NotNull V> map = (ImmutableMap)ImmutableMap.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull K @NotNull []> key = defaultConstructor();

  @NotNull("Given valueType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given valueType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull V @NotNull []> value = defaultConstructor();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor() {
    return (IntFunction)Fluent @NotNull []::new;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IMap interface">

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
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableMap<@NotNull K, @NotNull V> getMap() {
    return map;
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(pure = true)
  public EqualableSet<@NotNull K> findByValue(@NotNull final V value) {
    return stream()
      .filter(p -> p.getValue().isEqualTo(value))
      .map(Entry::getKey)
      .collect(EqualableCollectors.toSet(getKey()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
    return EqualableSet.<@NotNull K, @NotNull V>copyOfEntries(getMap().entrySet());
  }

  @NotNull
  @Override
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
