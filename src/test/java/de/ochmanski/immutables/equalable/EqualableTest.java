package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import de.ochmanski.immutables.fluent.Fluent;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static de.ochmanski.immutables.constants.Constants.BLANK;
import static de.ochmanski.immutables.equalable.EqualableTest.State.*;
import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EqualableTest
{

  @Test
  void duplicatedValue()
  {
    boolean actual = ServiceState.START.isIn(ServiceState.SLEEP, ServiceState.SLEEP);
    assertThat(actual).isFalse();
    actual = ServiceState.START.isNotIn(ServiceState.SLEEP, ServiceState.SLEEP);
    assertThat(actual).isTrue();
  }

  @Test
  void empty()
  {
    @NotNull final ESet<Equalable.Dummy> actual = EqualableSet.empty();
    assertThat(Optional.of(actual)).get().isInstanceOf(EqualableSet.class);
    final Class<? extends @NotNull Equalable<?>> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Equalable.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray()).isEmpty();
  }

  @Test
  void duplicatedValueArray()
  {
    boolean actual = ServiceState.START.isInArray(new ServiceState[] { ServiceState.SLEEP, ServiceState.SLEEP });
    assertThat(actual).isFalse();
    actual = ServiceState.START.isNotInArray(new ServiceState[] { ServiceState.SLEEP, ServiceState.SLEEP });
    assertThat(actual).isTrue();
  }

  @Test
  void duplicatedValueSet()
  {
    final List<ServiceState> duplicated = List.<ServiceState>of(ServiceState.SLEEP, ServiceState.SLEEP);
    boolean actual = ServiceState.START.isIn(duplicated);
    assertThat(actual).isFalse();
    actual = ServiceState.START.isNotIn(duplicated);
    assertThat(actual).isTrue();
  }

  @Test
  void startIsInErrorUnknown()
  {
    boolean actual = ServiceState.START.isIn(ServiceState.ERROR, ServiceState.UNKNOWN);
    assertThat(actual).isFalse();
    actual = ServiceState.START.isNotIn(ServiceState.ERROR, ServiceState.UNKNOWN);
    assertThat(actual).isTrue();
  }

  @Test
  void anyMatchIgnoreCase1()
  {
    List<Equalable.EqualableString> list = List.of(
      Equalable.EqualableString.of("a"),
      Equalable.EqualableString.of("b"),
      Equalable.EqualableString.of("C"),
      Equalable.EqualableString.of("D"));
    final boolean actual = Equalable.<Equalable.EqualableString>anyMatchT(list, p -> p.equalsIgnoreCase("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<Equalable.EqualableString>anyMatchT(list, p -> p.equalsIgnoreCase("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void anyMatchIgnoreCase3()
  {
    List<String> list = List.of("a", "b", "C", "D");
    final boolean actual = Equalable.<String>anyMatchIgnoreCase(list, "C");
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<String>anyMatchIgnoreCase(list, "c");
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void anyMatchIgnoreCase4()
  {
    List<String> list = List.of("a", "b", "C", "D");
    final boolean actual = Equalable.<String>anyMatchIgnoreCase(list, List.of("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<String>anyMatchIgnoreCase(list, List.of("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void anyMatchIgnoreCase5()
  {
    List<Equalable.EqualableString> list = List.of(
      Equalable.EqualableString.of("a"),
      Equalable.EqualableString.of("b"),
      Equalable.EqualableString.of("C"),
      Equalable.EqualableString.of("D"));
    final boolean actual = Equalable.<List<Equalable.EqualableString>>anyMatchIgnoreCase(list,
      p -> p.stream().map(o -> o.orElse(BLANK)).toList(), List.of("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<List<Equalable.EqualableString>>anyMatchIgnoreCase(list,
      p -> p.stream().map(Equalable.EqualableString::orElseBlank).toList(), List.of("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void anyMatchIgnoreCase6()
  {
    List<Equalable.EqualableString> list = List.of(
      Equalable.EqualableString.of("a"),
      Equalable.EqualableString.of("b"),
      Equalable.EqualableString.of("C"),
      Equalable.EqualableString.of("D"));
    final boolean actual = Equalable.<Equalable.EqualableString>anyMatchIgnoreCase(list, Equalable.EqualableString::orElseBlank, List.of("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<Equalable.EqualableString>anyMatchIgnoreCase(list, Equalable.EqualableString::orElseBlank, List.of("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void isNotIn()
  {
    final boolean actual = Equalable.EqualableString.of("a").isNotIn("A", "B");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.of("a").isNotIn("a", "b");
    assertThat(actual2).isFalse();
  }

  @Test
  void isNotInIgnoreCase()
  {
    final boolean actual = Equalable.EqualableString.of("a").isNotInIgnoreCase("A", "B");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.of("a").isNotInIgnoreCase("a", "b");
    assertThat(actual2).isFalse();
  }

  @Test
  void testIsNotIn()
  {
    final boolean actual = OK.isNotIn(State.ERROR, State.PENDING);
    assertThat(actual).isTrue();
    final boolean actual2 = OK.isNotIn(OK, ERROR);
    assertThat(actual2).isFalse();
  }

  @Test
  void isIn()
  {
    final boolean actual = Equalable.EqualableString.of("a").isIn("A", "B");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.of("a").isIn("a", "b");
    assertThat(actual2).isTrue();
  }

  @Test
  void isInIgnoreCase()
  {
    final boolean actual = Equalable.EqualableString.of("a").isInIgnoreCase("A", "B");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.of("a").isInIgnoreCase("a", "b");
    assertThat(actual2).isTrue();
  }

  @Test
  void testIsIn()
  {
    final boolean actual = OK.isIn(ERROR, PENDING);
    assertThat(actual).isFalse();
    final boolean actual2 = OK.isIn(OK, ERROR);
    assertThat(actual2).isTrue();
  }

  @Test
  void isNotEqualTo()
  {
    boolean actual = Equalable.EqualableString.of("a").isNotEqualTo(Equalable.EqualableString.of("A"));
    assertThat(actual).isTrue();
    boolean actual2 = Equalable.EqualableString.of("a").isNotEqualTo("A");
    assertThat(actual2).isTrue();
    boolean actual3 = Equalable.EqualableString.of("a").isNotEqualTo(Equalable.EqualableString.of("a"));
    assertThat(actual3).isFalse();
    boolean actual4 = Equalable.EqualableString.of("a").isNotEqualTo("a");
    assertThat(actual4).isFalse();
  }

  @Test
  void isEqualTo()
  {
    boolean actual = Equalable.EqualableString.of("a").isEqualTo(Equalable.EqualableString.of("A"));
    assertThat(actual).isFalse();
    boolean actual2 = Equalable.EqualableString.of("a").isEqualTo("A");
    assertThat(actual2).isFalse();
    boolean actual3 = Equalable.EqualableString.of("a").isEqualTo(Equalable.EqualableString.of("a"));
    assertThat(actual3).isTrue();
    boolean actual4 = Equalable.EqualableString.of("a").isEqualTo("a");
    assertThat(actual4).isTrue();
  }

  @Test
  void isNotEqualToIgnoreCase()
  {
    boolean actual = Equalable.EqualableString.of("a").isNotEqualToIgnoreCase(Equalable.EqualableString.of("A"));
    assertThat(actual).isFalse();
    boolean actual2 = Equalable.EqualableString.of("a").isNotEqualToIgnoreCase("A");
    assertThat(actual2).isFalse();
    boolean actual3 = Equalable.EqualableString.of("a").isNotEqualToIgnoreCase(Equalable.EqualableString.of("a"));
    assertThat(actual3).isFalse();
    boolean actual4 = Equalable.EqualableString.of("a").isNotEqualToIgnoreCase("a");
    assertThat(actual4).isFalse();
  }

  @Test
  void isEqualToIgnoreCase()
  {
    boolean actual = Equalable.EqualableString.of("a").isEqualToIgnoreCase(Equalable.EqualableString.of("A"));
    assertThat(actual).isTrue();
    boolean actual2 = Equalable.EqualableString.of("a").isEqualToIgnoreCase("A");
    assertThat(actual2).isTrue();
    boolean actual3 = Equalable.EqualableString.of("a").isEqualToIgnoreCase(Equalable.EqualableString.of("a"));
    assertThat(actual3).isTrue();
    boolean actual4 = Equalable.EqualableString.of("a").isEqualToIgnoreCase("a");
    assertThat(actual4).isTrue();
  }

  @Test
  void areNotEqual()
  {
    final boolean actual = Equalable.<Equalable.EqualableString>areNotEqual(Equalable.EqualableString.of("a"), Equalable.EqualableString.of("A"));
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.<Equalable.EqualableString>areNotEqual(Equalable.EqualableString.of("a"), Equalable.EqualableString.of("a"));
    assertThat(actual2).isFalse();
  }

  @Test
  void areEqual()
  {
    final boolean actual = Equalable.<Equalable.EqualableString>areEqual(Equalable.EqualableString.of("a"), Equalable.EqualableString.of("A"));
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.<Equalable.EqualableString>areEqual(Equalable.EqualableString.of("a"), Equalable.EqualableString.of("a"));
    assertThat(actual2).isTrue();
  }

  @Test
  void testAreNotEqual()
  {
    final boolean actual = Equalable.EqualableString.areNotEqual("a", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.areNotEqual("a", "a");
    assertThat(actual2).isFalse();
  }

  @Test
  void testAreEqual()
  {
    final boolean actual = Equalable.EqualableString.areEqual("a", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.areEqual("a", "a");
    assertThat(actual2).isTrue();
  }

  @Test
  void areNotEqualIgnoreCase()
  {
    final boolean actual = Equalable.EqualableString.areNotEqualIgnoreCase("a", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.areNotEqualIgnoreCase("a", "a");
    assertThat(actual2).isFalse();
  }

  @Test
  void areEqualIgnoreCase()
  {
    final boolean actual = Equalable.EqualableString.areEqualIgnoreCase("a", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.areEqualIgnoreCase("a", "a");
    assertThat(actual2).isTrue();
  }

  @Test
  void bothAreNotBlank()
  {
    final boolean actual = Equalable.EqualableString.bothAreNotBlank(" ", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.bothAreNotBlank("", " ");
    assertThat(actual2).isFalse();
  }

  @Test
  void bothAreBlank()
  {
    final boolean actual = Equalable.EqualableString.bothAreBlank(" ", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.bothAreBlank("", " ");
    assertThat(actual2).isTrue();
  }

  @Test
  void areNotTheSame()
  {
    final boolean actual = Equalable.EqualableString.areNotTheSame("", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.EqualableString.areNotTheSame("", "");
    assertThat(actual2).isFalse();
    final boolean actual3 = Equalable.EqualableString.areNotTheSame(BLANK, BLANK);
    assertThat(actual3).isFalse();
  }

  @Test
  void areTheSame()
  {
    final boolean actual = Equalable.EqualableString.areTheSame("", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.areTheSame("", "");
    assertThat(actual2).isTrue();
    final boolean actual3 = Equalable.EqualableString.areTheSame(BLANK, BLANK);
    assertThat(actual3).isTrue();
  }

  @Test
  void element()
  {
    final boolean actual = Equalable.EqualableString.of("String").isEqualTo("string");
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.EqualableString.of("String").isEqualTo("String");
    assertThat(actual2).isTrue();
  }

  @Test
  void equalableOfLong() {
    Equalable.EqualableLong actual = Equalable.ofLong(23L);
    assertThat(actual).isEqualTo(actual);
    assertThat(actual).isSameAs(actual);
    assertThat(actual).isEqualTo(Equalable.EqualableLong.of(23L));
    assertThat(actual).isNotSameAs(Equalable.EqualableLong.of(23L));
    assertThat(actual).isNotEqualTo(Equalable.EqualableLong.of(24L));
    assertThat(actual).isNotSameAs(Equalable.EqualableLong.of(23L));
    assertThat(actual.isEqualTo(23L)).isTrue();
    assertThat(actual.isSameAs(23L)).isTrue();
    Assertions.assertThat(actual.isSameAs(Equalable.EqualableLong.of(23L))).isFalse();
    Assertions.assertThat(actual.isEqualTo(Equalable.EqualableLong.of(23L))).isTrue();
  }

  public enum State implements Fluent<@NotNull State>
  {
    OK,
    ERROR,
    PENDING;
  }

}
