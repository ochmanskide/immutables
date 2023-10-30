package de.ochmanski.immutables.immutable.enums;

import de.ochmanski.immutables.fluent.Fluent;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImmutableEnumSetTest
{

  @Test
  void of()
  {
    assertThatThrownBy(ImmutableEnumSet::of)
        .isExactlyInstanceOf(UnsupportedOperationException.class)
        .hasMessage("Please pass array generator type to the method. "
            + "For example: ImmutableEnumSet.ofGenerator(Day[]::new)");
  }

  @Test
  void ofGenerator()
  {
    final @NotNull IntFunction<@NotNull Dummy[]> constructor = Dummy[]::new;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.ofGenerator(constructor);
    Assertions.assertThat(actual.isEmpty()).isTrue();
    Assertions.assertThat(actual.size()).isZero();
    Assertions.assertThat(actual.getSet().unwrap()).isEmpty();
    Assertions.assertThat(actual.toArray()).isEmpty();
    Assertions.assertThat(actual.toArray().getClass().getComponentType()).isEqualTo(Dummy.class);
    final List<Dummy> expected = Arrays.asList(Dummy.values());
    final Object[] enumConstants = actual.toArray().getClass().getComponentType().getEnumConstants();
    Assertions.assertThat(enumConstants).containsExactlyElementsOf(expected);
  }

  @Test
  void of1()
  {
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(Dummy.A, Dummy[]::new);
    Assertions.assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A);
  }

  @Test
  void of2()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.of(s1, s2, Dummy[]::new);
    Assertions.assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    Assertions.assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B);
  }

  @Test
  void of3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.of(s1, s2, s3, Dummy[]::new);
    Assertions.assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    Assertions.assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void ofArray3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    Assertions.assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    Assertions.assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void toArrayNull()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy) null, Dummy[]::new))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy s1 = Dummy.A;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
    Assertions.assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null");
  }

  @Test
  void toArrayNullClass()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.ofGenerator(null)).isInstanceOfAny(NullPointerException.class);
  }

  @Test
  void toArrayClass()
  {
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
    Assertions.assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy) null, Dummy[]::new))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
  }

  @Test
  void toArray1()
  {
    final Dummy s1 = Dummy.A;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.of(s1, Dummy[]::new);
    Assertions.assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s2, Dummy[]::new);
    Assertions.assertThat(actual.toArray()).containsExactly(s1, s2);
  }

  @Test
  void toArray3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    Assertions.assertThat(actual.toArray()).containsExactly(s1, s2, s3);
  }

  @Test
  void equalable()
  {
    final Dummy a = Dummy.A;
    final Dummy b = Dummy.A;
    Assertions.assertThat(a).isEqualTo(b);
    Assertions.assertThat(a.isEqualTo(b)).isTrue();
    Assertions.assertThat(a.equals(b)).isTrue();
    final Dummy c = Dummy.C;
    Assertions.assertThat(a).isNotEqualTo(c);
    Assertions.assertThat(a.isEqualTo(c)).isFalse();
    Assertions.assertThat(a.equals(c)).isFalse();
  }

  private enum Dummy implements Fluent<Dummy>
  {
    A,
    B,
    C;
  }

}
