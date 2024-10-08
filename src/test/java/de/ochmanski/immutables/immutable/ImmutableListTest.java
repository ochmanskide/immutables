package de.ochmanski.immutables.immutable;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.immutable.IList;
import de.ochmanski.immutables.immutable.ImmutableList;
import lombok.AccessLevel;
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
class ImmutableListTest
{

  @Test
  void ofNullClass()
  {
    assertThatThrownBy(() -> ImmutableList.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void empty()
  {
    @NotNull
    final IList<Equalable.Dummy> actual = ImmutableList.empty();
    assertThat(actual).isInstanceOf(ImmutableList.class);
    final Class<Equalable.@NotNull Dummy> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of0()
  {
    @NotNull final IList<Dummy3> actual = ImmutableList.noneOf(Dummy3[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, Dummy3[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy3::getS).containsExactly("a");
  }

  @Test
  void of2()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final Dummy3 s2 = Dummy3.builder().s("b").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, s2, Dummy3[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy3::getS).containsExactly("a", "b");
  }

  @Test
  void of3()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final Dummy3 s2 = Dummy3.builder().s("b").build();
    final Dummy3 s3 = Dummy3.builder().s("c").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, s2, s3, Dummy3[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy3::getS).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final Dummy3 s2 = Dummy3.builder().s("b").build();
    final Dummy3 s3 = Dummy3.builder().s("c").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, s1, s2, s3, Dummy3[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy3::getS).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of((Dummy3) null, Dummy3[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, Dummy3[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, Dummy3[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, null, Dummy3[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayNullClass()
  {
    assertThatThrownBy(() -> ImmutableList.noneOf(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass()
  {
    final IList<Dummy3> actual = ImmutableList.noneOf(Dummy3[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0()
  {
    assertThatThrownBy(() -> ImmutableList.of((Dummy3) null, Dummy3[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArray1()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, Dummy3[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final Dummy3 s2 = Dummy3.builder().s("b").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, s2, Dummy3[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s2);
  }

  @Test
  void toArray3()
  {
    final Dummy3 s1 = Dummy3.builder().s("a").build();
    final Dummy3 s2 = Dummy3.builder().s("b").build();
    final Dummy3 s3 = Dummy3.builder().s("c").build();
    final IList<Dummy3> actual = ImmutableList.of(s1, s1, s2, s3, Dummy3[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable()
  {
    final Dummy3 a = Dummy3.builder().s("a").build();
    final Dummy3 b = Dummy3.builder().s("a").build();
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final Dummy3 c = Dummy3.builder().s("c").build();
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTest01() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>noneOf(String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    ImmutableList<Fluent<?>> unitUnderTest = ImmutableList.empty();
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest11() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\"]");
  }

  @Test
  void toStringTest12() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\"]");
  }

  @Test
  void toStringTest21() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\"]");
  }

  @Test
  void toStringTest22() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\"]");
  }

  @Test
  void toStringTest31() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\"]");
  }

  @Test
  void toStringTest32() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\"]");
  }

  @Test
  void toStringTest41() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c", "d", String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTest42() {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void hashCode01()
  {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c");
    final int actual = unitUnderTest.hashCode();
    assertThat(actual).isNotZero();
  }

  @Test
  void hashCode02()
  {
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of("a", "b", "c", "d");
    final int actual = unitUnderTest.hashCode();
    assertThat(actual).isNotZero();
  }

  @Test
  void toStringTestArray01() {
    final String[] array = {"a", "b", "c", "d"};
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of(array, String[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTestArray02() {
    final String[] array = {"a", "b", "c", "d"};
    ImmutableList<String> unitUnderTest = ImmutableList.<@NotNull String>of(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[\"a\",\"b\",\"c\",\"d\"]");
  }

  @Test
  void toStringTestArray21() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> ImmutableList.<@NotNull String>of(array, String[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> ImmutableList.<@NotNull String>of(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
  private static class Dummy3 implements Equalable<@NotNull Dummy3>
  {
    @Builder.Default
    String s = "dummy";
  }
}
