package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.Equalable.EqualableString;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EqualableListTest {

  @Test
  void ofNullClass() {
    assertThatThrownBy(() -> EqualableList.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void empty()
  {
    EqualableList<Equalable.@NotNull Dummy> actual = EqualableList.empty();
    assertThat((Iterable<? extends Equalable.@NotNull Dummy>)actual).isInstanceOf(EqualableList.class);
    final Class<? extends @NotNull Equalable<?>> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of0() {
    @NotNull EqualableList<@NotNull EqualableString> actual = EqualableList.noneOf(EqualableString[]::new);
    assertThat((Iterable<? extends EqualableString>)actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final EqualableString s1 = EqualableString.of("a");
    EqualableList<@NotNull EqualableString> actual = EqualableList.of(s1, EqualableString[]::new);
    assertThat((Iterable<? extends EqualableString>)actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(EqualableString::orElseBlank).containsExactly("a");
  }

  @Test
  void of2() {
    final EqualableString s1 = EqualableString.of("a");
    final EqualableString s2 = EqualableString.of("b");
    EqualableList<@NotNull EqualableString> actual = EqualableList.of(s1, s2, EqualableString[]::new);
    assertThat((Iterable<? extends EqualableString>)actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(EqualableString::orElseBlank).containsExactly("a", "b");
  }

  @Test
  void of3() {
    final EqualableString s1 = EqualableString.of("a");
    final EqualableString s2 = EqualableString.of("b");
    final EqualableString s3 = EqualableString.of("c");
    EqualableList<@NotNull EqualableString> actual = EqualableList.of(s1, s2, s3, EqualableString[]::new);
    assertThat((Iterable<? extends EqualableString>)actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(EqualableString::orElseBlank).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3() {
    final EqualableString s1 = EqualableString.of("a");
    final EqualableString s2 = EqualableString.of("b");
    final EqualableString s3 = EqualableString.of("c");
    EqualableList<@NotNull EqualableString> actual = EqualableList.of(s1, s1, s2, s3, EqualableString[]::new);
    assertThat((Iterable<? extends EqualableString>)actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(EqualableString::orElseBlank).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull() {
    final EqualableString s1 = EqualableString.of("a");
    assertThatThrownBy(() -> EqualableList.of((EqualableString) null, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final EqualableString s1 = EqualableString.of("a");
    EqualableList<@NotNull EqualableString> actual = EqualableList.of(s1, EqualableString[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final EqualableString s1 = EqualableString.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final EqualableString s1 = EqualableString.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, null, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final EqualableString s1 = EqualableString.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, null, (IntFunction<EqualableString[]>)null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayNullClass() {
    assertThatThrownBy(() -> EqualableList.noneOf(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass() {
    EqualableList<? extends @NotNull EqualableString> actual = EqualableList.noneOf(EqualableString[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> EqualableList.of((EqualableString) null, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final EqualableString s1 = EqualableString.of("a");
    EqualableList<? extends @NotNull EqualableString> actual = EqualableList.of(s1, EqualableString[]::new);
    @NotNull
    final EqualableString[] array = actual.toArray();
    assertThat(array).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final EqualableString s1 = EqualableString.of("a");
    final EqualableString s2 = EqualableString.of("b");
    EqualableList<? extends @NotNull EqualableString> actual = EqualableList.of(s1, s2, EqualableString[]::new);
    @NotNull
    final EqualableString[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s2);
  }

  @Test
  void toArray3() {
    final EqualableString s1 = EqualableString.of("a");
    final EqualableString s2 = EqualableString.of("b");
    final EqualableString s3 = EqualableString.of("c");
    EqualableList<? extends @NotNull EqualableString> actual = EqualableList.of(s1, s1, s2, s3, EqualableString[]::new);
    @NotNull
    final EqualableString[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable() {
    final EqualableString a = EqualableString.of("a");
    final EqualableString b = EqualableString.of("a");
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final EqualableString c = EqualableString.of("c");
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTest01() {
    EqualableList<? extends @NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>noneOf(EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    EqualableList<Equalable.Dummy> unitUnderTest = EqualableList.empty();
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest11() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull String>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a]");
  }

  @Test
  void toStringTest12() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a]");
  }

  @Test
  void toStringTest21() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull String>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b]");
  }

  @Test
  void toStringTest22() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b]");
  }

  @Test
  void toStringTest31() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull String>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c]");
  }

  @Test
  void toStringTest32() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c]");
  }

  @Test
  void toStringTest41() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull String>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c, d]");
  }

  @Test
  void toStringTest42() {
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c, d]");
  }

  @Test
  void toStringTestArray01() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull String>of(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c, d]");
  }

  @Test
  void toStringTestArray02() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c, d]");
  }

  @Test
  void toStringTestArray21() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableList.<@NotNull String>of(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableList.<@NotNull EqualableString>of(array))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray31() {
    final EqualableString[] array = {EqualableString.of("a"), EqualableString.of("b"), EqualableString.of("c"), null};
    assertThatThrownBy(() -> EqualableList.<@NotNull EqualableString>of(array, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray32() {
    final EqualableString[] array = {EqualableString.of("a"), EqualableString.of("b"), EqualableString.of("c")};
    final EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of(array, EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c]");
  }

  @Test
  void toStringTestArray41() {
    final EqualableString[] array = {EqualableString.of("a"), EqualableString.of("b"), EqualableString.of("c"), null};
    assertThatThrownBy(() -> EqualableList.<@NotNull EqualableString>of(array, EqualableString[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray42() {
    final EqualableString[] array = {EqualableString.of("a"), EqualableString.of("b"), EqualableString.of("c")};
    final EqualableList<@NotNull EqualableString> unitUnderTest = EqualableList.<@NotNull EqualableString>of(array, EqualableString[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[a, b, c]");
  }

  @Test
  void toList()
  {
    final List<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toList().unwrap();
    assertThat(actual).extracting(EqualableString::toString).containsExactly("A", "B", "C");
  }

  @Test
  void toList2()
  {
    final EqualableList<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toList();
    assertThat(actual.map(EqualableString::toString)).containsExactly("A", "B", "C");
  }

  @Test
  void toList3()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toList();
    assertThat(actual).extracting(EqualableString::toString).containsExactly("A", "B", "C");
  }

  @Test
  void toList4()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").getList();
    assertThat(actual).extracting(EqualableString::toString).containsExactly("A", "B", "C");
  }

  @Test
  void toSet()
  {
    final Set<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toSet().unwrap();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet2()
  {
    final EqualableSet<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toSet();
    assertThat(actual.map(EqualableString::toString)).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void toSet3()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C").toSet();
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }

  @Test
  void createIterableList()
  {
    final Iterable<@NotNull EqualableString> actual = EqualableList.of("A", "B", "C");
    assertThat(actual).extracting(EqualableString::toString).containsExactlyInAnyOrder("A", "B", "C");
  }
}
