package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.immutable.IList;
import de.ochmanski.immutables.immutable.ImmutableList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;
import static java.util.function.Predicate.not;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableList<E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> implements EList<@NotNull E>
{

  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  IList<@NotNull E> list = ImmutableList.noneOf(Equalable.defaultKey());

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> empty()
  {
    return (EqualableList) EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final EqualableList<@NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static EqualableList<@NotNull Dummy> createConstant()
  {
    return EqualableList.<Equalable.@NotNull Dummy>builder().build();
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
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>noneOf(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, s6, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7,
    @NotNull final String s8) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7,
    @NotNull final EqualableString s8)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7,
    @NotNull final String s8,
    @NotNull final String s9) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8), Equalable.of(s9));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7,
    @NotNull final EqualableString s8,
    @NotNull final EqualableString s9)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
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
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7,
    @NotNull final String s8,
    @NotNull final String s9,
    @NotNull final String s10) {
    return EqualableList.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8), Equalable.of(s9), Equalable.of(s10));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7,
    @NotNull final EqualableString s8,
    @NotNull final EqualableString s9,
    @NotNull final EqualableString s10)
  {
    return EqualableList.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
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
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(@NotNull final String @NotNull [] array) {
    final List<@NotNull String> list = List.of(array);
    return EqualableList.<@NotNull EqualableString>of(list);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final List<@NotNull S> list = List.of(array);
    return EqualableList.<@NotNull S>of(list, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> of(
    @NotNull final Collection<@NotNull String> collection) {
    final ImmutableList<@NotNull String> list = ImmutableList.of(collection);
    return EqualableList.<@NotNull EqualableString>ofString(list);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableList<@NotNull S> list = ImmutableList.of(collection, constructor);
    return EqualableList.<@NotNull S>of(list);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableList<@NotNull EqualableString> ofString(
    @NotNull final ImmutableList<@NotNull String> immutableList) {
    final List<EqualableString> list = immutableList.map(EqualableString::of).toList();
    final ImmutableList<@NotNull EqualableString> wrappers = ImmutableList.<@NotNull EqualableString>of(list, EqualableString @NotNull []::new);
    return EqualableList.<@NotNull EqualableString>of(wrappers);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final ImmutableList<@NotNull S> immutableList) {
    return EqualableList.<@NotNull S>builder().list(immutableList).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> of(
    @NotNull final EqualableCollection<@NotNull S> equalableCollection) {
    return EqualableList.<@NotNull S>copyOf(equalableCollection);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableList<@NotNull S> copyOf(@NotNull final EqualableCollection<@NotNull S> equalableCollection)
  {
    return EqualableList.<@NotNull S>builder().list(equalableCollection.toSet().getSet().toList()).build();
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
  @Contract(pure = true)
  public IntFunction<@NotNull E @NotNull []> getKey()
  {
    return getList().getKey();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableList<@NotNull E> add(@NotNull final E c)
  {
    return addAll(c);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableList<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableList<@NotNull E> addAll(@NotNull final EqualableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableList<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableList<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(EqualableCollectors.toList(getKey()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   *
   * @return the element at the specified position in this list
   *
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Override
  @Contract(pure = true)
  public E get(final int index)
  {
    return list.get(index);
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst()
  {
    return isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findLast()
  {
    return isEmpty() ? Optional.empty() : Optional.of(list.get(size() - 1));
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findAny()
  {
    return findFirst();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> this", pure = true)
  public EqualableList<@NotNull E> getCollection() {
    return this;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> this", pure = true)
  public EqualableList<@NotNull E> toList() {
    return this;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableSet<@NotNull E> toSet() {
    return EqualableSet.of(this);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="7. implementation of Comparable<EqualableList> interface">

  /**
   * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
   * as this object is less than, equal to, or greater than the specified object.
   *
   * <p>The implementor must ensure {@link Integer#signum
   * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
   * {@code x.compareTo(y)} must throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive:
   * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
   *
   * <p>Finally, the implementor must ensure that {@code
   * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
   *
   * @param o the object to be compared.
   *
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
   *   specified object.
   *
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
   * @apiNote It is strongly recommended, but <i>not</i> strictly required that
   *   {@code (x.compareTo(y)==0) == (x.equals(y))}. Generally speaking, any class that implements the
   *   {@code Comparable} interface and violates this condition should clearly indicate this fact.  The recommended
   *   language is "Note: this class has a natural ordering that is inconsistent with equals."
   */
  @Override
  public int compareTo(@NotNull final EqualableList<@NotNull E> o)
  {
    return orderAsc(this, o);
  }

  @Override
  public int compareTo(@NotNull final EList<@NotNull E> o)
  {
    return orderAsc(this, o);
  }

  @Contract(pure = true)
  public int compareTo(@NotNull final EqualableCollection<@NotNull E> o)
  {
    return orderAsc(this, o);
  }

  @Override
  @Contract(pure = true)
  public int compareTo(@NotNull final ECollection<@NotNull E> o)
  {
    return orderAsc(this, o);
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableList::orderAsc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableList.orderAsc());
   *   }
   * </pre>
   * Compares two objects for order. Returns a negative integer, zero, or a positive integer if the first object is less
   * than, equal to, or greater than the second object. Any of the objects can be null. A null value is considered to be
   * less than a non-null value.
   *
   * @param a the first object.
   * @param b the second object.
   *
   * @return an integer value.
   */
  @Contract(pure = true)
  public static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> int orderAsc(@Nullable final ECollection<@NotNull E> a, @Nullable final ECollection<@NotNull E> b)
  {
    return a == b ? 0 : a != null ? b != null ? orderAsc().compare(a, b) : -1 : 1;
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableList::orderDesc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableList.orderDesc());
   *   }
   * </pre>
   * Compares two objects for order. Returns a negative integer, zero, or a positive integer if the first object is less
   * than, equal to, or greater than the second object. Any of the objects can be null. A null value is considered to be
   * less than a non-null value.
   *
   * @param a the first object.
   * @param b the second object.
   *
   * @return an integer value.
   */
  @Contract(pure = true)
  public static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> int orderDesc(@Nullable final ECollection<@NotNull E> a, @Nullable final ECollection<@NotNull E> b)
  {
    return a == b ? 0 : a != null ? b != null ? orderDesc().compare(a, b) : 1 : -1;
  }

  @NotNull
  @Contract(pure = true)
  public static <S> Comparator<@NotNull S> orderAsc()
  {
    return (Comparator)ORDER_BY_ID_COMPARATOR_ASCENDING;
  }

  @NotNull
  @Contract(pure = true)
  public static <S> Comparator<@NotNull S> orderDesc()
  {
    return (Comparator)ORDER_BY_ID_COMPARATOR_DESCENDING;
  }

  @NotNull
  private static final Comparator<? extends @NotNull EqualableList<?>> ORDER_BY_ID_COMPARATOR_ASCENDING = createComparatorAsc();

  @NotNull
  private static final Comparator<? extends @NotNull EqualableList<?>> ORDER_BY_ID_COMPARATOR_DESCENDING = createComparatorDesc();

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableList<@NotNull E>> createComparatorAsc()
  {
    return Comparator.nullsLast(
      Comparator.comparingInt(EqualableList<@NotNull E>::size)
        .thenComparing(EqualableList<@NotNull E>::getClassName, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(EqualableList::hashCode));
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableList<@NotNull E>>  createComparatorDesc()
  {
    return Comparator.nullsFirst(
      Comparator.comparingInt(EqualableList<@NotNull E>::size)
        .thenComparing(EqualableList<@NotNull E>::getClassName, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparingInt(EqualableList::hashCode));
  }

  @NonNls
  @NotNull
  @NotBlank
  @Unmodifiable
  @Contract(pure = true)
  public String getClassName()
  {
    return this.getClass().getName();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="8. custom methods">

  @NotNull
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public EqualableSortedSet<@NotNull E> findDuplicates() {
    Set<E> elements = new HashSet<>();
    return filter(not(elements::add))
      .collect(EqualableCollectors.toSortedSet(getKey()));
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   *
   * @return the element at the specified position in this list or {@code Optional.empty()} if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  public Optional<@Nullable E> findByIndex(final int index)
  {
    return isWithinBounds(index) ? Optional.of(get(index)) : Optional.empty();
  }

  @Contract(pure = true)
  private boolean isNotWithinBounds(final int index)
  {
    return !isWithinBounds(index);
  }

  @Contract(pure = true)
  private boolean isWithinBounds(final int index)
  {
    return !isIndexLessThanZero(index) && !isIndexGreaterOrEqualToSize(index);
  }

  @Contract(pure = true)
  public boolean isIndexLessThanZero(final int index)
  {
    return index < 0;
  }

  @Contract(pure = true)
  public boolean isIndexGreaterOrEqualToSize(final int index)
  {
    return index >= size();
  }

  @Contract(pure = true)
  public boolean isSizeGreaterThanZero()
  {
    return size() > 0;
  }

  @Contract(pure = true)
  public boolean hasExactlyOneElement()
  {
    return size() == 1;
  }

  @Contract(pure = true)
  public boolean hasMoreThanOneElement()
  {
    return hasMultipleElements();
  }

  @Contract(pure = true)
  public boolean hasMultipleElements()
  {
    return isSizeGreaterThanOne();
  }

  @Contract(pure = true)
  public boolean isSizeGreaterThanOne()
  {
    return size() > 1;
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
