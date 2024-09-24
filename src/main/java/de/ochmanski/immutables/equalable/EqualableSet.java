package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.immutable.ISet;
import de.ochmanski.immutables.immutable.ImmutableSet;
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

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class EqualableSet<E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> implements ESet<@NotNull E>
{

  @Unmodifiable
  @UnmodifiableView
  @NotNull("Given set cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given set cannot be null.")
  @Builder.Default
  ISet<@NotNull E> set = ImmutableSet.empty();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S extends Comparable<@NotNull S> & Equalable<@NotNull S>> EqualableSet<@NotNull S> empty()
  {
    return (EqualableSet) EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final EqualableSet<Equalable.@NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static EqualableSet<Equalable.@NotNull Dummy> createConstant()
  {
    return EqualableSet.<Equalable.@NotNull Dummy>builder().build();
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
   *   final ISet<Dummy> actual = EqualableSet.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of() {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: EqualableSet.noneOf(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = EqualableSet.noneOf(Dummy[]::new);
   *   final ISet<DayOfWeek> actual = EqualableSet.noneOf(DayOfWeek[]::new);
   *   final ISet<Month> actual = EqualableSet.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>noneOf(constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _-> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7,
    @NotNull final String s8) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final EqualableString s1,
    @NotNull final EqualableString s2,
    @NotNull final EqualableString s3,
    @NotNull final EqualableString s4,
    @NotNull final EqualableString s5,
    @NotNull final EqualableString s6,
    @NotNull final EqualableString s7,
    @NotNull final EqualableString s8)
  {
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final S s5,
    @NotNull final S s6,
    @NotNull final S s7,
    @NotNull final S s8,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final String s1,
    @NotNull final String s2,
    @NotNull final String s3,
    @NotNull final String s4,
    @NotNull final String s5,
    @NotNull final String s6,
    @NotNull final String s7,
    @NotNull final String s8,
    @NotNull final String s9) {
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8), Equalable.of(s9));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
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
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
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
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "  _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
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
    return EqualableSet.<@NotNull EqualableString>of(Equalable.of(s1), Equalable.of(s2), Equalable.of(s3), Equalable.of(s4), Equalable.of(s5), Equalable.of(s6), Equalable.of(s7), Equalable.of(s8), Equalable.of(s9), Equalable.of(s10));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
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
    return EqualableSet.<@NotNull EqualableString>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
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
    return EqualableSet.<@NotNull S>of(ImmutableSet.<@NotNull S>of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(ImmutableSet.ofArray(array, constructor));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> ofArray(
    @NotNull final String @NotNull [] array) {
    return EqualableSet.<@NotNull EqualableString>ofArray(toEqualable(array), EqualableString @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_-> new", pure = true)
  private static EqualableString @NotNull [] toEqualable(@NotNull final String @NotNull [] array)
  {
    return EqualableString.ofArray(array);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> ofArray(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return EqualableSet.<@NotNull S>of(array, constructor);
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  private static <K extends @NotNull Comparable<? super @NotNull K>, V> EMap.@Unmodifiable @UnmodifiableView @NotNull Entry<@NotNull K, @NotNull V> toEqualableEntry(
    @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> entry) {
    return EMap.Entry.<@NotNull K, @NotNull V>of(entry);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <K extends @NotNull Comparable<? super @NotNull K> & @NotNull Equalable<@NotNull K>, V extends @NotNull Comparable<? super @NotNull V> & @NotNull Equalable<@NotNull V>> EqualableSet<EMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
    @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entries,
    @NotNull final IntFunction<EMap.@NotNull Entry<@NotNull K, @NotNull V> @NotNull []> entry)
  {
    return entries.stream().map(EqualableSet::toEqualableEntry).collect(EqualableCollectors.toSet(entry));
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> of(
    @NotNull final Collection<@NotNull String> collection) {
    final IntFunction<String[]> constructor = String[]::new;
    ImmutableSet<@NotNull String> set = ImmutableSet.of(collection, constructor);
    return EqualableSet.<@NotNull EqualableString>ofString(set);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final Collection<@NotNull S> keySet,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    final ImmutableSet<@NotNull S> immutableSet = ImmutableSet.<@NotNull S>of(keySet, constructor);
    return EqualableSet.<@NotNull S>of(immutableSet);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  public static EqualableSet<@NotNull EqualableString> ofString(
    @NotNull final ImmutableSet<@NotNull String> immutableSet) {
    final List<EqualableString> list = immutableSet.map(EqualableString::of).toList();
    final ImmutableSet<@NotNull EqualableString> wrappers = ImmutableSet.<@NotNull EqualableString>of(list, EqualableString @NotNull []::new);
    return EqualableSet.<@NotNull EqualableString>of(wrappers);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final ISet<@NotNull S> immutableSet) {
    return EqualableSet.<@NotNull S>builder().set(immutableSet).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final EqualableList<@NotNull S> equalableList) {
    return EqualableSet.<@NotNull S>copyOf(equalableList);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> of(
    @NotNull final EqualableCollection<@NotNull S> equalableCollection) {
    return EqualableSet.<@NotNull S>copyOf(equalableCollection);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> EqualableSet<@NotNull S> copyOf(@NotNull final EqualableCollection<@NotNull S> equalableCollection)
  {
    return EqualableSet.<@NotNull S>builder().set(equalableCollection.getCollection().toList().getList().toSet()).build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of ESet interface">

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
  public EqualableSet<? extends @NotNull E> deepClone() {
    return toBuilder().build();
  }

  @NotNull
  @Override
  @Unmodifiable
  @Contract(pure = true)
  public IntFunction<@NotNull E @NotNull []> getKey()
  {
    return getSet().getKey();
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSet<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSet<@NotNull E> addAll(@NotNull final EqualableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSet<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public EqualableSet<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(EqualableCollectors.toSet(getKey()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> this", pure = true)
  public EqualableSet<@NotNull E> getCollection() {
    return this;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public EqualableList<@NotNull E> toList() {
    return EqualableList.<@NotNull E>of(this);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> this", pure = true)
  public EqualableSet<@NotNull E> toSet() {
    return this;
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. implementation of Comparable<EqualableSet> interface">

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
  public int compareTo(@NotNull final EqualableSet<@NotNull E> o)
  {
    return EqualableSet.orderAsc(this, o);
  }

  @Override
  public int compareTo(@NotNull final ESet<@NotNull E> o)
  {
    return EqualableSet.orderAsc(this, o);
  }

  @Contract(pure = true)
  public int compareTo(@NotNull final EqualableCollection<@NotNull E> o)
  {
    return EqualableSet.orderAsc(this, o);
  }

  @Override
  @Contract(pure = true)
  public int compareTo(@NotNull final ECollection<@NotNull E> o)
  {
    return EqualableSet.orderAsc(this, o);
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableSet::orderAsc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().min(EqualableSet.orderAsc());
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
    return a == b ? 0 : a != null ? b != null ? EqualableSet.orderAsc().compare(a, b) : -1 : 1;
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableSet::orderDesc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().max(EqualableSet.orderDesc());
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
  private static final Comparator<? extends @NotNull EqualableSet<?>> ORDER_BY_ID_COMPARATOR_ASCENDING = createComparatorAsc();

  @NotNull
  private static final Comparator<? extends @NotNull EqualableSet<?>> ORDER_BY_ID_COMPARATOR_DESCENDING = createComparatorDesc();

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableSet<@NotNull E>> createComparatorAsc()
  {
    return Comparator.nullsLast(
      Comparator.comparingInt(EqualableSet<@NotNull E>::size)
        .thenComparing(EqualableSet<@NotNull E>::getClassName, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(EqualableSet::hashCode));
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static <E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> Comparator<? extends @NotNull EqualableSet<@NotNull E>> createComparatorDesc()
  {
    return Comparator.nullsFirst(
      Comparator.comparingInt(EqualableSet<@NotNull E>::size)
        .thenComparing(EqualableSet<@NotNull E>::getClassName, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparingInt(EqualableSet::hashCode));
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
    return set.toString();
  }
}
