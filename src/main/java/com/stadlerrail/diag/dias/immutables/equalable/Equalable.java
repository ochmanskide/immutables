package com.stadlerrail.diag.dias.immutables.equalable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stadlerrail.diag.dias.immutables.collection.ICollection;
import com.stadlerrail.diag.dias.immutables.collection.Not;
import com.stadlerrail.diag.dias.immutables.constants.Constants;
import com.stadlerrail.diag.dias.immutables.immutable.ImmutableSet;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.*;

import static com.stadlerrail.diag.dias.immutables.constants.Constants.Warning.*;

public interface Equalable<T extends @NotNull Equalable<@NotNull T>> {

  //<editor-fold defaultstate="collapsed" desc="1. static utility methods of Equalable<T> interface">
  @Contract(pure = true)
  public static <S> boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull S> s,
                                        @NotNull final Function<? super @NotNull S, @NotNull String> a, 
                                        @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(s.stream().map(a), b);
  }

  @Contract(pure = true)
  public static <S> boolean anyMatchIgnoreCase(@NotNull final S s, @NotNull final Function<? super @NotNull S,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull S>anyMatchIgnoreCase(a.apply(s), b);
  }

  @Contract(pure = true)
  public static boolean anyMatchIgnoreCase(
    @NotNull final Collection<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull String>anyMatchIgnoreCase(a.stream(), b);
  }

  @Contract(pure = true)
  public static boolean anyMatchIgnoreCase(
    @NotNull final Stream<@NotNull String> a,
    @NotNull final Collection<@NotNull String> b) {
    final Set<@NotNull String> s = a
      .map(String::toUpperCase)
      .collect(Collectors.toUnmodifiableSet());
    return Equalable.<@NotNull String>anyMatchT(b, c -> s.contains(c.toUpperCase()));
  }

  @Contract(pure = true)
  public static boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> elements, @NotNull final String text)
  {
    return Equalable.<@NotNull String>anyMatchT(elements, p -> p.equalsIgnoreCase(text));
  }

  @Contract(pure = true)
  public static <T> boolean anyMatchT(
    @NotNull final Collection<? extends @NotNull T> elements,
    @NotNull final Predicate<? super @NotNull T> predicate) {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  public static <T extends @NotNull Equalable<@NotNull T>> boolean anyMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().anyMatch(predicate);
  }

  @Contract(pure = true)
  public static <T extends @NotNull Equalable<@NotNull T>> boolean allMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().allMatch(predicate);
  }

  @Contract(pure = true)
  public static <T> boolean noneMatchT(@NotNull final Collection<? extends @NotNull T> elements,
                                @NotNull final Predicate<? super @NotNull T> predicate) {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(pure = true)
  public static <T extends @NotNull Equalable<@NotNull T>> boolean noneMatch(
    @NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements,
    @NotNull final Predicate<? super @NotNull Equalable<@NotNull T>> predicate) {
    return elements.stream().noneMatch(predicate);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> boolean areNotEqual(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return !Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  public static <S> boolean areNotEqual(@Nullable final S a, @Nullable final S b)
  {
    return !Equalable.<@NotNull S>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> boolean areEqual(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return Equalable.<@NotNull Equalable<@NotNull S>>areEqual(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  public static <S> boolean areEqual(@Nullable final S a, @Nullable final S b)
  {
    return Objects.equals(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> boolean areNotTheSame(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return !Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
  public static <S> boolean areNotTheSame(@Nullable final S a, @Nullable final S b)
  {
    return !Equalable.<@NotNull S>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> boolean areTheSame(
    @Nullable final Equalable<@NotNull S> a,
    @Nullable final Equalable<@NotNull S> b) {
    return Equalable.<@NotNull Equalable<@NotNull S>>areTheSame(a, b);
  }

  @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
  public static <S> boolean areTheSame(@Nullable final S a, @Nullable final S b)
  {
    return a == b;
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean anyMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean anyMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isIn(elements);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Collection<@NotNull String> b,
                                     @NotNull final Function<? super @NotNull Equalable<@NotNull T>, @NotNull String> a) {
    final String s = a.apply(this);
    return Equalable.<@NotNull String>anyMatchIgnoreCase(b, s);
  }

  @Contract(pure = true)
  default boolean anyMatchIgnoreCase(@NotNull final Function<? super @NotNull Equalable<@NotNull T>,
    @NotNull Collection<@NotNull String>> a, @NotNull final Collection<@NotNull String> b) {
    return Equalable.<@NotNull Equalable<@NotNull T>>anyMatchIgnoreCase(this, a, b);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean allMatch(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return allMatchArray(array);
  }

  @Contract(pure = true)
  default boolean allMatchArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return allMatch(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean allMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return allMatch(elements, this::isEqualTo); //TODO: test if this can be converted into elements.containsAll(this)
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean noneMatchElements(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean noneMatch(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isNotIn(elements);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isNotIn(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isNotInArray(array);
  }

  @Contract(pure = true)
  default boolean isNotInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return !isIn(array);
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isNotIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements) {
    return !isIn(elements);
  }

  @Contract(pure = true)
  @SuppressWarnings(UNCHECKED)
  default boolean isIn(@NotNull final Equalable<@NotNull T> @NotNull ... array) {
    return isInArray(array);
  }

  @Contract(pure = true)
  default boolean isInArray(@NotNull final Equalable<@NotNull T> @NotNull [] array) {
    return isIn(List.<@NotNull Equalable<@NotNull T>>of(array));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull T>> elements) {
    return isIn(Set.<@NotNull Equalable<@NotNull T>>copyOf(elements));
  }

  @Contract(pure = true)
  default boolean isIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull T>> elements) {
    return elements.contains(this);
  }

  @Contract(pure = true)
  default boolean isNotEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return !isEqualTo(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isEqualTo(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areEqual(this, other);
  }

  @Contract(pure = true)
  default boolean isNotSameAs(@Nullable final Equalable<@NotNull T> other) {
    return !isSameAs(other);
  }

  @Contract(value = "null -> false", pure = true)
  default boolean isSameAs(@Nullable final Equalable<@NotNull T> other) {
    return Equalable.<@NotNull Equalable<@NotNull T>>areTheSame(this, other);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. class Holder implements Holder<Equalable>">

  @NotNull
//  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> Equalable.Holder<@NotNull S> element(@Nullable final S s)
  {
    return Equalable.of(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> ofNullable(@Nullable final E s) {
    return EqualableEnum.<@NotNull E>element(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@NotNull final E s) {
    return EqualableEnum.<@NotNull E>element(Objects.requireNonNull(s));
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E s) {
    return EqualableEnum.<@NotNull E>of(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Equalable<@NotNull S>> Holder<@NotNull S> of(@Nullable final S s) {
    return Equalable.Holder.of(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableString ofString(@Nullable final String s) {
    return Equalable.of(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableString of(@Nullable final String s) {
    return Equalable.EqualableString.ofNullable(s);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableInteger ofInt(final int i) {
    return Equalable.of(i);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableInteger of(final int i) {
    return Equalable.EqualableInteger.of(i);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableLong ofLong(final long l) {
    return Equalable.of(l);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableLong of(final long l) {
    return Equalable.EqualableLong.of(l);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableFloat ofFloat(final float f) {
    return Equalable.of(f);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableFloat of(final float f) {
    return Equalable.EqualableFloat.of(f);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableDouble ofDouble(final double d) {
    return Equalable.of(d);
  }

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static EqualableDouble of(final double d) {
    return Equalable.EqualableDouble.of(d);
  }

  /**
   * Generic class for holding a single, nullable, non-primitive object.
   * @param <S>
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Holder<S extends @NotNull Equalable<@NotNull S>> implements Not<@NotNull S>
  {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    @SuppressWarnings({ UNCHECKED, RAWTYPES })
    public static <S extends Equalable<@NotNull S>> Holder<@NotNull S> empty() {
      return (Holder) EMPTY;
    }

    @NotNull
    @Unmodifiable
    public static final Holder<?> EMPTY = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static <S extends Equalable<@NotNull S>> Holder<@NotNull S> createConstant() {
      return Holder.<@NotNull S>builder().build();
    }

    @Getter(AccessLevel.PRIVATE)
    @Nullable S s;

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> this", pure = true)
    public Holder<@NotNull S> filter(@NotNull final Predicate<? super @NotNull S> predicate)
    {
      return ofNullable().filter(predicate).map(p -> this).orElse(Holder.empty());
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public <U> Optional<@Nullable U> flatMap(@NotNull final Function<? super @NotNull S, ? extends @NotNull Optional<? extends @Nullable U>> mapper)
    {
      return ofNullable().flatMap(mapper);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public Holder<@NotNull S> map(@NotNull final Function<? super @Nullable Holder<@NotNull S>, @Nullable Holder<@NotNull S>> mapper)
    {
      return ofNullable().map(Holder::of).map(mapper).orElse(Holder.empty());
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public Optional<@Nullable S> mapToObj(@NotNull final Function<? super @NotNull S, @NotNull S> mapper)
    {
      return ofNullable().map(mapper);
    }

    @Nullable
    @Unmodifiable
    @SuppressWarnings(DATA_FLOW_ISSUE)
    @Contract(value = "-> new", pure = true)
    public S orElseNull()
    {
      return orElse(null);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public S orElse(@SuppressWarnings(ALL) @NotNull final S other)
    {
      return ofNullable().orElse(other);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public S orElseGet(@NotNull final Supplier<? extends @NotNull S> supplier)
    {
      return ofNullable().orElseGet(supplier);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    public Optional<@Nullable S> ofNullable()
    {
      return Optional.ofNullable(s);
    }

    @Override
    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final S @NotNull ... array) {
      return isInArray(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<? extends @NotNull S> elements) {
      return !elements.isEmpty() && isIn(Set.<@NotNull S>copyOf(elements));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<? extends @NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Stream<? extends @NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.anyMatch(p -> p.isEqualTo(s));
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNotNull() {
      return !isNull();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNull() {
      return null == s;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@Nullable final E e) {
      return EqualableEnum.<@NotNull E>element(e);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E e) {
      return EqualableEnum.<@NotNull E>of(e);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <S extends @NotNull Equalable<@NotNull S>> Equalable.Holder<@NotNull S> of(@Nullable final S s) {
      return Equalable.Holder.<@NotNull S>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <S extends @NotNull Equalable<@NotNull S>> Equalable.Holder<@NotNull S> element(@Nullable final S s) {
      return Equalable.Holder.<@NotNull S>builder().s(s).build();
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. class EqualableEnum<S extends @NotNull Enum<@NotNull S>>">
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableEnum<S extends @NotNull Enum<@NotNull S>> {// implements Not<@NotNull S> { // & @NotNull Equalable<@NotNull S>> implements Equalable<S> {

    @NotNull
    @Unmodifiable
    public static final EqualableEnum<Equalable.@NotNull Dummy> EMPTY = empty();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableEnum<@NotNull Dummy> empty()
    {
      return EqualableEnum.<@NotNull Dummy>builder().s(Dummy.DUMMY_ENUM_ITEM).build();
    }

    //<editor-fold defaultstate="collapsed" desc="3.1. static utility methods">
    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> boolean areNotEqual(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b)
    {
      return !EqualableEnum.<@NotNull E>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> boolean areEqual(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b)
    {
      return EqualableEnum.<@NotNull E>areTheSame(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> boolean areNotTheSame(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b)
    {
      return !EqualableEnum.<@NotNull E>areTheSame(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> boolean areTheSame(@Nullable final Enum<@NotNull E> a, @Nullable final Enum<@NotNull E> b)
    {
      return Equalable.<@NotNull Enum<@NotNull E>>areTheSame(a, b);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="3.2. implements EqualableHolder<Enum>">

    @Nullable S s;

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final S other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final S other) {
      return Equalable.<@NotNull S>areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final S other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final S other) {
      return Equalable.<@NotNull S>areTheSame(s, other);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> of(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <E extends @NotNull Enum<@NotNull E>> EqualableEnum<@NotNull E> element(@Nullable final E s) {
      return EqualableEnum.<@NotNull E>builder().s(s).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="3.3. old Java Collection API">
    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final S @NotNull ... array) {
      return !isIn(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isIn(@NotNull final S @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final S @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@NotNull final S @NotNull [] array) {
      return isIn(List.<@NotNull S>of(array));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Stream<? extends @NotNull S> elements)
    {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Stream<? extends @NotNull S> elements)
    {
      return elements.anyMatch(p -> EqualableEnum.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Collection<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<@NotNull S> elements) {
      return !elements.isEmpty() && isIn(EnumSet.<@NotNull S>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final EnumSet<@NotNull S> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<@NotNull S> elements) {
      if (null == s) {
        return false;
      }
      return elements.contains(s);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="3.4. new Java Collection API">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="3.5. Comparable<EqualableString> interface">

    //</editor-fold>
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. class EqualableString implements Equalable<@NotNull EqualableString>">

  /**
   * Specialized class for handling Strings.
   * In theory, you could get away with utilizing this class by using static methods of {@code Holder<String>} interface,
   * however {@code String} has more logic, than the {@code Holder<S>} class can fit.
   * Example method is {@code .isEqualToIgnoreCase()}, which could not be added to the interface of {@code Holder<S>}
   * because not every object is case-sensitive. {@code EqualableString} class is merely a convenience class that provides some
   * utility methods for {@code String} objects and a refreshed {@code String} API, {@code Stream}s and {@code Immutable Collection}.
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableString implements Comparable<@NotNull EqualableString>, Equalable<@NotNull EqualableString>, Not<@NotNull Equalable<@NotNull EqualableString>> {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static EqualableString blank()
    {
      return EqualableString.BLANK;
    }

    @NotNull
    @Unmodifiable
    public static final EqualableString BLANK = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableString createConstant()
    {
      return EqualableString.of(Constants.BLANK);
    }

    //<editor-fold defaultstate="collapsed" desc="4.1. static utility methods">
    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static boolean areNotEqual(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqual(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return Equalable.<@NotNull EqualableString>areEqual(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static boolean areNotEqual(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areEqual(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqual(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areEqual(a, b);
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static boolean areNotEqualIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areEqualIgnoreCase(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqualIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return EqualableString.<@NotNull EqualableString>areTheSame(a, b) || EqualableString.<@NotNull EqualableString>bothAreBlank(a, b) || (a instanceof EqualableString stringA && stringA.isEqualToIgnoreCase(b));
    }

    @Contract(value = "null, !null -> true; !null, null -> true; null, null -> false", pure = true)
    public static boolean areNotEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areEqualIgnoreCase(a, b);
    }

    @Contract(value = "null, !null -> false; !null, null -> false; null, null -> true", pure = true)
    public static boolean areEqualIgnoreCase(@Nullable final String a, @Nullable final String b) {
      return EqualableString.<@NotNull String>areTheSame(a, b) || EqualableString.<@NotNull String>bothAreBlank(a, b) || (a != null && a.equalsIgnoreCase(b));
    }

    @Contract(value = "null, null -> true", pure = true)
    public static boolean bothAreNotBlank(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>bothAreBlank(a, b);
    }

    @Contract(value = "null, null -> false", pure = true)
    public static boolean bothAreBlank(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return a instanceof EqualableString stringA && stringA.isBlank() && b instanceof EqualableString stringB && stringB.isBlank();
    }

    @Contract(value = "null, null -> true", pure = true)
    public static boolean bothAreNotBlank(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>bothAreBlank(a, b);
    }

    @Contract(value = "null, null -> false", pure = true)
    public static boolean bothAreBlank(@Nullable final String a, @Nullable final String b) {
      return a != null && b != null && a.isBlank() && b.isBlank();
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return !EqualableString.<@NotNull EqualableString>areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(@Nullable final Equalable<@NotNull EqualableString> a, @Nullable final Equalable<@NotNull EqualableString> b) {
      return Equalable.<@NotNull EqualableString>areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(@Nullable final String a, @Nullable final String b) {
      return !EqualableString.<@NotNull String>areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(@Nullable final String a, @Nullable final String b) {
      return Equalable.<@NotNull String>areTheSame(a, b);
    }

    @NotNull
    @Unmodifiable
    @UnmodifiableView
    @Contract(value = "_ -> new", pure = true)
    public static EqualableString[] ofArray(@NotNull final String @NotNull [] array)
    {
      return Arrays.stream(array).map(EqualableString::of).toArray(EqualableString[]::new);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableString ofNullable(@Nullable final String s) {
      return EqualableString.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableString of(@NotNull final String s) {
      return EqualableString.element(Objects.requireNonNull(s));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableString element(@Nullable final String s)
    {
      return EqualableString.builder().plain(s).build();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isNotNullAndNotBlank(@Nullable final Equalable<@NotNull EqualableString> s) {
      return !EqualableString.<@NotNull EqualableString>isNullOrBlank(s);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean isNullOrBlank(@Nullable final Equalable<@NotNull EqualableString> s) {
      return null == s || s instanceof EqualableString string && string.isBlank();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isNotNullAndIsNotBlank(@Nullable final String s) {
      return isNeitherNullNorBlank(s);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isNeitherNullNorBlank(@Nullable final String s) {
      return !EqualableString.<@NotNull String>isNullOrBlank(s);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean isNullOrBlank(@Nullable final String s) {
      return null == s || s.isBlank();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="4.2. implements EqualableHolder<String>">

    @Getter(AccessLevel.PRIVATE)
    @Nullable String plain;

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> this", pure = true)
    public EqualableString filter(@NotNull final Predicate<? super @NotNull String> predicate)
    {
      return ofNullable().filter(predicate).map(p -> this).orElse(EqualableString.blank());
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public <U> Optional<@Nullable U> flatMapToObj(@NotNull final Function<? super @NotNull String, ? extends @NotNull Optional<? extends @Nullable U>> mapper)
    {
      return ofNullable().flatMap(mapper);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public EqualableString map(@NotNull final Function<? super @NotNull String, @Nullable String> mapper)
    {
      return ofNullable().map(mapper).map(EqualableString::of).orElse(EqualableString.blank());
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public <U> Optional<@NotNull U> mapToObj(@NotNull final Function<? super String, ? extends U> mapper)
    {
      return ofNullable().map(mapper);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    public String orElseBlank()
    {
      return orElse(Constants.BLANK);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public String orElse(@NotNull final String other)
    {
      return ofNullable().orElse(other);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "_ -> new", pure = true)
    public String orElseGet(@NotNull final Supplier<? extends @NotNull String> supplier)
    {
      return ofNullable().orElseGet(supplier);
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    public String orElseThrow()
    {
      return isBlank() ? throwNpe() : orElseBlank();
    }

    @Contract(value = "-> fail", pure = true)
    private String throwNpe()
    {
      throw new NullPointerException("Equalable String is NULL.");
    }

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    public Optional<@Nullable String> ofNullable()
    {
      return Optional.ofNullable(plain);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableString> other) {
      return other instanceof EqualableString string && isEqualTo(getPlain(string));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final EqualableString other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final EqualableString other) {
      return isEqualTo(getPlain(other));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final String other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final String other) {
      return EqualableString.areEqual(plain, other);
    }

    @Contract(pure = true)
    public final boolean isNotEqualToIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean equalsIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean isEqualToIgnoreCase(@Nullable final Equalable<@NotNull EqualableString> other) {
      return isEqualToIgnoreCase(null == other ? null : getPlain(other));
    }

    @Contract(pure = true)
    public final boolean isNotEqualToIgnoreCase(@Nullable final String other) {
      return !isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean equalsIgnoreCase(@Nullable final String other) {
      return isEqualToIgnoreCase(other);
    }

    @Contract(pure = true)
    public final boolean isEqualToIgnoreCase(@Nullable final String other) {
      return EqualableString.<@NotNull String>areEqualIgnoreCase(plain, other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final Equalable<@NotNull EqualableString> other) {
      return !isSameAs(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final Equalable<@NotNull EqualableString> other) {
      return EqualableString.<@NotNull String>areTheSame(plain, null == other ? null : getPlain(other));
    }

    @NonNls
    @Nullable
    @Unmodifiable
    @Contract(pure = true)
    private static String getPlain(@NotNull final Equalable<@NotNull EqualableString> other) {
      return other instanceof EqualableString string ? getPlain(string) : null;
    }

    @NonNls
    @Nullable
    @Unmodifiable
    @Contract(pure = true)
    private static String getPlain(@Nullable final EqualableString other) {
      return null == other ? null : other.getPlain();
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final EqualableString other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final EqualableString other) {
      return Equalable.<@NotNull EqualableString>areTheSame(EqualableString.this, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final String other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final String other) {
      return EqualableString.<@NotNull String>areTheSame(plain, other);
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNotNullAndIsNotBlank() {
      return isNeitherNullNorBlank();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNeitherNullNorBlank() {
      return !isNullOrBlank();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNullOrBlank() {
      return isNull() || isBlank();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNotNull() {
      return !isNull();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNull() {
      return null == plain;
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isNotBlank() {
      return !isBlank();
    }

    @JsonIgnore
    @Contract(pure = true)
    public final boolean isBlank() {
      return Equalable.<@NotNull EqualableString>areTheSame(this, EqualableString.BLANK) || Equalable.<@NotNull String>areTheSame(plain, Constants.BLANK) || EqualableString.<@NotNull String>isNullOrBlank(plain);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return !isIn(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@NotNull final String @NotNull ... array) {
      return !isIn(array);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final String @NotNull ... array) {
      return isInArray(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return !isInIgnoreCase(array);
    }

    @SafeVarargs
    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull ... array) {
      return isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final String @NotNull ... array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final String @NotNull ... array) {
      return isInArrayIgnoreCase(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      return !isInArray(array);
    }

    @Override
    @Contract(pure = true)
    public final boolean isInArray(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      final List<@NotNull Equalable<@NotNull EqualableString>> list = Arrays.stream(array).toList();
      return isIn(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@NotNull final String @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@NotNull final String @NotNull [] array) {
      final List<@NotNull String> list = Arrays.stream(array).toList();
      return isInPlain(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArrayIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInArrayIgnoreCase(@NotNull final Equalable<@NotNull EqualableString> @NotNull [] array) {
      final List<@NotNull Equalable<@NotNull EqualableString>> list = Arrays.stream(array).toList();
      return isInIgnoreCase(list);
    }

    @Contract(pure = true)
    public final boolean isNotInArrayIgnoreCase(@NotNull final String @NotNull [] array) {
      return !isInArrayIgnoreCase(array);
    }

    @Contract(pure = true)
    public final boolean isInArrayIgnoreCase(@NotNull final String @NotNull [] array) {
      final List<@NotNull String> list = Arrays.stream(array).toList();
      return isInIgnoreCasePlain(list);
    }

    @Contract(pure = true)
    public final boolean doesNotWith(@NotNull final String prefix) {
      return !startsWith(prefix);
    }

    @Contract(pure = true)
    public final boolean startsWith(@NotNull final String prefix) {
      return mapToObj(p -> p.startsWith(prefix)).orElse(false);
    }

    @Contract(pure = true)
    public final boolean doesNotEndWith(@NotNull final String suffix) {
      return !endsWith(suffix);
    }

    @Contract(pure = true)
    public final boolean endsWith(@NotNull final String suffix) {
      return mapToObj(p -> p.endsWith(suffix)).orElse(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="4.3. old Java Collection API">
    @Override
    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isIn(elements);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isIn(Set.<@NotNull Equalable<@NotNull EqualableString>>copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final Collection<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Collection<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInPlain(Set.<@NotNull String>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Collection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInIgnoreCase(Set.<@NotNull Equalable<@NotNull EqualableString>>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final Collection<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final Collection<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInIgnoreCasePlain(Set.<@NotNull String>copyOf(elements));
    }

    @Override
    @Contract(pure = true)
    public boolean isNotIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isIn(elements);
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.contains(this);
    }

    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final Set<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Set<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.contains(plain);
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final Set<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> p instanceof EqualableString string && string.isEqualToIgnoreCase(plain));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final Set<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final Set<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> p.equalsIgnoreCase(plain));
    }

    @Override
    @Contract(pure = true)
    public final boolean isIn(@NotNull final Stream<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.anyMatch(p -> p instanceof EqualableString string && string.isEqualTo(plain));
    }

    @Contract(pure = true)
    public final boolean isNotInPlain(@NotNull final Stream<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final Stream<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.anyMatch(p -> EqualableString.areEqual(p, plain));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="4.4. new Immutables Collection API">
    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final ICollection<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final ICollection<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInPlain(ImmutableSet.<@NotNull String>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final ICollection<@NotNull Equalable<? extends @NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final ICollection<@NotNull Equalable<? extends @NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInIgnoreCase(ImmutableSet.<@NotNull Equalable<? extends @NotNull EqualableString>>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final ICollection<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final ICollection<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return !elements.isEmpty() && isInIgnoreCasePlain(ImmutableSet.<@NotNull String>copyOf(elements));
    }

    @Contract(pure = true)
    public boolean isNotIn(@NotNull final EqualableCollection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public boolean isIn(@NotNull final EqualableCollection<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.containsEqualable(this);
    }

    @Contract(pure = true)
    public boolean isNotInPlain(@NotNull final ImmutableSet<@NotNull String> elements) {
      return !isInPlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInPlain(@NotNull final ImmutableSet<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.contains(plain);
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCase(@NotNull final EqualableSet<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      return !isInIgnoreCase(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCase(@NotNull final EqualableSet<? extends @NotNull Equalable<@NotNull EqualableString>> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> p instanceof EqualableString string && string.isEqualToIgnoreCase(plain));
    }

    @Contract(pure = true)
    public final boolean isNotInIgnoreCasePlain(@NotNull final ImmutableSet<@NotNull String> elements) {
      return !isInIgnoreCasePlain(elements);
    }

    @Contract(pure = true)
    public final boolean isInIgnoreCasePlain(@NotNull final ImmutableSet<@NotNull String> elements) {
      if (null == plain) {
        return false;
      }
      return elements.stream().anyMatch(p -> p.equalsIgnoreCase(plain));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="4.5. Comparable<EqualableString> interface">

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
    public int compareTo(@NotNull final EqualableString o)
    {
      return orderAsc(this, o);
    }

    /**
     * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
     * reference instead of eagerly invoking a builder:
     *
     * <pre>
     *   {@code
     *     dtos.stream().min(EqualableString::orderAsc);
     *   }
     * </pre>
     * instead of:
     * <pre>
     *   {@code
     *     dtos.stream().min(EqualableString.orderAsc());
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
    public static int orderAsc(@Nullable final EqualableString a, @Nullable final EqualableString b)
    {
      return a == b ? 0 : a != null ? b != null ? orderAsc().compare(a, b) : -1 : 1;
    }

    /**
     * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
     * reference instead of eagerly invoking a builder:
     *
     * <pre>
     *   {@code
     *     dtos.stream().max(EqualableString::orderDesc);
     *   }
     * </pre>
     * instead of:
     * <pre>
     *   {@code
     *     dtos.stream().max(EqualableString.orderDesc());
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
    public static int orderDesc(@Nullable final EqualableString a, @Nullable final EqualableString b)
    {
      return a == b ? 0 : a != null ? b != null ? orderDesc().compare(a, b) : 1 : -1;
    }

    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull EqualableString> orderAsc()
    {
      return ORDER_BY_TEXT_COMPARATOR_ASCENDING;
    }

    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull EqualableString> orderDesc()
    {
      return ORDER_BY_TEXT_COMPARATOR_DESCENDING;
    }

    @NotNull
    private static final Comparator<@NotNull EqualableString> ORDER_BY_TEXT_COMPARATOR_ASCENDING = createComparatorAsc();

    @NotNull
    private static final Comparator<@NotNull EqualableString> ORDER_BY_TEXT_COMPARATOR_DESCENDING = createComparatorDesc();

    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull EqualableString> createComparatorAsc()
    {
      return Comparator.<@NotNull EqualableString>nullsLast(
        Comparator.comparing((EqualableString p) -> p.getPlain(), Comparator.nullsLast(Comparator.naturalOrder()))
          .thenComparingInt(EqualableString::length)
          .thenComparing(EqualableString::getClassName, Comparator.nullsLast(Comparator.naturalOrder()))
          .thenComparingInt(EqualableString::hashCode));
    }

    @Contract(pure = true)
    @SuppressWarnings(NULL)
    private int length()
    {
      return null == plain ? 0 : getPlain().length();
    }

    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull EqualableString> createComparatorDesc()
    {
      return Comparator.<@NotNull EqualableString>nullsFirst(
        Comparator.comparing((EqualableString p) -> p.getPlain(), Comparator.nullsFirst(Comparator.naturalOrder()))
          .thenComparingInt(EqualableString::length)
          .thenComparing(EqualableString::getClassName, Comparator.nullsFirst(Comparator.naturalOrder()))
          .thenComparingInt(EqualableString::hashCode));
    }

    @NonNls
    @NotNull
    @NotBlank
    @JsonIgnore
    @Unmodifiable
    @Contract(pure = true)
    public String getClassName()
    {
      return this.getClass().getName();
    }
    //</editor-fold>

    @Override
    @Nullable
    public String toString()
    {
      return plain;
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. class EqualableInteger implements Equalable<@NotNull EqualableInteger>">

  /**
   * Specialized class for holding primitive {@code int} values.
   * It is a replacement of {@code Holder<Integer>} class, which is unfortunately a big {@code @Nullable Integer}.
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableInteger implements Equalable<@NotNull EqualableInteger> {//, Not<@NotNull Equalable<@NotNull EqualableInteger>> {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static EqualableInteger empty() {
      return EMPTY;
    }

    @NotNull
    @Unmodifiable
    public static final EqualableInteger EMPTY = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableInteger createConstant()
    {
      return EqualableInteger.of(0);
    }

    //<editor-fold defaultstate="collapsed" desc="5.1. static utility methods">
    @Contract(pure = true)
    public static boolean areNotEqual(final int a, final int b) {
      return !Equalable.EqualableInteger.areEqual(a, b);
    }

    @Contract(pure = true)
    public static boolean areEqual(final int a, final int b) {
      return Equalable.EqualableInteger.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(final int a, final int b) {
      return !Equalable.EqualableInteger.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(final int a, final int b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableInteger integer(final int s)
    {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableInteger parseInt(@NotNull final String s)
    {
      return EqualableInteger.of(Integer.parseInt(s));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableInteger of(final int s)
    {
      return EqualableInteger.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableInteger element(final int s)
    {
      return EqualableInteger.builder().s(s).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="5.2. implements EqualableHolder<int>">

    int s;

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableInteger> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableInteger> other) {
      if (null == other) {
        return false;
      }
      return other instanceof EqualableInteger equalableInteger && isEqualTo(getS(equalableInteger));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final EqualableInteger other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final EqualableInteger other) {
      return null != other && isEqualTo(getS(other));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final int other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final int other) {
      return Equalable.EqualableInteger.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final EqualableInteger other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final EqualableInteger other) {
      return Equalable.<@NotNull EqualableInteger>areTheSame(EqualableInteger.this, other);
    }

    @NonNls
    @Unmodifiable
    @Contract(pure = true)
    private static int getS(@NotNull final Equalable<@NotNull EqualableInteger> other) {
      return other instanceof EqualableInteger equalableInteger ? getS(equalableInteger) : 0;
    }

    @NonNls
    @Unmodifiable
    @Contract(pure = true)
    private static int getS(@NotNull final EqualableInteger other) {
      return other.getS();
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final int other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final int other) {
      return Equalable.EqualableInteger.areTheSame(s, other);
    }

    @Contract(pure = true)
    public final boolean isBetweenInclusive(final int lowerBoundary, final int higherBoundary) {
      return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
    }

    @Contract(pure = true)
    public final boolean isBetweenExclusive(final int lowerBoundary, final int higherBoundary) {
      return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
    }

    @Contract(pure = true)
    public final boolean isNotZero() {
      return !isZero();
    }

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableInteger.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableInteger.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableInteger.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final int other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final int other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final int other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final int other) {
      return s <= other;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="5.3. old Java Collection API">
    @Contract(pure = true)
    public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull ... array) {
      return !isIn(array);
    }

    @Contract(pure = true)
    public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final int @NotNull [] array) {
      final List<java.lang.@NotNull Integer> list = IntStream.of(array).boxed().toList();
      return isInCollection(list);
    }

    @Contract(pure = true)
    public final boolean isNotIn(@Nullable final Collection<? extends @NotNull Equalable<@NotNull EqualableInteger>> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@Nullable final Collection<? extends @NotNull Equalable<@NotNull EqualableInteger>> elements) {
      if (null == elements) {
        return false;
      }
      return !elements.isEmpty() && isIn(Set.<@NotNull Equalable<@NotNull EqualableInteger>>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotIn(@Nullable final Stream<? extends @NotNull Equalable<@NotNull EqualableInteger>> elements) {
      return !isIn(elements);
    }

    @Contract(pure = true)
    public final boolean isIn(@Nullable final Stream<? extends @NotNull Equalable<@NotNull EqualableInteger>> elements) {
      if (null == elements) {
        return false;
      }
      return elements.anyMatch(p -> p instanceof EqualableInteger equalableInteger && equalableInteger.isEqualTo(s));
    }

    @Contract(pure = true)
    public final boolean isNotInCollection(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
      return !isInCollection(elements);
    }

    @Contract(pure = true)
    public final boolean isInCollection(@NotNull final Collection<java.lang.@NotNull Integer> elements) {
      return !elements.isEmpty() && isInCollection(Set.<java.lang.@NotNull Integer>copyOf(elements));
    }

    @Contract(pure = true)
    public final boolean isNotInCollection(@NotNull final Set<java.lang.@NotNull Integer> elements) {
      return !isInCollection(elements);
    }

    @Contract(pure = true)
    public final boolean isInCollection(@NotNull final Set<java.lang.@NotNull Integer> elements) {
      return elements.contains(s);
    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final IntStream elements) {
      return elements.anyMatch(p -> EqualableInteger.areTheSame(p, s));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="5.4. new Java Collection API">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="5.5. Comparable<EqualableInteger> interface">

    //</editor-fold>
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. class EqualableLong implements Equalable<@NotNull EqualableLong>">
  /**
   * Specialized class for holding primitive {@code} long values.
   * It is a replacement of {@code Holder<Long>} class, which is unfortunately a big {@code @Nullable} Long.
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableLong implements Equalable<@NotNull EqualableLong> {//, Not<@NotNull Equalable<@NotNull EqualableLong>> {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static EqualableLong empty() {
      return EMPTY;
    }

    @NotNull
    @Unmodifiable
    public static final EqualableLong EMPTY = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableLong createConstant()
    {
      return EqualableLong.of(0L);
    }

    //<editor-fold defaultstate="collapsed" desc="6.1. static utility methods">
    @Contract(pure = true)
    public static boolean areNotEqual(final long a, final long b) {
      return !EqualableLong.areEqual(a, b);
    }

    @Contract(pure = true)
    public static boolean areEqual(final long a, final long b) {
      return EqualableLong.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(final long a, final long b) {
      return !EqualableLong.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(final long a, final long b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableLong parseLong(@NotNull final String s)
    {
      return EqualableLong.of(Long.parseLong(s));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableLong of(final long s)
    {
      return EqualableLong.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableLong element(final long s)
    {
      return EqualableLong.builder().s(s).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="6.2. implements EqualableHolder<long>">

    long s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableLong.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableLong.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableLong.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final long other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final long other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final long other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final long other) {
      return s <= other;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="6.3. old Java Collection API">
//    @Contract(pure = true)
//    public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull ... array) {
//      return !isIn(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull ... array) {
//      return isInArray(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
//      return !isInArray(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final long @NotNull [] array) {
//      final List<java.lang.Long> list = LongStream.of(array).boxed().toList();
//      return isIn(list);
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
//      return !isIn(elements);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Long> elements) {
//      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Long>copyOf(elements));
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
//      return !isIn(elements);
//    }

//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Long> elements) {
//      return elements.contains(s);
//    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final LongStream elements) {
      return elements.anyMatch(p -> EqualableLong.areTheSame(p, s));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final long other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final long other) {
      return EqualableLong.areEqual(s, other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableLong> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableLong> other) {
      return other instanceof EqualableLong equalableLong && isEqualTo(getS(equalableLong));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final EqualableLong other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final EqualableLong other) {
      return isEqualTo(null == other ? null : getS(other));
    }

    public long getS(@Nullable final EqualableLong holder) {
      return null == holder ? 0 : holder.getS();
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final long other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final long other) {
      return EqualableLong.areTheSame(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final EqualableLong other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final EqualableLong other) {
      return Equalable.<@NotNull EqualableLong>areTheSame(EqualableLong.this, other);
    }

    @Contract(pure = true)
    public final boolean isBetweenInclusive(final long lowerBoundary, final long higherBoundary) {
      return isGreaterThanOrEqualTo(lowerBoundary) && isLessThanOrEqualTo(higherBoundary);
    }

    @Contract(pure = true)
    public final boolean isBetweenExclusive(final long lowerBoundary, final long higherBoundary) {
      return isGreaterThan(lowerBoundary) && isLessThan(higherBoundary);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="6.4. new Java Collection API">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="6.5. Comparable<EqualableLong> interface">

    //</editor-fold>
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="7. class EqualableFloat implements Equalable<@NotNull EqualableFloat>">
  /**
   * Specialized class for holding primitive {@code float} values.
   * It is a replacement of {@code Holder<Float>} class, which is unfortunately a big {@code @Nullable} Float.
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableFloat implements Equalable<@NotNull EqualableFloat> {//, Not<@NotNull Equalable<@NotNull EqualableFloat>> {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static EqualableFloat empty() {
      return EMPTY;
    }

    @NotNull
    @Unmodifiable
    public static final EqualableFloat EMPTY = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableFloat createConstant()
    {
      return EqualableFloat.of(0f);
    }

    //<editor-fold defaultstate="collapsed" desc="7.1. static utility methods">
    @Contract(pure = true)
    public static boolean areNotEqual(final float a, final float b) {
      return !EqualableFloat.areEqual(a, b);
    }

    @Contract(pure = true)
    public static boolean areEqual(final float a, final float b) {
      return EqualableFloat.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(final float a, final float b) {
      return !EqualableFloat.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(final float a, final float b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableFloat parseFloat(@NotNull final String s)
    {
      return EqualableFloat.of(Float.parseFloat(s));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableFloat of(final float s)
    {
      return EqualableFloat.element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableFloat element(final float s)
    {
      return EqualableFloat.builder().s(s).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="7.2. implements EqualableHolder<float>">

    float s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableFloat.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableFloat.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableFloat.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final float other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final float other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final float other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final float other) {
      return s <= other;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="7.3. old Java Collection API">
    @Contract(pure = true)
    public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull ... array) {
      return !isIn(array);
    }

    @Contract(pure = true)
    public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull ... array) {
      return isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] array) {
      return !isInArray(array);
    }

    @Contract(pure = true)
    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final float @NotNull [] floatArray) {
      DoubleStream ds = IntStream.range(0, floatArray.length)
        .mapToDouble(i -> floatArray[i]);
      return isIn(ds);
    }

//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
//      return !isIn(elements);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Float> elements) {
//      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Float>copyOf(elements));
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
//      return !isIn(elements);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Float> elements) {
//      return elements.contains(s);
//    }

    @Contract(pure = true)
    public final boolean isIn(@NotNull final DoubleStream elements) {
      return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
    }

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableFloat> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableFloat> other) {
      return other instanceof EqualableFloat equalableFloat && isEqualTo(getS(equalableFloat));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final EqualableFloat other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final EqualableFloat other) {
      return isEqualTo(null == other ? null : getS(other));
    }

    public float getS(@Nullable final EqualableFloat holder) {
      return null == holder ? 0 : holder.getS();
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final float other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final float other) {
      return EqualableFloat.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final EqualableFloat other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final EqualableFloat other) {
      return Equalable.<@NotNull EqualableFloat>areTheSame(EqualableFloat.this, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final float other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final float other) {
      return EqualableFloat.areTheSame(s, other);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="7.4. new Java Collection API">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="7.5. Comparable<EqualableFloat> interface">

    //</editor-fold>
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="8. class EqualableDouble implements Equalable<@NotNull EqualableDouble>">
  /**
   * Specialized class for holding primitive {@code double} values.
   * It is a replacement of {@code Holder<Double>} class, which is unfortunately a big {@code @Nullable Double}.
   */
  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class EqualableDouble implements Equalable<@NotNull EqualableDouble> {//, Not<@NotNull Equalable<@NotNull EqualableDouble>> {

    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static EqualableDouble empty() {
      return EMPTY;
    }

    @NotNull
    @Unmodifiable
    public static final EqualableDouble EMPTY = createConstant();

    @NotNull
    @Unmodifiable
    @Contract(value = "-> new", pure = true)
    private static EqualableDouble createConstant()
    {
      return EqualableDouble.of(0d);
    }

    //<editor-fold defaultstate="collapsed" desc="8.1. static utility methods">
    @Contract(pure = true)
    public static boolean areNotEqual(final double a, final double b) {
      return !EqualableDouble.areEqual(a, b);
    }

    @Contract(pure = true)
    public static boolean areEqual(final double a, final double b) {
      return EqualableDouble.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areNotTheSame(final double a, final double b) {
      return !EqualableDouble.areTheSame(a, b);
    }

    @Contract(pure = true)
    public static boolean areTheSame(final double a, final double b) {
      return a == b;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Equalable.EqualableDouble parseDouble(@NotNull final String s)
    {
      return EqualableDouble.of(Double.parseDouble(s));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static EqualableDouble of(final double s)
    {
      return element(s);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static EqualableDouble element(final double s)
    {
      return EqualableDouble.builder().s(s).build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="8.2. implements EqualableHolder<double>">

    double s;

    @Contract(pure = true)
    public final boolean isZero() {
      return EqualableDouble.areTheSame(s, 0);
    }

    @Contract(pure = true)
    public final boolean isOne() {
      return EqualableDouble.areTheSame(s, 1);
    }

    @Contract(pure = true)
    public final boolean isTwo() {
      return EqualableDouble.areTheSame(s, 2);
    }

    @Contract(pure = true)
    public final boolean isNegativeOrZero() {
      return isLessThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isNegative() {
      return isLessThanZero();
    }

    @Contract(pure = true)
    public final boolean isPositiveOrZero() {
      return isGreaterThanOrEqualToZero();
    }

    @Contract(pure = true)
    public final boolean isPositive() {
      return isGreaterThanZero();
    }

    @Contract(pure = true)
    public final boolean isGreaterThanZero() {
      return isGreaterThan(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualToZero() {
      return isGreaterThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanZero() {
      return isLessThan(0);
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualToZero() {
      return isLessThanOrEqualTo(0);
    }

    @Contract(pure = true)
    public final boolean isGreaterThan(final double other) {
      return s > other;
    }

    @Contract(pure = true)
    public final boolean isGreaterThanOrEqualTo(final double other) {
      return s >= other;
    }

    @Contract(pure = true)
    public final boolean isLessThan(final double other) {
      return s < other;
    }

    @Contract(pure = true)
    public final boolean isLessThanOrEqualTo(final double other) {
      return s <= other;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="8.3. old Java Collection API">
//    @Contract(pure = true)
//    public final boolean isNotIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull ... array) {
//      return !isIn(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull ... array) {
//      return isInArray(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
//      return !isInArray(array);
//    }
//
//    @Contract(pure = true)
//    public final boolean isInArray(@SuppressWarnings(NULLABLE_PROBLEMS) @NotNull final double @NotNull [] array) {
//      final List<java.lang.@NotNull Double> list = DoubleStream.of(array).boxed().toList();
//      return isIn(list);
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
//      return !isIn(elements);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Collection<java.lang.@NotNull Double> elements) {
//      return !elements.isEmpty() && isIn(Set.<java.lang.@NotNull Double>copyOf(elements));
//    }
//
//    @Contract(pure = true)
//    public final boolean isNotIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
//      return !isIn(elements);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final Set<java.lang.@NotNull Double> elements) {
//      return elements.contains(s);
//    }
//
//    @Contract(pure = true)
//    public final boolean isIn(@NotNull final DoubleStream elements) {
//      return elements.anyMatch(p -> EqualableDouble.areTheSame(p, s));
//    }

    @Override
    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final Equalable<@NotNull EqualableDouble> other) {
      return !isEqualTo(other);
    }

    @Override
    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final Equalable<@NotNull EqualableDouble> other) {
      return other instanceof EqualableDouble equalableDouble && isEqualTo(getS(equalableDouble));
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(@Nullable final EqualableDouble other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(@Nullable final EqualableDouble other) {
      return isEqualTo(null == other ? null : getS(other));
    }

    public double getS(@Nullable final EqualableDouble holder) {
      return null == holder ? 0 : holder.getS();
    }

    @Contract(pure = true)
    public final boolean isNotEqualTo(final double other) {
      return !isEqualTo(other);
    }

    @Contract(pure = true)
    public final boolean isEqualTo(final double other) {
      return EqualableDouble.areEqual(s, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(@Nullable final EqualableDouble other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(@Nullable final EqualableDouble other) {
      return Equalable.<@NotNull EqualableDouble>areTheSame(EqualableDouble.this, other);
    }

    @Contract(pure = true)
    public final boolean isNotSameAs(final double other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public final boolean isSameAs(final double other) {
      return EqualableDouble.areTheSame(s, other);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="8.4. new Java Collection API">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="8.5. Comparable<EqualableDouble> interface">

    //</editor-fold>
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="9. enum Dummy">
  @NotNull
  @Unmodifiable
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  @Contract(pure = true)
  static <S> IntFunction<@NotNull S @NotNull []> defaultKey()
  {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  @Unmodifiable
  IntFunction<Equalable.@NotNull Dummy @NotNull []> DEFAULT_KEY = createConstantKey();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static IntFunction<Equalable.@NotNull Dummy @NotNull []> createConstantKey()
  {
    return Equalable.Dummy @NotNull []::new;
  }

  @Unmodifiable
  enum Dummy implements Comparable<@NotNull Dummy>, Equalable<@NotNull Dummy>
  {
    DUMMY_ENUM_ITEM
  }
  //</editor-fold>
}
