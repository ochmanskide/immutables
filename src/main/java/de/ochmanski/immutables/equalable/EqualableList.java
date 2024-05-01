package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.collection.IList;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.immutable.ImmutableList;
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

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableList<E extends @NotNull Equalable<@NotNull E>> implements IList<@NotNull E>
{

  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  ImmutableList<@NotNull E> list = (ImmutableList) ImmutableList.empty();

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @SuppressWarnings({ Constants.Warning.UNCHECKED, Constants.Warning.RAWTYPES })
  @Contract(value = "-> new", pure = true)
  private static <S extends @NotNull Equalable<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Fluent<?> @NotNull []> DEFAULT_KEY = Fluent @NotNull []::new;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ Constants.Warning.UNCHECKED })
  public static EqualableList<? extends @NotNull Fluent<?>> empty()
  {
    return EMPTY;
  }

  @NotNull
  @SuppressWarnings({ Constants.Warning.UNCHECKED, Constants.Warning.RAWTYPES })
  private static final EqualableList EMPTY = EqualableList.builder().build();
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">
  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #noneOf(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = EqualableList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = EqualableList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = EqualableList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  public static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: EqualableList.noneOf(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = EqualableList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = EqualableList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = EqualableList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableList.<@NotNull S>of(ImmutableList.noneOf(constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(
    @NotNull final String s1) {
    return EqualableList.ofString(ImmutableList.of(s1));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.of(s1, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(
    @NotNull final String s1,
    @NotNull final String s2) {
    return EqualableList.ofString(ImmutableList.of(s1, s2));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.of(s1, s2, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3) {
    return EqualableList.ofString(ImmutableList.of(s1, s2, s3));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.of(s1, s2, s3, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4) {
    return EqualableList.ofString(ImmutableList.of(s1, s2, s3, s4));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.of(ImmutableList.of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(@NotNull final String @NotNull [] array) {
    final List<@NotNull String> list = List.of(array);
    return EqualableList.<@NotNull StringWrapper>of(list);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final List<@NotNull S> list = List.of(array);
    return EqualableList.<@NotNull S>of(list, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> of(
    @NotNull final Collection<@NotNull String> collection) {
    final ImmutableList<@NotNull String> list = ImmutableList.of(collection);
    return EqualableList.<@NotNull StringWrapper>ofString(list);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableList<@NotNull S> list = ImmutableList.of(collection, constructor);
    return EqualableList.<@NotNull S>of(list);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableList<@NotNull StringWrapper> ofString(
    @NotNull final ImmutableList<@NotNull String> immutableList) {
    final List<StringWrapper> list = immutableList.stream().map(StringWrapper::of).toList();
    final ImmutableList<@NotNull StringWrapper> wrappers = ImmutableList.<@NotNull StringWrapper>of(list, StringWrapper @NotNull []::new);
    return EqualableList.<@NotNull StringWrapper>builder().list(wrappers).key(StringWrapper[]::new).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final ImmutableList<@NotNull S> immutableList) {
    return EqualableList.<@NotNull S>builder().list(immutableList).key(immutableList.getKey()).build();
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
  public EqualableList<@NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public ImmutableList<@NotNull E> getList() {
    return list;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<@NotNull E> toSet() {
    return EqualableSet.of(this);
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
