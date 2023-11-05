package de.ochmanski.immutables;

import annotations.UnitTest;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.ImmutableList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
//@ExtendWith(MockitoExtension.class)
class ImmutableListTest
{

  @Test
  void ofNullClass()
  {
    assertThatThrownBy(() -> ImmutableList.ofGenerator(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void of0()
  {
    @NotNull final IList<Dummy> actual = ImmutableList.ofGenerator(Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void of1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = ImmutableList.of(s1, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a");
  }

  @Test
  void of2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final IList<Dummy> actual = ImmutableList.of(s1, s2, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "b");
  }

  @Test
  void of3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = ImmutableList.of(s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = ImmutableList.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.unwrap()).extracting(Dummy::getS).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = ImmutableList.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> ImmutableList.of(s1, null, null, null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e2' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e3' of de/ochmanski/immutables/ImmutableList.of must not be null"),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArrayNullClass()
  {
    assertThatThrownBy(() -> ImmutableList.ofGenerator(null)).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class);
  }

  @Test
  void toArrayClass()
  {
    final IList<Dummy> actual = ImmutableList.ofGenerator(Dummy[]::new);
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void toArray0()
  {
    assertThatThrownBy(() -> ImmutableList.of((Dummy) null, Dummy[]::new))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isExactlyInstanceOf(NullPointerException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'e1' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void toArray1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = ImmutableList.of(s1, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1);
  }

  @Test
  void toArray2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final IList<Dummy> actual = ImmutableList.of(s1, s2, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s2);
  }

  @Test
  void toArray3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = ImmutableList.of(s1, s1, s2, s3, Dummy[]::new);
    assertThat(actual.toArray()).containsExactly(s1, s1, s2, s3);
  }

  @Test
  void equalable()
  {
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
  void toStringTest() {
    ImmutableList<String> classUnderTest = ImmutableList.<@javax.validation.constraints.NotNull String>of("nothing", String[]::new);
    final String actual = classUnderTest.toString();
    assertThat(actual).isEqualTo("[\"nothing\"]");
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
  private static class Dummy implements Equalable<Dummy>
  {
    @Builder.Default
    String s = "dummy";
  }
}
