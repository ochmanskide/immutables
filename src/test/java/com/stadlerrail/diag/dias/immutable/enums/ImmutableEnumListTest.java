package com.stadlerrail.diag.dias.immutable.enums;

import annotations.UnitTest;
import com.stadlerrail.diag.dias.immutables.equalable.Equalable;
import com.stadlerrail.diag.dias.immutables.immutable.IList;
import com.stadlerrail.diag.dias.immutables.immutable.enums.ImmutableEnumList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ImmutableEnumListTest {

  @Test
  void ofNullClass() {
    assertThatThrownBy(() -> ImmutableEnumList.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void empty()
  {
    @NotNull final ImmutableEnumList<Equalable.@NotNull Dummy> actual = ImmutableEnumList.empty();
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    final Class<Equalable.@NotNull Dummy> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    final Equalable.@NotNull Dummy[] array = actual.toArray();
    assertThat(array).isEmpty();
  }

  @Test
  void of0() {
    @NotNull final IList<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final Dummy s1 = Dummy.A;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A);
  }

  @Test
  void of2() {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, s2, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A, Dummy.B);
  }

  @Test
  void of3() {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void ofArray3() {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableEnumList.class);
    assertThat(actual.unwrap()).containsExactly(Dummy.A, Dummy.A, Dummy.B, Dummy.C);
  }

  @Test
  void toArrayNull() {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final Dummy s1 = Dummy.A;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumList.of(s1, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumList.of(s1, null, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableEnumList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final Dummy s1 = Dummy.A;
    assertThatThrownBy(() -> ImmutableEnumList.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableEnumList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableEnumList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void toArrayNullClass() {
    assertThatThrownBy(() -> ImmutableEnumList.noneOf(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass() {
    final IList<Dummy> actual = ImmutableEnumList.noneOf(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> ImmutableEnumList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableEnumList.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final Dummy s1 = Dummy.A;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, s2, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s2);
  }

  @Test
  void toArray3() {
    final Dummy s1 = Dummy.A;
    final Dummy s2 = Dummy.B;
    final Dummy s3 = Dummy.C;
    final IList<Dummy> actual = ImmutableEnumList.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable() {
    final Dummy a = Dummy.A;
    final Dummy b = Dummy.A;
    assertThat(a).isEqualTo(b);
    assertThat(Equalable.<@Nullable Dummy>areTheSame(a, b)).isTrue();
    assertThat(Equalable.<@Nullable Dummy>areEqual(a, b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final Dummy c = Dummy.C;
    assertThat(a).isNotEqualTo(c);
    assertThat(Equalable.<@Nullable Dummy>areTheSame(a, c)).isFalse();
    assertThat(Equalable.<@Nullable Dummy>areEqual(a, c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTestArray21() {
    final Dummy[] array = {Dummy.A, Dummy.B, Dummy.C, Dummy.A, null};
    assertThatThrownBy(() -> ImmutableEnumList.<@NotNull Dummy>of(array, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  private enum Dummy {
    A,
    B,
    C;
  }
}
