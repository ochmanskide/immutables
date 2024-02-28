package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.collection.IList;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumList;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumSet;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

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

  @NonNull
  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  ImmutableEnumList<@NonNull @NotNull E> list = ImmutableEnumList.<@NotNull E>empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Fluent<?> @NotNull []> DEFAULT_KEY = Fluent @NotNull []::new;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings(Constants.Warning.UNCHECKED)
  public static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> empty() {
    return EMPTY_SET;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @SuppressWarnings(Constants.Warning.RAWTYPES)
  private static final FluentEnumList EMPTY_SET = create();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S> & Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> create() {
    return FluentEnumList.<@NotNull S>builder().build();
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
   *   final IList<Dummy> actual = ImmutableEnumList.ofGenerator(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.ofGenerator(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: ImmutableEnumList.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = ImmutableEnumList.ofGenerator(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.ofGenerator(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<? extends @NotNull S>> FluentEnumList<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.ofGenerator(constructor));
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
