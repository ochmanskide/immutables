package de.ochmanski.immutables.immutable.enums;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.immutable.ImmutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        + "For example: ImmutableEnumSet.noneOf(Day[]::new)");
  }

  @Test
  void empty()
  {
    @NotNull final ImmutableEnumSet<Equalable.@NotNull Dummy> actual = ImmutableEnumSet.empty();
    assertThat(actual).isInstanceOf(ImmutableEnumSet.class);
    final Class<Equalable.@NotNull Dummy> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    final Equalable.@NotNull Dummy[] array = actual.toArray();
    assertThat(array).isEmpty();
  }

  @Test
  void noneOf()
  {
    @NotNull
    final IntFunction<@NotNull Dummy[]> constructor = Dummy[]::new;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.noneOf(constructor);
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
    assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy)null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 0 of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 0 (parameter 's1') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy s1 = Dummy.A;
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.noneOf(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage(
          "Cannot invoke \"java.util.function.IntFunction.apply(int)\" because \"constructor\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 (parameter 'constructor') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's2' of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 (parameter 's2') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumSet.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"Object.getClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's2' of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 1 (parameter 's2') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 2 (parameter 's3') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 3 (parameter 'constructor') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null")
      );
  }

  @Test
  void toArrayNullClass()
  {
    assertThatThrownBy(() -> ImmutableEnumSet.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass()
  {
    final ImmutableEnumSet<@NotNull Dummy> actual = ImmutableEnumSet.noneOf(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0()
  {
    assertThatThrownBy(() -> ImmutableEnumSet.of((Dummy)null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        p -> assertThat(p).hasMessage("Cannot invoke \"java.lang.Enum.getDeclaringClass()\" because \"e\" is null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 's1' of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 0 of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null"),
        p -> assertThat(p).hasMessage("NotNull annotated argument 0 (parameter 's1') of "
          + "de/ochmanski/immutable/enums/ImmutableEnumSet.of must not be null")
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

  @Test
  void getSet()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> dummy = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    final ImmutableSet<@NotNull Dummy> actual = dummy.getSet();
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void toList()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> dummy = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    final ImmutableEnumList<@NotNull Dummy> actual = dummy.toList();
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).containsExactlyInAnyOrder(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void findFirst()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> dummy = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    final Optional<@Nullable Dummy> actual = dummy.findFirst();
    assertThat(actual).get().isSameAs(Dummy.A);
  }

  @Test
  void findLast()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> dummy = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    final Optional<@Nullable Dummy> actual = dummy.findLast();
    assertThat(actual).containsSame(Dummy.C);
  }

  @Test
  void findAny()
  {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final ImmutableEnumSet<@NotNull Dummy> dummy = ImmutableEnumSet.of(s1, s1, s2, s3, Dummy[]::new);
    final Optional<@Nullable Dummy> actual = dummy.findAny();
    assertThat(actual).get().isIn(Dummy.A, Dummy.B, Dummy.C);
  }

  private enum Dummy implements Fluent<@NotNull Dummy>
  {
    A,
    B,
    C;
  }

}
