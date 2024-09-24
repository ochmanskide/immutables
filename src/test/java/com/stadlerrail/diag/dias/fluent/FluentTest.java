package com.stadlerrail.diag.dias.fluent;

import annotations.UnitTest;
import com.stadlerrail.diag.dias.immutables.equalable.Equalable;
import com.stadlerrail.diag.dias.immutables.fluent.Fluent;
import com.stadlerrail.diag.dias.immutables.fluent.FluentEnumSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FluentTest
{

  @Test
  void isInArrayEmpty()
  {
    final State[] array = new State[0];
    final boolean actual = FluentTest.State.PENDING.isIn(array);
    assertThat(actual).isFalse();

    final boolean negation = FluentTest.State.PENDING.isNotIn(array);
    assertThat(negation).isTrue();
  }

  @Test
  void isInEmpty()
  {
    List<FluentTest.State> list = List.of();
    final boolean actual = FluentTest.State.PENDING.isIn(list);
    assertThat(actual).isFalse();

    final boolean negation = FluentTest.State.PENDING.isNotIn(list);
    assertThat(negation).isTrue();
  }

  @Test
  void isInRange()
  {
    final boolean actual = FluentTest.State.PENDING.isInRange(FluentTest.State.UNKNOWN, FluentTest.State.OK);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotInRange(FluentTest.State.UNKNOWN, FluentTest.State.OK);
    assertThat(negation).isFalse();
  }

  @Test
  void isIn()
  {
    final boolean actual = FluentTest.State.PENDING.isIn(FluentTest.State.UNKNOWN, FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotIn(FluentTest.State.UNKNOWN, FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR);
    assertThat(negation).isFalse();
  }

  @Test
  void isInArray()
  {
    final FluentTest.State[] array = new FluentTest.State[] { FluentTest.State.UNKNOWN, FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR };
    final boolean actual = FluentTest.State.PENDING.isIn(array);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotIn(array);
    assertThat(negation).isFalse();
  }

  @Test
  void testIsIn()
  {
    final List<FluentTest.State> collection = List.of(FluentTest.State.UNKNOWN, FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR);
    final boolean actual = FluentTest.State.PENDING.isIn(collection);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotIn(collection);
    assertThat(negation).isFalse();
  }

  @Test
  void isInCollection()
  {
    final Set<FluentTest.State> enumSet = EnumSet.of(FluentTest.State.UNKNOWN, FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR);
    final boolean actual = FluentTest.State.PENDING.isIn(enumSet);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotIn(enumSet);
    assertThat(negation).isFalse();
  }

  @Test
  void isInEnumSet()
  {
    final EnumSet<FluentTest.State> enumSet = EnumSet.of(
      FluentTest.State.UNKNOWN,
      FluentTest.State.PENDING,
      FluentTest.State.PENDING,
      FluentTest.State.ERROR);
    final boolean actual = FluentTest.State.PENDING.isIn(enumSet);
    assertThat(actual).isTrue();

    final boolean negation = FluentTest.State.PENDING.isNotIn(enumSet);
    assertThat(negation).isFalse();
  }

  @Test
  void isEqual01() {
    final State a = State.UNKNOWN;
    final State b = State.PENDING;
    final boolean actual = a.isEqualTo(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isEqualTo(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isSame01() {
    final State a = State.UNKNOWN;
    final State b = State.PENDING;
    final boolean actual = a.isSameAs(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isSameAs(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isEqual02() {
    final Fluent<@NotNull State> a = State.UNKNOWN;
    final Fluent<@NotNull State> b = State.PENDING;
    final boolean actual = a.isEqualTo(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isEqualTo(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isSame02() {
    final Fluent<@NotNull State> a = State.UNKNOWN;
    final Fluent<@NotNull State> b = State.PENDING;
    final boolean actual = a.isSameAs(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isSameAs(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isEqual03() {
    final Equalable<@NotNull Fluent<@NotNull State>> a = State.UNKNOWN;
    final Equalable<@NotNull Fluent<@NotNull State>> b = State.PENDING;
    final boolean actual = a.isEqualTo(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isEqualTo(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isSame03() {
    final Equalable<@NotNull Fluent<@NotNull State>> a = State.UNKNOWN;
    final Equalable<@NotNull Fluent<@NotNull State>> b = State.PENDING;
    final boolean actual = a.isSameAs(b);
    assertThat(actual).isFalse();
    final boolean actual2 = b.isSameAs(a);
    assertThat(actual2).isFalse();
  }

  @Test
  void isNotEqual01() {
    final State a = State.UNKNOWN;
    final State b = State.PENDING;
    final boolean actual = a.isNotEqualTo(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotEqualTo(a);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotSame01() {
    final State a = State.UNKNOWN;
    final State b = State.PENDING;
    final boolean actual = a.isNotSameAs(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotSameAs(a);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotEqual02() {
    final Fluent<@NotNull State> a = State.UNKNOWN;
    final Fluent<@NotNull State> b = State.PENDING;
    final boolean actual = a.isNotEqualTo(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotEqualTo(a);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotSame02() {
    final Fluent<@NotNull State> a = State.UNKNOWN;
    final Fluent<@NotNull State> b = State.PENDING;
    final boolean actual = a.isNotSameAs(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotSameAs(a);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotEqual03() {
    final Equalable<@NotNull Fluent<@NotNull State>> a = State.UNKNOWN;
    final Equalable<@NotNull Fluent<@NotNull State>> b = State.PENDING;
    final boolean actual = a.isNotEqualTo(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotEqualTo(a);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotSame03() {
    final Equalable<@NotNull Fluent<@NotNull State>> a = State.UNKNOWN;
    final Equalable<@NotNull Fluent<@NotNull State>> b = State.PENDING;
    final boolean actual = a.isNotSameAs(b);
    assertThat(actual).isTrue();
    final boolean actual2 = b.isNotSameAs(a);
    assertThat(actual2).isTrue();
  }

  //<editor-fold defaultstate="collapsed" desc="enum State">
  @Getter
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  enum State implements Fluent<@NotNull State>, Comparable<@NotNull State>
  {
    ERROR(0),
    UNKNOWN(1),
    PENDING(2),
    OK(3);

    @PositiveOrZero
    @MagicConstant(intValues = { 0, 1, 2, 3 })
    @Min(value = 0, message = "Priority should not be less than {value}. Actual value: ${validatedValue}")
    int priority;

    //<editor-fold defaultstate="collapsed" desc="Cache entries">

    /**
     * Load and store values eagerly, for performance boost.
     */
    @NotNull
    private static final State @NotNull [] ENTRIES = State.values();

    @Getter
    @NotNull
    @Unmodifiable
    @UnmodifiableView
    private static final FluentEnumSet<@NotNull State> entries = FluentEnumSet.<@NotNull State>allOf(State[]::new);

    @NotNull
    @Unmodifiable
    @UnmodifiableView
    @Contract(value = "-> new", pure = true)
    public static Stream<@NotNull State> sortedStream()
    {
      return entries.stream().sorted(State::orderByOrdinalNullsLast);
    }

    @NotNull
    @Unmodifiable
    @UnmodifiableView
    @Contract(value = "-> new", pure = true)
    public static Stream<@NotNull State> stream()
    {
      return entries.stream();
    }

    @Contract(pure = true)
    public static void forEach(@NotNull final Consumer<? super @NotNull State> consumer)
    {
      entries.forEach(consumer);
    }

    @Contract(pure = true)
    public static void forEachOrdered(@NotNull final Consumer<? super @NotNull State> consumer)
    {
      entries.forEachOrdered(consumer, orderByOrdinalComparatorAsc());
    }

    @Contract(pure = true)
    public static int count()
    {
      return ENTRIES.length;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Comparable<State>">

    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @Contract(pure = true)
    public static int orderByOrdinalNullsLast(@Nullable final State a, @Nullable final State b)
    {
      if(a == b)
      {
        return 0;
      }
      return a != null ? b != null ? orderByOrdinalComparatorAsc().compare(a, b) : -1 : 1;
    }

    /**
     * <p>Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @Contract(pure = true)
    public static int orderByOrdinalNullsFirst(@Nullable final State a, @Nullable final State b)
    {
      if(a == b)
      {
        return 0;
      }
      return a != null ? b != null ? orderByOrdinalComparatorDesc().compare(a, b) : 1 : -1;
    }

    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull State> orderByOrdinalComparatorAsc()
    {
      return ORDER_BY_ORDINAL_COMPARATOR_ASCENDING;
    }

    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull State> orderByOrdinalComparatorDesc()
    {
      return ORDER_BY_ORDINAL_COMPARATOR_DESCENDING;
    }

    @NotNull
    private static final Comparator<@NotNull State> ORDER_BY_ORDINAL_COMPARATOR_ASCENDING = createOrderByOrdinalComparatorAsc();

    @NotNull
    private static final Comparator<@NotNull State> ORDER_BY_ORDINAL_COMPARATOR_DESCENDING = createOrderByOrdinalComparatorDesc();

    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull State> createOrderByOrdinalComparatorAsc()
    {
      return Comparator.nullsLast(
        Comparator.comparingInt(State::ordinal));
    }

    /**
     * <p>Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull State> createOrderByOrdinalComparatorDesc()
    {
      return Comparator.nullsFirst(
        Comparator.comparingInt(State::ordinal));
    }
    //</editor-fold>
  }
  //</editor-fold>
}
