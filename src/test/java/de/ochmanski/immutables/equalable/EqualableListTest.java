package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import de.ochmanski.immutables.StringWrapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EqualableListTest {

  @Test
  void ofNullClass() {
    assertThatThrownBy(() -> EqualableList.ofGenerator(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void of0() {
    @NotNull EqualableList<@NotNull StringWrapper> actual = EqualableList.ofGenerator(StringWrapper[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final StringWrapper s1 = StringWrapper.of("a");
    EqualableList<@NotNull StringWrapper> actual = EqualableList.of(s1, StringWrapper[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(StringWrapper::getRaw).containsExactly("a");
  }

  @Test
  void of2() {
    final StringWrapper s1 = StringWrapper.of("a");
    final StringWrapper s2 = StringWrapper.of("b");
    EqualableList<@NotNull StringWrapper> actual = EqualableList.of(s1, s2, StringWrapper[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(StringWrapper::getRaw).containsExactly("a", "b");
  }

  @Test
  void of3() {
    final StringWrapper s1 = StringWrapper.of("a");
    final StringWrapper s2 = StringWrapper.of("b");
    final StringWrapper s3 = StringWrapper.of("c");
    EqualableList<@NotNull StringWrapper> actual = EqualableList.of(s1, s2, s3, StringWrapper[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(StringWrapper::getRaw).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3() {
    final StringWrapper s1 = StringWrapper.of("a");
    final StringWrapper s2 = StringWrapper.of("b");
    final StringWrapper s3 = StringWrapper.of("c");
    EqualableList<@NotNull StringWrapper> actual = EqualableList.of(s1, s1, s2, s3, StringWrapper[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(StringWrapper::getRaw).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull() {
    final StringWrapper s1 = StringWrapper.of("a");
    assertThatThrownBy(() -> EqualableList.of((StringWrapper) null, StringWrapper[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final StringWrapper s1 = StringWrapper.of("a");
    EqualableList<@NotNull StringWrapper> actual = EqualableList.of(s1, StringWrapper[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final StringWrapper s1 = StringWrapper.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, StringWrapper[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final StringWrapper s1 = StringWrapper.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, null, StringWrapper[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final StringWrapper s1 = StringWrapper.of("a");
    assertThatThrownBy(() -> EqualableList.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayNullClass() {
    assertThatThrownBy(() -> EqualableList.ofGenerator(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass() {
    EqualableList<? extends @NotNull StringWrapper> actual = EqualableList.ofGenerator(StringWrapper[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> EqualableList.of((StringWrapper) null, StringWrapper[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final StringWrapper s1 = StringWrapper.of("a");
    EqualableList<? extends @NotNull StringWrapper> actual = EqualableList.of(s1, StringWrapper[]::new);
    final @NotNull StringWrapper[] array = actual.toArray();
    assertThat(array).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final StringWrapper s1 = StringWrapper.of("a");
    final StringWrapper s2 = StringWrapper.of("b");
    EqualableList<? extends @NotNull StringWrapper> actual = EqualableList.of(s1, s2, StringWrapper[]::new);
    final @NotNull StringWrapper[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s2);
  }

  @Test
  void toArray3() {
    final StringWrapper s1 = StringWrapper.of("a");
    final StringWrapper s2 = StringWrapper.of("b");
    final StringWrapper s3 = StringWrapper.of("c");
    EqualableList<? extends @NotNull StringWrapper> actual = EqualableList.of(s1, s1, s2, s3, StringWrapper[]::new);
    final @NotNull StringWrapper[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable() {
    final StringWrapper a = StringWrapper.of("a");
    final StringWrapper b = StringWrapper.of("a");
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final StringWrapper c = StringWrapper.of("c");
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTest01() {
    EqualableList<? extends @NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>ofGenerator(StringWrapper[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    EqualableList<? extends @NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>empty();
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest11() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull String>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"}]");
  }

  @Test
  void toStringTest12() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of("a");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"}]");
  }

  @Test
  void toStringTest21() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull String>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"}]");
  }

  @Test
  void toStringTest22() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of("a", "b");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"}]");
  }

  @Test
  void toStringTest31() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull String>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"}]");
  }

  @Test
  void toStringTest32() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of("a", "b", "c");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"}]");
  }

  @Test
  void toStringTest41() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull String>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"},{\"raw\":\"d\"}]");
  }

  @Test
  void toStringTest42() {
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of("a", "b", "c", "d");
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"},{\"raw\":\"d\"}]");
  }

  @Test
  void toStringTestArray01() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull String>of(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"},{\"raw\":\"d\"}]");
  }

  @Test
  void toStringTestArray02() {
    final String[] array = {"a", "b", "c", "d"};
    EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of(array);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"},{\"raw\":\"d\"}]");
  }

  @Test
  void toStringTestArray21() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableList.<@NotNull String>of(array))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final String[] array = {"a", "b", "c", null};
    assertThatThrownBy(() -> EqualableList.<@NotNull StringWrapper>of(array))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray31() {
    final StringWrapper[] array = {StringWrapper.of("a"), StringWrapper.of("b"), StringWrapper.of("c"), null};
    assertThatThrownBy(() -> EqualableList.<@NotNull StringWrapper>of(array, StringWrapper[]::new))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray32() {
    final StringWrapper[] array = {StringWrapper.of("a"), StringWrapper.of("b"), StringWrapper.of("c")};
    final EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of(array, StringWrapper[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"}]");
  }

  @Test
  void toStringTestArray41() {
    final StringWrapper[] array = {StringWrapper.of("a"), StringWrapper.of("b"), StringWrapper.of("c"), null};
    assertThatThrownBy(() -> EqualableList.<@NotNull StringWrapper>of(array, StringWrapper[]::new))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray42() {
    final StringWrapper[] array = {StringWrapper.of("a"), StringWrapper.of("b"), StringWrapper.of("c")};
    final EqualableList<@NotNull StringWrapper> unitUnderTest = EqualableList.<@NotNull StringWrapper>of(array, StringWrapper[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"raw\":\"a\"},{\"raw\":\"b\"},{\"raw\":\"c\"}]");
  }
}
