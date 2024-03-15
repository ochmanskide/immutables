package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.collection.IList;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.immutable.ImmutableList;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.EnumSet;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

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
public class ImmutableEnumList<E extends @NotNull Enum<@NotNull E>> implements IList<@NotNull E> {

  @NonNull
  @NotNull("Given set cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableList<@NonNull @NotNull E> list = ImmutableList.<@NotNull E>empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Enum<?> @NotNull []> DEFAULT_KEY = Enum @NotNull []::new;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings(Constants.Warning.UNCHECKED)
  public static <E extends @NotNull Enum<@NotNull E>> ImmutableEnumList<@NotNull E> empty() {
    return EMPTY_SET;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @SuppressWarnings(Constants.Warning.RAWTYPES)
  private static final ImmutableEnumList EMPTY_SET = create();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  private static <E extends @NotNull Enum<@NotNull E>> ImmutableEnumList<@NotNull E> create() {
    return ImmutableEnumList.<@NotNull E>builder().build();
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
   *   final ISet<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumList.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ImmutableEnumList.noneOf(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = ImmutableEnumList.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = ImmutableEnumList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final Class<@NotNull S> componentTypeE = getComponentTypeFromConstructor(constructor);
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(EnumSet.<@NotNull S>noneOf(componentTypeE), constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ICollection.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableList<@NotNull S> set = ImmutableList.of(array, constructor);
    return ImmutableEnumList.<@NotNull S>of(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final Collection<? extends @NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    if (collection.isEmpty()) {
      return ImmutableEnumList.<@NotNull S>noneOf(constructor);
    }
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(collection, constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> ofEnumSet(
    @NotNull final EnumSet<@NotNull S> enumSet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableList<@NotNull S> immutableList = ImmutableList.<@NotNull S>of(enumSet, constructor);
    return ImmutableEnumList.<@NotNull S>of(immutableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableList<@NotNull S> immutableList) {
    return ImmutableEnumList.<@NotNull S>builder().list(immutableList).key(immutableList.getKey()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> ofAll(
    @NotNull final IntFunction<@NotNull S @NotNull []> key) {
    return ImmutableEnumList.<@NotNull S>allOf(key);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> allOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> key) {
    final Class<@NotNull S> componentType = getComponentTypeFromConstructor(key);
    return ImmutableEnumList.<@NotNull S>ofEnumSet(EnumSet.<@NotNull S>allOf(componentType), key);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableEnumList.<@NotNull S>noneOf(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableEnumSet<@NotNull S> set) {
    return set.toList();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S>> ImmutableEnumList<@NotNull S> of(
    @NotNull final ImmutableSet<@NotNull S> set) {
    return ImmutableEnumList.<@NotNull S>builder().list(set.toList()).key(set.getKey()).build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumList<@NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _,_ -> new", pure = true)
  public ImmutableEnumList<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to) {
    return ImmutableEnumList.<@NotNull E>ofEnumSet(EnumSet.<@NotNull E>range(from, to), getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableEnumSet<@NotNull E> toSet() {
    return ImmutableEnumSet.of(this);
  }
  //</editor-fold>

  @NotNull
  @Override
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public String toString() {
    return list.toString();
  }
}
