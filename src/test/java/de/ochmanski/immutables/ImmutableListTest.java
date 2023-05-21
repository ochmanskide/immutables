package de.ochmanski.immutables;

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

@ExtendWith(MockitoExtension.class)
class ImmutableListTest
{

  @Test
  void ofNullClass()
  {
    assertThatThrownBy(() -> IList.ofGenerator(null)).isInstanceOfAny(NullPointerException.class);
  }

  @Test
  void of0()
  {
    @NotNull
    final IList<Dummy> actual = IList.ofGenerator(Dummy[]::new);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().get()).isEmpty();
  }

  @Test
  void of1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = IList.of(s1);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a");
  }

  @Test
  void of2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final IList<Dummy> actual = IList.of(s1, s2);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a", "b");
  }

  @Test
  void of3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = IList.of(s1, s2, s3);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a", "b", "c");
  }

  @Test
  void ofArray3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = IList.of(s1, s1, s2, s3);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a", "a", "b", "c");
  }

  @Test
  void toArrayNull()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> IList.of((Dummy)null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(null);
  }

  @Test
  void toArrayEmpty()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = IList.of(s1);
    assertThat(actual.toArray().get()).containsExactly(s1);
  }

  @Test
  void toArrayEmpty1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> IList.of(s1, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(null);
  }

  @Test
  void toArrayEmpty2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> IList.of(s1, null, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(null);
  }

  @Test
  void toArrayEmpty3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> IList.of(s1, null, null, null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(null);
  }

  @Test
  void toArrayNullClass()
  {
    assertThatThrownBy(() -> IList.ofGenerator(null)).isInstanceOfAny(NullPointerException.class);
  }

  @Test
  void toArrayClass()
  {
    final IList<Dummy> actual = IList.ofGenerator(Dummy[]::new);
    assertThat(actual.toArray().get()).isEmpty();
  }

  @Test
  void toArray0()
  {
    assertThatThrownBy(() -> IList.of((Dummy)null))
        .isExactlyInstanceOf(NullPointerException.class)
        .hasMessage(null);
  }

  @Test
  void toArray1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = IList.of(s1);
    assertThat(actual.toArray().get()).containsExactly(s1);
  }

  @Test
  void toArray2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final IList<Dummy> actual = IList.of(s1, s2);
    assertThat(actual.toArray().get()).containsExactly(s1, s2);
  }

  @Test
  void toArray3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = IList.of(s1, s1, s2, s3);
    assertThat(actual.toArray().get()).containsExactly(s1, s1, s2, s3);
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

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true)
  private static class Dummy implements Equalable<Dummy>
  {
    @Builder.Default
    String s = "dummy";

  }

}
