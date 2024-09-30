package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.ESet;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.equalable.Equalable.EqualableString;
import de.ochmanski.immutables.equalable.EqualableList;
import de.ochmanski.immutables.equalable.EqualableSet;
import de.ochmanski.immutables.immutable.ISet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EqualableSetTest
{

  @Test
  void ofNullClass() {
    assertThatThrownBy(() -> EqualableSet.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void empty()
  {
    @NotNull
    final ESet<Equalable.@NotNull Dummy> actual = EqualableSet.empty();
    assertThat((Iterable<? extends Equalable.@NotNull Dummy>)actual).isInstanceOf(EqualableSet.class);
    final Class<Equalable.@NotNull Dummy> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of0() {
    @NotNull final ESet<@NotNull Dummy2> actual = EqualableSet.noneOf(Dummy2[]::new);
    assertThat((Iterable<? extends @NotNull Dummy2>)actual).isInstanceOf(EqualableSet.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, Dummy2[]::new);
    assertThat(actual).isInstanceOf(EqualableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactly("a");
  }

  @Test
  void of2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, s2, Dummy2[]::new);
    assertThat(actual).isInstanceOf(EqualableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b");
  }

  @Test
  void of3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, s2, s3, Dummy2[]::new);
    assertThat(actual).isInstanceOf(EqualableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b", "c");
  }

  @Test
  void ofArray3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, s1, s2, s3, Dummy2[]::new);
    assertThat(actual).isInstanceOf(EqualableSet.class);
    assertThat(actual.unwrap()).extracting(Dummy2::getS).containsExactlyInAnyOrder("a", "b", "c");
  }

  @Test
  void toArrayNull() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> EqualableSet.of((Dummy2) null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> EqualableSet.of(s1, null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> EqualableSet.of(s1, null, null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    assertThatThrownBy(() -> EqualableSet.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableSet.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void toArrayNullClass() {
    assertThatThrownBy(() -> EqualableSet.noneOf(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass() {
    final ISet<Dummy2> actual = EqualableSet.noneOf(Dummy2[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> EqualableSet.of((Dummy2) null, Dummy2[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableSet.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, s2, Dummy2[]::new);
    assertThat(actual.toArray()).containsExactlyInAnyOrder(s1, s2);
  }

  @Test
  void toArray3() {
    final Dummy2 s1 = Dummy2.builder().s("a").build();
    final Dummy2 s2 = Dummy2.builder().s("b").build();
    final Dummy2 s3 = Dummy2.builder().s("c").build();
    final ISet<Dummy2> actual = EqualableSet.of(s1, s1, s2, s3, Dummy2[]::new);
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
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>noneOf(EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    EqualableSet<Equalable.@NotNull Dummy> unitUnderTest = EqualableSet.empty();
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest11() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of(Equalable.of("a"), EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a]");
  }

  @Test
  void toStringTest12() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a]");
  }

  @Test
  void toStringTest21() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of(Equalable.of("a"), Equalable.of("b"), EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b]"),
      p -> assertThat(p).isEqualTo("[b,a]"),
      p -> assertThat(p).isEqualTo("[a, b]"),
      p -> assertThat(p).isEqualTo("[b, a]")
    );
  }

  @Test
  void toStringTest22() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b]"),
      p -> assertThat(p).isEqualTo("[b,a]"),
      p -> assertThat(p).isEqualTo("[a, b]"),
      p -> assertThat(p).isEqualTo("[b, a]")
    );
  }

  @Test
  void toStringTest31() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of(Equalable.of("a"), Equalable.of("b"), Equalable.of("c"),  EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b,c]"),
      p -> assertThat(p).isEqualTo("[c,b,a]"),
      p -> assertThat(p).isEqualTo("[a, b, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b]"),
      p -> assertThat(p).isEqualTo("[b, a, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a]"),
      p -> assertThat(p).isEqualTo("[c, b, a]"),
      p -> assertThat(p).isEqualTo("[c, a, b]")
    );
  }

  @Test
  void toStringTest32() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b,c]"),
      p -> assertThat(p).isEqualTo("[c,b,a]"),
      p -> assertThat(p).isEqualTo("[a, b, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b]"),
      p -> assertThat(p).isEqualTo("[b, a, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a]"),
      p -> assertThat(p).isEqualTo("[c, b, a]"),
      p -> assertThat(p).isEqualTo("[c, a, b]")
    );
  }

  @Test
  void toStringTest41() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>of(Equalable.of("a"), Equalable.of("b"), Equalable.of("c"), Equalable.of("d"), EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b,c,d]"),
      p -> assertThat(p).isEqualTo("[d,c,b,a]"),

      p -> assertThat(p).isEqualTo("[a, b, c, d]"),
      p -> assertThat(p).isEqualTo("[a, b, d, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b, d]"),
      p -> assertThat(p).isEqualTo("[a, c, d, b]"),
      p -> assertThat(p).isEqualTo("[a, d, b, c]"),
      p -> assertThat(p).isEqualTo("[a, d, c, b]"),

      p -> assertThat(p).isEqualTo("[b, a, c, d]"),
      p -> assertThat(p).isEqualTo("[b, a, d, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a, d]"),
      p -> assertThat(p).isEqualTo("[b, c, d, a]"),
      p -> assertThat(p).isEqualTo("[b, d, a, c]"),
      p -> assertThat(p).isEqualTo("[b, d, c, a]"),

      p -> assertThat(p).isEqualTo("[c, a, b, d]"),
      p -> assertThat(p).isEqualTo("[c, a, d, b]"),
      p -> assertThat(p).isEqualTo("[c, b, a, d]"),
      p -> assertThat(p).isEqualTo("[c, b, d, a]"),
      p -> assertThat(p).isEqualTo("[c, d, a, b]"),
      p -> assertThat(p).isEqualTo("[c, d, b, a]"),

      p -> assertThat(p).isEqualTo("[d, a, b, c]"),
      p -> assertThat(p).isEqualTo("[d, a, c, b]"),
      p -> assertThat(p).isEqualTo("[d, b, a, c]"),
      p -> assertThat(p).isEqualTo("[d, b, c, a]"),
      p -> assertThat(p).isEqualTo("[d, c, a, b]"),
      p -> assertThat(p).isEqualTo("[d, c, b, a]")
    );
  }

  @Test
  void toStringTest42() {
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull  EqualableString>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a,b,c,d]"),
      p -> assertThat(p).isEqualTo("[d,c,b,a]"),

      p -> assertThat(p).isEqualTo("[a, b, c, d]"),
      p -> assertThat(p).isEqualTo("[a, b, d, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b, d]"),
      p -> assertThat(p).isEqualTo("[a, c, d, b]"),
      p -> assertThat(p).isEqualTo("[a, d, b, c]"),
      p -> assertThat(p).isEqualTo("[a, d, c, b]"),

      p -> assertThat(p).isEqualTo("[b, a, c, d]"),
      p -> assertThat(p).isEqualTo("[b, a, d, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a, d]"),
      p -> assertThat(p).isEqualTo("[b, c, d, a]"),
      p -> assertThat(p).isEqualTo("[b, d, a, c]"),
      p -> assertThat(p).isEqualTo("[b, d, c, a]"),

      p -> assertThat(p).isEqualTo("[c, a, b, d]"),
      p -> assertThat(p).isEqualTo("[c, a, d, b]"),
      p -> assertThat(p).isEqualTo("[c, b, a, d]"),
      p -> assertThat(p).isEqualTo("[c, b, d, a]"),
      p -> assertThat(p).isEqualTo("[c, d, a, b]"),
      p -> assertThat(p).isEqualTo("[c, d, b, a]"),

      p -> assertThat(p).isEqualTo("[d, a, b, c]"),
      p -> assertThat(p).isEqualTo("[d, a, c, b]"),
      p -> assertThat(p).isEqualTo("[d, b, a, c]"),
      p -> assertThat(p).isEqualTo("[d, b, c, a]"),
      p -> assertThat(p).isEqualTo("[d, c, a, b]"),
      p -> assertThat(p).isEqualTo("[d, c, b, a]")
    );
  }

  @Test
  void toStringTestArray01() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>ofArray(EqualableString.ofArray(array), EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(
      p -> assertThat(p).isEqualTo("[a, b, c, d]"),
      p -> assertThat(p).isEqualTo("[a, b, d, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b, d]"),
      p -> assertThat(p).isEqualTo("[a, c, d, b]"),
      p -> assertThat(p).isEqualTo("[a, d, b, c]"),
      p -> assertThat(p).isEqualTo("[a, d, c, b]"),

      p -> assertThat(p).isEqualTo("[b, a, c, d]"),
      p -> assertThat(p).isEqualTo("[b, a, d, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a, d]"),
      p -> assertThat(p).isEqualTo("[b, c, d, a]"),
      p -> assertThat(p).isEqualTo("[b, d, a, c]"),
      p -> assertThat(p).isEqualTo("[b, d, c, a]"),

      p -> assertThat(p).isEqualTo("[c, a, b, d]"),
      p -> assertThat(p).isEqualTo("[c, a, d, b]"),
      p -> assertThat(p).isEqualTo("[c, b, a, d]"),
      p -> assertThat(p).isEqualTo("[c, b, d, a]"),
      p -> assertThat(p).isEqualTo("[c, d, a, b]"),
      p -> assertThat(p).isEqualTo("[c, d, b, a]"),

      p -> assertThat(p).isEqualTo("[d, a, b, c]"),
      p -> assertThat(p).isEqualTo("[d, a, c, b]"),
      p -> assertThat(p).isEqualTo("[d, b, a, c]"),
      p -> assertThat(p).isEqualTo("[d, b, c, a]"),
      p -> assertThat(p).isEqualTo("[d, c, a, b]"),
      p -> assertThat(p).isEqualTo("[d, c, b, a]")
    );
  }

  @Test
  void toStringTestArray02() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableSet<@NotNull EqualableString> unitUnderTest = EqualableSet.<@NotNull EqualableString>ofArray(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).satisfiesAnyOf(

      p -> assertThat(p).isEqualTo("[a, b, c, d]"),
      p -> assertThat(p).isEqualTo("[a, b, d, c]"),
      p -> assertThat(p).isEqualTo("[a, c, b, d]"),
      p -> assertThat(p).isEqualTo("[a, c, d, b]"),
      p -> assertThat(p).isEqualTo("[a, d, b, c]"),
      p -> assertThat(p).isEqualTo("[a, d, c, b]"),

      p -> assertThat(p).isEqualTo("[b, a, c, d]"),
      p -> assertThat(p).isEqualTo("[b, a, d, c]"),
      p -> assertThat(p).isEqualTo("[b, c, a, d]"),
      p -> assertThat(p).isEqualTo("[b, c, d, a]"),
      p -> assertThat(p).isEqualTo("[b, d, a, c]"),
      p -> assertThat(p).isEqualTo("[b, d, c, a]"),

      p -> assertThat(p).isEqualTo("[c, a, b, d]"),
      p -> assertThat(p).isEqualTo("[c, a, d, b]"),
      p -> assertThat(p).isEqualTo("[c, b, a, d]"),
      p -> assertThat(p).isEqualTo("[c, b, d, a]"),
      p -> assertThat(p).isEqualTo("[c, d, a, b]"),
      p -> assertThat(p).isEqualTo("[c, d, b, a]"),

      p -> assertThat(p).isEqualTo("[d, a, b, c]"),
      p -> assertThat(p).isEqualTo("[d, a, c, b]"),
      p -> assertThat(p).isEqualTo("[d, b, a, c]"),
      p -> assertThat(p).isEqualTo("[d, b, c, a]"),
      p -> assertThat(p).isEqualTo("[d, c, a, b]"),
      p -> assertThat(p).isEqualTo("[d, c, b, a]")
    );
  }

  @Test
  void toStringTestArray21() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableSet.<@NotNull EqualableString>ofArray(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableSet.<@NotNull EqualableString>ofArray(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toList()
  {
    final List<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toList().unwrap();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toList2()
  {
    final EqualableList<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toList();
    assertThat(actual.map(EqualableString::toString)).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toList3()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toList();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void createIterableSet()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C");
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet()
  {
    final Set<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toSet().unwrap();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet2()
  {
    final EqualableSet<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toSet();
    assertThat(actual.map(EqualableString::toString)).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet3()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").toSet();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet4()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableSet.of("A", "B", "C").getSet();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
  private static class Dummy2 implements Comparable<@NotNull Dummy2>, Equalable<@NotNull Dummy2>
  {
    @Builder.Default
    String s = "dummy";

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this object
     *     is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *     from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     *     {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     *     class that implements the {@code Comparable} interface and violates
     *     this condition should clearly indicate this fact.  The recommended
     *     language is "Note: this class has a natural ordering that is
     *     inconsistent with equals."
     */
    @Contract(pure = true)
    @Override
    public int compareTo(@NotNull final Dummy2 o)
    {
      return 0;
    }
  }
}
