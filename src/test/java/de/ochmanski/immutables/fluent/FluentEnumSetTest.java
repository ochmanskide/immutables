package de.ochmanski.immutables.fluent;

import annotations.UnitTest;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
//@ExtendWith(MockitoExtension.class)
class FluentEnumSetTest
{

  @Test
  void of()
  {
    assertThatThrownBy(FluentEnumSet::of)
      .isExactlyInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Please pass array generator type to the method. "
        + "For example: FluentEnumSet.ofGenerator(Day[]::new)");
  }

  @Test
  void ofArray()
  {
    final FluentExample e1 = FluentExample.A;
    final FluentExample e2 = FluentExample.A;
    final FluentExample e3 = FluentExample.B;
    final FluentExample[] enums = { e1, e2, e3 };
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.ofArray(enums, FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.A, FluentExample.B)
      .hasSize(2);
  }

  @Test
  void ofGivenClass()
  {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.<@NotNull FluentExample>ofGenerator(
      FluentExample[]::new);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray()).extracting(FluentExample::getClass).isEmpty();
    assertThat(actual.toArray()).extracting(FluentExample::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).extracting(Object::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).extracting(Object::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).hasSize(0);
    assertThat(actual.unwrap().toArray()).isNotNull();
    assertThat(actual.unwrap().toArray()).isEmpty();
    assertThat(actual.unwrap().toArray().getClass()).isNotNull();

  }

  @Test
  void ofElement()
  {
    final FluentEnumSet<@NotNull FluentExample> actual2 = FluentEnumSet.of(FluentExample.A,
      FluentExample[]::new);
    assertThat(actual2.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual2.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual2.toArray()).isNotEmpty();
    assertThat(actual2.toArray()).isNotNull();
    assertThat(actual2.toArray()).containsExactly(FluentExample.A);
    assertThat(actual2.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual2.toArray()).extracting(Object::getClass).containsExactly(new Class[] { FluentExample.class });
    assertThat(actual2.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual2.unwrap().toArray().getClass()).isSameAs(Object[].class);
    assertThat(actual2.unwrap().toArray().getClass().getComponentType()).isSameAs(Object.class);
    assertThat(actual2.unwrap().toArray().getClass().getComponentType()).isEqualTo(Object.class);
    assertThat(actual2.unwrap().toArray()).isNotNull();
    assertThat(actual2.unwrap().toArray()).isNotEmpty();
    assertThat(actual2.unwrap().toArray()).containsExactly(FluentExample.A);
    assertThat(actual2.unwrap().toArray().getClass()).isSameAs(Object[].class);
  }

  @Test
  void of0() {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.<@NotNull FluentExample>ofGenerator(
      FluentExample[]::new);
    assertThat(actual.getClass()).isSameAs(FluentEnumSet.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual.unwrap().toArray()).isNotNull();
    assertThat(actual.unwrap().toArray()).isEmpty();
    assertThat(actual.unwrap().toArray().getClass()).isSameAs(Object[].class);
  }

  @Test
  void of1()
  {
    final FluentExample e1 = FluentExample.A;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.A)
      .hasSize(1)
      .containsExactlyElementsOf(actual.unwrap());
  }

  @Test
  void of2()
  {
    final FluentExample e1 = FluentExample.A;
    final FluentExample e2 = FluentExample.A;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.A)
      .hasSize(1)
      .containsExactlyElementsOf(actual.unwrap());
  }

  @Test
  void of3()
  {
    final FluentExample e1 = FluentExample.A;
    final FluentExample e2 = FluentExample.A;
    final FluentExample e3 = FluentExample.B;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2, e3,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.A, FluentExample.B)
      .hasSize(2)
      .containsExactlyInAnyOrderElementsOf(actual.unwrap());
  }

  @Test
  void of4()
  {
    final FluentExample e1 = FluentExample.A;
    final FluentExample e2 = FluentExample.A;
    final FluentExample e3 = FluentExample.B;
    final FluentExample e4 = FluentExample.C;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2, e3, e4,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.A, FluentExample.B, FluentExample.C)
      .hasSize(3)
      .containsExactlyInAnyOrderElementsOf(actual.unwrap());
  }

  @Test
  void ofGenerator() {
    final @NotNull IntFunction<@NotNull FluentExample[]> constructor = FluentExample[]::new;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.ofGenerator(constructor);
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.size()).isZero();
    assertThat(actual.getSet().unwrap()).isEmpty();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isEqualTo(FluentExample.class);
    final List<@NotNull FluentExample> expected = Arrays.asList(FluentExample.values());
    final Object[] enumConstants = actual.toArray().getClass().getComponentType().getEnumConstants();
    assertThat(enumConstants).containsExactlyElementsOf(expected);
  }

  @Test
  void of5() {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(FluentExample.A, FluentExample[]::new);
    assertThat(actual).isInstanceOf(FluentEnumSet.class);
    assertThat(actual.unwrap()).containsExactly(FluentExample.A);
  }

  @Test
  void of6() {
    final FluentExample s1 = FluentExample.A;
    final FluentExample s2 = FluentExample.B;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, s2, FluentExample[]::new);
    assertThat(actual).isInstanceOf(FluentEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(FluentExample.A, FluentExample.B);
  }

  @Test
  void of7() {
    final FluentExample s1 = FluentExample.A;
    final FluentExample s2 = FluentExample.B;
    final FluentExample s3 = FluentExample.C;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, s2, s3, FluentExample[]::new);
    assertThat(actual).isInstanceOf(FluentEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(FluentExample.A, FluentExample.B, FluentExample.C);
  }

  @Test
  void ofArray3() {
    final FluentExample s1 = FluentExample.A;
    final FluentExample s2 = FluentExample.B;
    final FluentExample s3 = FluentExample.C;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, s1, s2, s3, FluentExample[]::new);
    assertThat(actual).isInstanceOf(FluentEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(FluentExample.A, FluentExample.B, FluentExample.C);
  }

  @Test
  void toArray4() {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.A,
      FluentExample.A,
      FluentExample.B,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.A, FluentExample.B).hasSize(2);
  }

  @Test
  void toArray5() {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.A,
      FluentExample.A,
      FluentExample.B,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.A, FluentExample.B).hasSize(2);
  }

  @Test
  void toArrayGenerator() {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.A,
      FluentExample.A,
      FluentExample.B,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.A, FluentExample.B).hasSize(2);
  }

  @Test
  void toArrayNull() {
    Assertions.assertThatThrownBy(() -> FluentEnumSet.of((FluentExample) null, FluentExample[]::new))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty() {
    final FluentExample s1 = FluentExample.A;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.ofGenerator(FluentExample[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArrayEmpty1() {
    final FluentExample s1 = FluentExample.A;
    Assertions.assertThatThrownBy(() -> FluentEnumSet.of(s1, null))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasMessage("Cannot invoke \"java.util.function.IntFunction.apply(int)\" because \"constructor\" is null");
  }

  @Test
  void toArrayEmpty2() {
    final FluentExample s1 = FluentExample.A;
    Assertions.assertThatThrownBy(() -> FluentEnumSet.of(s1, null, null))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty3() {
    final FluentExample s1 = FluentExample.A;
    Assertions.assertThatThrownBy(() -> FluentEnumSet.of(s1, null, null, null))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null");
  }

  @Test
  void toArrayNullClass() {
    Assertions.assertThatThrownBy(() -> FluentEnumSet.ofGenerator(null)).isInstanceOfAny(NullPointerException.class);
  }

  @Test
  void toArrayClass() {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.ofGenerator(FluentExample[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    Assertions.assertThatThrownBy(() -> FluentEnumSet.of((FluentExample) null, FluentExample[]::new))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
  }

  @Test
  void toArray1() {
    final FluentExample s1 = FluentExample.A;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, FluentExample[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final FluentExample s1 = FluentExample.A;
    final FluentExample s2 = FluentExample.B;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, s2, FluentExample[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2);
  }

  @Test
  void toArray3() {
    final FluentExample s1 = FluentExample.A;
    final FluentExample s2 = FluentExample.B;
    final FluentExample s3 = FluentExample.C;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(s1, s1, s2, s3, FluentExample[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2, s3);
  }

  @Test
  void equalable() {
    final FluentExample a = FluentExample.A;
    final FluentExample b = FluentExample.A;
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final FluentExample c = FluentExample.C;
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  private enum FluentExample implements Fluent<@NotNull FluentExample> {
    A,
    B,
    C;
  }
}
