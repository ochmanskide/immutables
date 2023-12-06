package de.ochmanski.immutables.immutable.enums;

import annotations.UnitTest;
import de.ochmanski.immutables.fluent.Fluent;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
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
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.ofGenerator(constructor);
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.size()).isZero();
    assertThat(actual.getSet().unwrap()).isEmpty();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isEqualTo(Dummy.class);
    final List<@NotNull Dummy> expected = Arrays.asList(Dummy.values());
    final Object[] enumConstants = actual.toArray().getClass().getComponentType().getEnumConstants();
    assertThat(enumConstants).containsExactlyElementsOf(expected);
  }

  @Test
  void of1()
  {
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(Dummy.A, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A);
  }

  @Test
  void of2()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s2, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B);
  }

  @Test
  void of3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void ofArray3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void toArrayNull()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
        .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy s1 = Dummy.A;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"java.util.function.IntFunction.apply(int)\" because \"constructor\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of de/ochmanski/immutables/fluent/FluentEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of de/ochmanski/immutables/fluent/FluentEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy s1 = Dummy.A;
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of de/ochmanski/immutables/fluent/FluentEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayNullClass()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.ofGenerator(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass()
  {
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.ofGenerator(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0()
  {
    Assertions.assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
//        .hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null");
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of de/ochmanski/immutables/fluent/FluentEnumSet.of must not be null")
      );
  }

  @Test
  void toArray1()
  {
    final Dummy s1 = Dummy.A;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s2, Dummy[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2);
  }

  @Test
  void toArray3()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2, s3);
  }

  @Test
  void equalable()
  {
    final Dummy a = Dummy.A;
    final Dummy b = Dummy.A;
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final Dummy c = Dummy.C;
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  private enum Dummy implements Fluent<@NotNull Dummy>
  {
    A,
    B,
    C;
  }

}
