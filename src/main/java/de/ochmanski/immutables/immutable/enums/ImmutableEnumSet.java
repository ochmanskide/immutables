package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.ISet;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.fluent.Fluent.Dummy;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * Immutable wrapper of <pre>{@code java.util.EnumSet<K,V>}</pre>
 * <p>This Read-Only implementation of <pre>{@code Set<>}</pre> interface
 * doesn't accept Null elements.
 * <p>This Set doesn't allow modifications. All mutators/setters were disabled.
 *
 * @param <E> {@code @NotNull E } key
 */
@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class ImmutableEnumSet<E extends @NotNull Enum<@NotNull E>> implements ISet<@NotNull E> {

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableSet<@NotNull E> set = (ImmutableSet) ImmutableSet.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E @NotNull []> key = Fluent.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static ImmutableEnumSet<? extends @NotNull Enum<?>> empty() {
    return EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final ImmutableEnumSet<? extends @NotNull Enum<?>> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static ImmutableEnumSet<? extends @NotNull Enum<?>> createConstant() {
    return ImmutableEnumSet.<@NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #noneOf(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ImmutableEnumSet.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumSet.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumSet.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  public static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ImmutableEnumSet.noneOf(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ImmutableEnumSet.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumSet.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumSet.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final Class<@NotNull S> componentTypeE = getComponentTypeFromConstructor(constructor);
    return ImmutableEnumSet.<@NotNull S>ofEnumSet(EnumSet.<@NotNull S>noneOf(componentTypeE), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final S s9,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_,_,_,_,_,_,_,_,_,_,_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final S s9,
    @NotNull final S s10,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(EnumSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final EnumSet<@NotNull S> set = EnumSet.copyOf(List.of(array));
    return ImmutableEnumSet.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumSet.<@NotNull S>of(array, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> allOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> key) {
    final Class<@NotNull S> componentTypeE = getComponentTypeFromConstructor(key);
    return ImmutableEnumSet.<@NotNull S>ofEnumSet(EnumSet.<@NotNull S>allOf(componentTypeE), key);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> keySet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    if (keySet.isEmpty()) {
      return ImmutableEnumSet.<@NotNull S>noneOf(constructor);
    }
    final EnumSet<@NotNull S> enumSet = EnumSet.<@NotNull S>copyOf(keySet);
    return ImmutableEnumSet.<@NotNull S>ofEnumSet(enumSet, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> ofEnumSet(
    @NotNull final EnumSet<@NotNull S> enumSet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableSet<@NotNull S> immutableSet = ImmutableSet.<@NotNull S>of(enumSet, constructor);
    return ImmutableEnumSet.<@NotNull S>of(immutableSet);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final ImmutableSet<@NotNull S> immutableSet) {
    return ImmutableEnumSet.<@NotNull S>builder().set(immutableSet).key(immutableSet.getKey()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }


  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumSet<@NotNull S> of(
    @NotNull final ImmutableEnumList<@NotNull S> immutableList) {
    return ImmutableEnumSet.<@NotNull S>of(immutableList.getList().toSet());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ISet interface">

  @Override
  @Contract(pure = true)
  @SuppressWarnings(Constants.Warning.SIMPLIFY_STREAM_API_CALL_CHAINS)
  public void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer, @NotNull final Comparator<? super @NotNull E> comparator) {
    final TreeSet<@NotNull E> sortedSet = new TreeSet<@NotNull E>(comparator);
    sortedSet.addAll(unwrap());
    sortedSet.stream().forEachOrdered(consumer);
  }

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<? extends @NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableSet<@NotNull E> getSet() {
    return set;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumSet<@NotNull E> unwrap() {
    if (isEmpty()) {
      return EnumSet.noneOf(getComponentTypeFromKey());
    }
    return EnumSet.<@NotNull E>copyOf(set.unwrap());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _,_ -> new", pure = true)
  public ImmutableEnumSet<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to) {
    return ImmutableEnumSet.<@NotNull E>of(EnumSet.<@NotNull E>range(from, to), getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumList<@NotNull E> toList() {
    return ImmutableEnumList.ofEnumSet(unwrap(), getKey());
  }
  //</editor-fold>

  @NotNull
  @Override
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public String toString() {
    return set.toString();
  }
}
