package de.ochmanski.immutables;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

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
        .hasMessage("sadfa");
  }

  @Test
  void ofGenerator()
  {
    final @NotNull IntFunction<@NotNull Dummy[]> constructor = Dummy[]::new;
    final ImmutableEnumSet<Dummy> actual = ImmutableEnumSet.ofGenerator(constructor);
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.size()).isZero();
    assertThat(actual.getSet()).isEmpty();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isExactlyInstanceOf(Dummy.class);
  }

  @Test
  void testOf()
  {
    //when(dependency.testOf(any())).thenReturn(dummy());
    //actual = unitUnderTest.testOf();
    //expected = "expectedValue";
    //assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testOf1()
  {
    //when(dependency.testOf1(any())).thenReturn(dummy());
    //actual = unitUnderTest.testOf1();
    //expected = "expectedValue";
    //assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testOf2()
  {
    //when(dependency.testOf2(any())).thenReturn(dummy());
    //actual = unitUnderTest.testOf2();
    //expected = "expectedValue";
    //assertThat(actual).isEqualTo(expected);
  }

  @Test
  void testOf3()
  {
    //when(dependency.testOf3(any())).thenReturn(dummy());
    //actual = unitUnderTest.testOf3();
    //expected = "expectedValue";
    //assertThat(actual).isEqualTo(expected);
  }

  @Test
  void toArray()
  {
    //when(dependency.toArray(any())).thenReturn(dummy());
    //actual = unitUnderTest.toArray();
    //expected = "expectedValue";
    //assertThat(actual).isEqualTo(expected);
  }

  private enum Dummy implements Equalable<Dummy>
  {
    A,
    B,
    C;
  }

}
