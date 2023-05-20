package de.ochmanski.immutables;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ImmutableListTest
{

  @Test
  void ofNull()
  {
    assertThatThrownBy(() -> IList.of(null)).isInstanceOfAny(NullPointerException.class);
  }

  @Test
  void of0()
  {
    final IList<Dummy> actual = IList.of();
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
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
  void ofArrayNull()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    assertThatThrownBy(() -> IList.of(s1, (Dummy[])null))
        .hasMessage("Cannot read the array length because \"array\" is null");
  }

  @Test
  void ofArray0()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = IList.of(s1, new Dummy[0]);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a");
  }

  @Test
  void ofArray1()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final IList<Dummy> actual = IList.of(s1, new Dummy[1]);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a");
  }

  @Test
  void ofArray2()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final IList<Dummy> actual = IList.of(s1, new Dummy[2]);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a", "b");
  }

  @Test
  void ofArray3()
  {
    final Dummy s1 = Dummy.builder().s("a").build();
    final Dummy s2 = Dummy.builder().s("b").build();
    final Dummy s3 = Dummy.builder().s("c").build();
    final IList<Dummy> actual = IList.of(s1, s1, s2, s3);
    assertThat(actual).isInstanceOf(ImmutableList.class);
    assertThat(actual.toList()).extracting(Dummy::getS).containsExactly("a", "b", "c");
  }

  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder(toBuilder = true)
  private static class Dummy
  {
    @Builder.Default
    String s = "dummy";

  }

}
