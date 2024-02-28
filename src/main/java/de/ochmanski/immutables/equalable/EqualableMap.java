package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.collection.IMap;
import de.ochmanski.immutables.immutable.ImmutableMap;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableMap<K extends @NotNull Equalable<@NotNull K>, V extends @NotNull Equalable<@NotNull V>>
  implements IMap<@NotNull K, @NotNull V> {

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

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor() {
    return (IntFunction) Equalable @NotNull []::new;
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
