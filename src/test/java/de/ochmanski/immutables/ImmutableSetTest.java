package de.ochmanski.immutables;

import annotations.UnitTest;
import de.ochmanski.immutables.collection.ISet;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableSet;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ImmutableSetTest {

  @Test
  void ofNullClass() {
    assertThatThrownBy(() -> ImmutableSet.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void of0() {
    @NotNull final ISet<Dummy2> actual = ImmutableSet.noneOf(Dummy2[]::new);
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, Dummy2[]::new);
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactly("a");
  }

  @Test
  void of2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, s2, Dummy2[]::new);
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b");
  }

  @Test
  void of3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, s2, s3, Dummy2[]::new);
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b", "c");
  }

  @Test
  void ofArray3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, s1, s2, s3, Dummy2[]::new);
    assertThat(actual).isInstanceOf(ImmutableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b", "c");
  }

  @Test
  void toArrayNull() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableSet.of((Dummy2) null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableSet.of(s1, null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableSet.of(s1, null, null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableSet.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void toArrayNullClass() {
    assertThatThrownBy(() -> ImmutableSet.noneOf(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass() {
    final ISet<Dummy2> actual = ImmutableSet.noneOf(Dummy2[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> ImmutableSet.of((Dummy2) null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableSet.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, s2, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2);
  }

  @Test
  void toArray3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = ImmutableSet.of(s1, s1, s2, s3, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2, s3);
  }

  @Test
  void equalable() {
    final Dummy2 a = Dummy2.builder().s("a").build();
    final Dummy2 b = Dummy2.builder().s("a").build();
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final Dummy2 c = Dummy2.builder().s("c").build();
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTest01() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>noneOf(String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>noneOf(String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest11() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\"]");
  }

  @Test
  void toStringTest12() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\"]");
  }

  @Test
  void toStringTest21() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\"]");
  }

  @Test
  void toStringTest22() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\"]");
  }

  @Test
  void toStringTest31() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b", "c", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\"]");
  }

  @Test
  void toStringTest32() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\"]");
  }

  @Test
  void toStringTest41() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b", "c", "d", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTest42() {
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTestArray01() {
    final String[] array = {"a", "b", "c", "d"};
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>ofArray(array, String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTestArray02() {
    final String[] array = {"a", "b", "c", "d"};
    ImmutableSet<String> unitUnderTest = ImmutableSet.<@NotNull String>ofArray(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTestArray21() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> ImmutableSet.<@NotNull String>ofArray(array, String[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> ImmutableSet.<@NotNull String>ofArray(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Value
  @RequiredArgsConstructor
  @Builder(toBuilder = true)
  private static class Dummy2 implements Equalable<@NotNull Dummy2> {
    @Builder.Default
    String s = "dummy";
  }
}
