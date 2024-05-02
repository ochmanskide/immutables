package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.collection.IList;
import de.ochmanski.immutables.fluent.Fluent.Dummy;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumList;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Immutable wrapper of <pre>{@code java.util.EnumList<K,V>}</pre>
 * <p>This Read-Only implementation of <pre>{@code List<>}</pre> interface
 * doesn't accept Null elements.
 * <p>This List doesn't allow modifications. All mutators/listters were disabled.
 *
 * @param <E> {@code @NotNull E } key
 */
@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class FluentEnumList<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>>
  implements IList<@NotNull E> {

  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  ImmutableEnumList<@NotNull E> list = (ImmutableEnumList) ImmutableEnumList.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E @NotNull []> key = Fluent.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static FluentEnumList<? extends @NotNull Fluent<?>> empty() {
    return EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final FluentEnumList<@NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static FluentEnumList<@NotNull Dummy> createConstant() {
    return FluentEnumList.<@NotNull Dummy>builder().build();
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
   *   final IList<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.noneOf(Month[]::new);
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
   *   final IList<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.noneOf(constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, s5, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, s5, s6, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, s5, s6, s7, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, s5, s6, s7, s8, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final List<@NotNull S> list = List.of(array);
    return FluentEnumList.<@NotNull S>of(list, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> ofEnumSet(
    @NotNull final Collection<@NotNull S> set,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(set, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>builder()
      .list(ImmutableEnumList.<@NotNull S>of(collection, constructor))
      .key(constructor)
      .build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final ImmutableEnumList<@NotNull S> set) {
    return FluentEnumList.<@NotNull S>builder().list(set).key(set.getKey()).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> allOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> key) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumSet.<@NotNull S>allOf(key));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final ImmutableEnumSet<@NotNull S> set) {
    return FluentEnumList.<@NotNull S>builder().list(set.toList()).key(set.getKey()).build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">

  /**
   * Returns a deep copy of this {@code ArrayList} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayList} instance
   */
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumList<@NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableEnumList<@NotNull E> getList() {
    return list;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumSet<@NotNull E> toSet() {
    return FluentEnumSet.of(this);
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
