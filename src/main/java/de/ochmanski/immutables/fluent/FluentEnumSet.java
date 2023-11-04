package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.ISet;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
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
public class FluentEnumSet<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>>
  implements ISet<@NotNull E>
{

  @Unmodifiable
  @UnmodifiableView
  @NonNull
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ImmutableEnumSet<@NonNull @NotNull E> set = ImmutableEnumSet.<@NotNull E>empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given constructor cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> empty()
  {
    return EMPTY;
  }

  private static final FluentEnumSet EMPTY = FluentEnumSet.builder().build();

  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = " -> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) Fluent @NotNull []::new;
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
   *   final ISet<Dummy> actual = FluentEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = FluentEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = FluentEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: FluentEnumSet.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = FluentEnumSet.ofGenerator(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = FluentEnumSet.ofGenerator(DayOfWeek[]::new);
   *   final ISet<Month> actual = FluentEnumSet.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumSet.<@NotNull S>of(ImmutableEnumSet.<@NotNull S>noneOf(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, constructor);
    return FluentEnumSet.<@NotNull S>of(of);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, constructor);
    return FluentEnumSet.<@NotNull S>of(of);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, s3, constructor);
    return FluentEnumSet.<@NotNull S>of(of);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableEnumSet<@NotNull S> of = ImmutableEnumSet.<@NotNull S>of(s1, s2, s3, s4, constructor);
    return FluentEnumSet.<@NotNull S>of(of);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> set = ImmutableEnumSet.ofArray(array, constructor);
    return FluentEnumSet.<@NotNull S>of(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> ofCollection(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> set = ImmutableEnumSet.<@NotNull S>of(collection, constructor);
    return FluentEnumSet.<@NotNull S>of(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final ImmutableEnumSet<@NotNull S> set)
  {
    return FluentEnumSet.<@NotNull S>builder().set(set).key(set.getKey()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> allOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> key)
  {
    return FluentEnumSet.<@NotNull S>of(ImmutableEnumSet.<@NotNull S>allOf(key));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> ofEnumSet(
    @NotNull final EnumSet<@NotNull S> enumSet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final ImmutableEnumSet<@NotNull S> immutableSet = ImmutableEnumSet.<@NotNull S>ofEnumSet(enumSet, constructor);
    return FluentEnumSet.<@NotNull S>of(immutableSet);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumSet<@NotNull S> of(
    @NotNull final FluentEnumList<@NotNull S> enumList) {
    return FluentEnumSet.<@NotNull S>of(enumList.getList().toSet());
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
  public FluentEnumSet<? extends @NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EnumSet<@NotNull E> unwrap() {
    return set.unwrap();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public FluentEnumSet<? extends @NotNull E> range(@NotNull final E from, @NotNull final E to) {
    return FluentEnumSet.<@NotNull E>ofEnumSet(EnumSet.range(from, to), getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumList<@NotNull E> toList() {
    return FluentEnumList.ofEnumSet(unwrap(), key);
  }
  //</editor-fold>
}
