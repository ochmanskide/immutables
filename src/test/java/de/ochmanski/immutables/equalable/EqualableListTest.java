package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
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
    @NotNull EqualableList<@NotNull Dummy> actual = EqualableList.ofGenerator(Dummy[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1() {
    final Dummy s1 = Dummy.builder().s("a").build();
    EqualableList<@NotNull Dummy> actual = EqualableList.of(s1, Dummy[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a");
  }

  @Test
  void of2() {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    EqualableList<@NotNull Dummy> actual = EqualableList.of(s1, s2, Dummy[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "b");
  }

  @Test
  void of3() {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    EqualableList<@NotNull Dummy> actual = EqualableList.of(s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3() {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    EqualableList<@NotNull Dummy> actual = EqualableList.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(EqualableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull() {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> EqualableList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty() {
    final Dummy s1 = Dummy.builder().s("a").build();
    EqualableList<@NotNull Dummy> actual = EqualableList.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1() {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> EqualableList.of(s1, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2() {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> EqualableList.of(s1, null, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/EqualableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3() {
    final Dummy s1 = Dummy.builder().s("a").build();
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
    EqualableList<? extends @NotNull Dummy> actual = EqualableList.ofGenerator(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0() {
    assertThatThrownBy(() -> EqualableList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/EqualableList.of must not be null")
      );
  }

  @Test
  void toArray1() {
    final Dummy s1 = Dummy.builder().s("a").build();
    EqualableList<? extends @NotNull Dummy> actual = EqualableList.of(s1, Dummy[]::new);
    final @NotNull Dummy[] array = actual.toArray();
    assertThat(array).containsExactly(s1);
  }

  @Test
  void toArray2() {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    EqualableList<? extends @NotNull Dummy> actual = EqualableList.of(s1, s2, Dummy[]::new);
    final @NotNull Dummy[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s2);
  }

  @Test
  void toArray3() {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    EqualableList<? extends @NotNull Dummy> actual = EqualableList.of(s1, s1, s2, s3, Dummy[]::new);
    final @NotNull Dummy[] array = actual.toArray();
    assertThat(array).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable() {
    final Dummy a = Dummy.builder().s("a").build();
    final Dummy b = Dummy.builder().s("a").build();
    assertThat(a).isEqualTo(b);
    assertThat(a.isEqualTo(b)).isTrue();
    assertThat(a.equals(b)).isTrue();
    final Dummy c = Dummy.builder().s("c").build();
    assertThat(a).isNotEqualTo(c);
    assertThat(a.isEqualTo(c)).isFalse();
    assertThat(a.equals(c)).isFalse();
  }

  @Test
  void toStringTest01() {
    EqualableList<? extends @NotNull Dummy> unitUnderTest = EqualableList.<@NotNull Dummy>ofGenerator(Dummy[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }

  @Test
  void toStringTest02() {
    EqualableList<? extends @NotNull Dummy> unitUnderTest = EqualableList.<@NotNull Dummy>empty();
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[]");
  }


  @Test
  void toStringTestArray21() {
    final Dummy[] array = {Dummy.builder().s("a").build(), Dummy.builder().s("b").build(), Dummy.builder().s("c").build(), null};
    assertThatThrownBy(() -> EqualableList.<@NotNull Dummy>of(array, Dummy[]::new))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause();
  }

  @Test
  void toStringTestArray22() {
    final Dummy[] array = {Dummy.builder().s("a").build(), Dummy.builder().s("b").build(), Dummy.builder().s("c").build()};
    final EqualableList<@NotNull Dummy> unitUnderTest = EqualableList.<@NotNull Dummy>of(array, Dummy[]::new);
    final String actual = unitUnderTest.toString();
    assertThat(actual).isEqualTo("[{\"s\":\"a\"},{\"s\":\"b\"},{\"s\":\"c\"}]");
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
  private static class Dummy implements Equalable<@NotNull Dummy> {
    @Builder.Default
    String s = "dummy";
  }
}
