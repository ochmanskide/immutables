package de.ochmanski.immutables.fluent;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FluentTest
{

  @Test
  void isInArrayEmpty() {
    final State[] array = new State[0];
    final boolean actual = FluentTest.State.PENDING.isIn(array);
    assertThat(actual).isFalse();

    final boolean negation = FluentTest.State.PENDING.isNotIn(array);
    assertThat(negation).isTrue();
  }

  @Test
  void isInEmpty() {
    List<FluentTest.State> list = List.of();
    final boolean actual = FluentTest.State.PENDING.isIn(list);
    assertThat(actual).isFalse();

    final boolean negation = FluentTest.State.PENDING.isNotIn(list);
    assertThat(negation).isTrue();
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

    /**
     * Load and store values eagerly, for performance boost.
     */
    @NotNull
    public static final State @NotNull [] ENTRIES = State.values();

    @NotNull
    @Contract(value = "-> new", pure = true)
    public static Stream<@NotNull State> stream()
    {
      return Arrays.<@NotNull State>stream(ENTRIES);
    }

    @Contract(pure = true)
    public static void forEach(@NotNull final Consumer<? super @NotNull State> consumer)
    {
      Arrays.<@NotNull State>asList(ENTRIES).forEach(consumer);
    }

    @Contract(pure = true)
    public boolean isNotError()
    {
      return !isError();
    }

    @Contract(pure = true)
    public boolean isError()
    {
      return isSameAs(ERROR);
    }

    @Contract(pure = true)
    public boolean isNotUnknown()
    {
      return !isUnknown();
    }

    @Contract(pure = true)
    public boolean isUnknown()
    {
      return isSameAs(UNKNOWN);
    }

    @Contract(pure = true)
    public boolean isNotPending()
    {
      return !isPending();
    }

    @Contract(pure = true)
    public boolean isPending()
    {
      return isSameAs(PENDING);
    }

    @Contract(pure = true)
    public boolean isNotOk()
    {
      return !isOk();
    }

    @Contract(pure = true)
    public boolean isOk()
    {
      return isSameAs(OK);
    }

    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    public static int orderByPriorityNullsLast(@Nullable final State a, @Nullable final State b)
    {
      if(a == b)
      {
        return 0;
      }
      return a != null ? b != null ? orderByPriorityComparatorAsc().compare(a, b) : -1 : 1;
    }

    /**
     * <p>Returns a null-friendly comparator that considers {@code null} to be
     * <p>less than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    public static int orderByPriorityNullsFirst(@Nullable final State a, @Nullable final State b)
    {
      if(a == b)
      {
        return 0;
      }
      return a != null ? b != null ? orderByPriorityComparatorDesc().compare(a, b) : 1 : -1;
    }

    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull State> orderByPriorityComparatorAsc()
    {
      return ORDER_BY_PRIORITY_COMPARATOR_ASCENDING;
    }

    /**
     * <p>Returns a null-friendly comparator that considers {@code null} to be
     * <p>less than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(pure = true)
    public static Comparator<@NotNull State> orderByPriorityComparatorDesc()
    {
      return ORDER_BY_PRIORITY_COMPARATOR_DESCENDING;
    }

    @NonNull
    @NotNull
    @javax.validation.constraints.NotNull
    private static final Comparator<@NotNull State> ORDER_BY_PRIORITY_COMPARATOR_ASCENDING = createOrderByPriorityComparatorAsc();

    @NonNull
    @NotNull
    @javax.validation.constraints.NotNull
    private static final Comparator<@NotNull State> ORDER_BY_PRIORITY_COMPARATOR_DESCENDING = createOrderByPriorityComparatorDesc();

    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * <p>greater than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull State> createOrderByPriorityComparatorAsc()
    {
      return Comparator.nullsLast(
        Comparator.comparingInt(State::getPriority)
          .thenComparingInt(State::ordinal));
    }

    /**
     * <p>Returns a null-friendly comparator that considers {@code null} to be
     * <p>less than non-null. When both are {@code null}, they are considered
     * <p>equal. If both are non-null, the specified {@code Comparator} is used
     * <p>to determine the order. If the specified comparator is {@code null},
     * <p>then the returned comparator considers all non-null values to be equal.
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    private static Comparator<@NotNull State> createOrderByPriorityComparatorDesc()
    {
      return Comparator.nullsFirst(
        Comparator.comparingInt(State::getPriority)
          .thenComparingInt(State::ordinal));
    }
  }
  //</editor-fold>

}
