package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.IMap;
import de.ochmanski.immutables.ISet;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class EqualableSet<E extends @NotNull Equalable<@NotNull E>> implements ISet<@NotNull E>
{

  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableSet<@NonNull @NotNull E> set = ImmutableSet.empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <E extends @NotNull Equalable<@NotNull E>> EqualableSet<@NotNull E> empty() {
    return EMPTY_SET;
  }

  private static final EqualableSet EMPTY_SET = EqualableSet.builder().build();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  public static <S extends @NotNull Equalable<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) Equalable @NotNull []::new;
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = EqualableSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: EqualableSet.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = EqualableSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>ofGenerator(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableSet<@NotNull S> set = ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, constructor);
    return EqualableSet.<@NotNull S>of(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.ofArray(array, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>ofGenerator(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(array, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Equalable<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> EqualableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entries,
    @NotNull final IntFunction<IMap.@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> entry) {
    return entries.stream().map(EqualableSet::toImmutableEntry).collect(EqualableCollectors.toSet(entry));
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private static <K, V> IMap.@Unmodifiable @UnmodifiableView @NotNull Entry<@NotNull K, @NotNull V> toImmutableEntry(
    @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> entry) {
    return IMap.Entry.<@NotNull K, @NotNull V>builder().key(entry.getKey()).value(entry.getValue()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <K extends @NotNull Equalable<@NotNull K>, V extends @NotNull Equalable<@NotNull V>> EqualableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final ImmutableSet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> entries) {
    return entries.stream().map(e -> e.toBuilder().build()).collect(EqualableCollectors.toSet(entries.getKey()));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> keySet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableSet<@NotNull S> immutableSet = ImmutableSet.<@NotNull S>of(keySet, constructor);
    return EqualableSet.<@NotNull S>of(immutableSet);
  }

  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final ImmutableSet<@NotNull S> immutableSet) {
    return EqualableSet.<@NotNull S>builder().set(immutableSet).key(immutableSet.getKey()).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final EqualableList<@NotNull S> equalableList) {
    return EqualableSet.<@NotNull S>of(equalableList.unwrap(), equalableList.getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ISet interface">

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<? extends @NotNull E> deepClone() {
    return toBuilder().key(key).build();
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableList<@NotNull E> toList() {
    return EqualableList.<@NotNull E>of(unwrap(), key);
  }
  //</editor-fold>
}
