package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import de.ochmanski.immutables.fluent.Fluent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static de.ochmanski.immutables.equalable.EqualableTest.State.*;
import static de.ochmanski.immutables.constants.Constants.BLANK;
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
    List<EqualableString> list = List.of(
      EqualableString.of("a"),
      EqualableString.of("b"),
      EqualableString.of("C"),
      EqualableString.of("D"));
    final boolean actual = Equalable.<EqualableString>anyMatchT(list, p -> p.equalsIgnoreCase("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<EqualableString>anyMatchT(list, p -> p.equalsIgnoreCase("c"));
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
    List<EqualableString> list = List.of(
      EqualableString.of("a"),
      EqualableString.of("b"),
      EqualableString.of("C"),
      EqualableString.of("D"));
    final boolean actual = Equalable.<List<EqualableString>>anyMatchIgnoreCase(list,
      p -> p.stream().map(o -> o.orElse(BLANK)).toList(), List.of("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<List<EqualableString>>anyMatchIgnoreCase(list,
      p -> p.stream().map(EqualableString::orElseBlank).toList(), List.of("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void anyMatchIgnoreCase6()
  {
    List<EqualableString> list = List.of(
      EqualableString.of("a"),
      EqualableString.of("b"),
      EqualableString.of("C"),
      EqualableString.of("D"));
    final boolean actual = Equalable.<EqualableString>anyMatchIgnoreCase(list, EqualableString::orElseBlank, List.of("C"));
    final boolean expected = true;
    assertThat(actual).isEqualTo(expected);
    final boolean actual2 = Equalable.<EqualableString>anyMatchIgnoreCase(list, EqualableString::orElseBlank, List.of("c"));
    assertThat(actual2).isEqualTo(expected);
  }

  @Test
  void isNotIn()
  {
    final boolean actual = EqualableString.of("a").isNotIn("A", "B");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.of("a").isNotIn("a", "b");
    assertThat(actual2).isFalse();
  }

  @Test
  void isNotInIgnoreCase()
  {
    final boolean actual = EqualableString.of("a").isNotInIgnoreCase("A", "B");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.of("a").isNotInIgnoreCase("a", "b");
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
    final boolean actual = EqualableString.of("a").isIn("A", "B");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.of("a").isIn("a", "b");
    assertThat(actual2).isTrue();
  }

  @Test
  void isInIgnoreCase()
  {
    final boolean actual = EqualableString.of("a").isInIgnoreCase("A", "B");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.of("a").isInIgnoreCase("a", "b");
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
    boolean actual = EqualableString.of("a").isNotEqualTo(EqualableString.of("A"));
    assertThat(actual).isTrue();
    boolean actual2 = EqualableString.of("a").isNotEqualTo("A");
    assertThat(actual2).isTrue();
    boolean actual3 = EqualableString.of("a").isNotEqualTo(EqualableString.of("a"));
    assertThat(actual3).isFalse();
    boolean actual4 = EqualableString.of("a").isNotEqualTo("a");
    assertThat(actual4).isFalse();
  }

  @Test
  void isEqualTo()
  {
    boolean actual = EqualableString.of("a").isEqualTo(EqualableString.of("A"));
    assertThat(actual).isFalse();
    boolean actual2 = EqualableString.of("a").isEqualTo("A");
    assertThat(actual2).isFalse();
    boolean actual3 = EqualableString.of("a").isEqualTo(EqualableString.of("a"));
    assertThat(actual3).isTrue();
    boolean actual4 = EqualableString.of("a").isEqualTo("a");
    assertThat(actual4).isTrue();
  }

  @Test
  void isNotEqualToIgnoreCase()
  {
    boolean actual = EqualableString.of("a").isNotEqualToIgnoreCase(EqualableString.of("A"));
    assertThat(actual).isFalse();
    boolean actual2 = EqualableString.of("a").isNotEqualToIgnoreCase("A");
    assertThat(actual2).isFalse();
    boolean actual3 = EqualableString.of("a").isNotEqualToIgnoreCase(EqualableString.of("a"));
    assertThat(actual3).isFalse();
    boolean actual4 = EqualableString.of("a").isNotEqualToIgnoreCase("a");
    assertThat(actual4).isFalse();
  }

  @Test
  void isEqualToIgnoreCase()
  {
    boolean actual = EqualableString.of("a").isEqualToIgnoreCase(EqualableString.of("A"));
    assertThat(actual).isTrue();
    boolean actual2 = EqualableString.of("a").isEqualToIgnoreCase("A");
    assertThat(actual2).isTrue();
    boolean actual3 = EqualableString.of("a").isEqualToIgnoreCase(EqualableString.of("a"));
    assertThat(actual3).isTrue();
    boolean actual4 = EqualableString.of("a").isEqualToIgnoreCase("a");
    assertThat(actual4).isTrue();
  }

  @Test
  void areNotEqual()
  {
    final boolean actual = Equalable.<EqualableString>areNotEqual(EqualableString.of("a"), EqualableString.of("A"));
    assertThat(actual).isTrue();
    final boolean actual2 = Equalable.<EqualableString>areNotEqual(EqualableString.of("a"), EqualableString.of("a"));
    assertThat(actual2).isFalse();
  }

  @Test
  void areEqual()
  {
    final boolean actual = Equalable.<EqualableString>areEqual(EqualableString.of("a"), EqualableString.of("A"));
    assertThat(actual).isFalse();
    final boolean actual2 = Equalable.<EqualableString>areEqual(EqualableString.of("a"), EqualableString.of("a"));
    assertThat(actual2).isTrue();
  }

  @Test
  void testAreNotEqual()
  {
    final boolean actual = EqualableString.areNotEqual("a", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.areNotEqual("a", "a");
    assertThat(actual2).isFalse();
  }

  @Test
  void testAreEqual()
  {
    final boolean actual = EqualableString.areEqual("a", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.areEqual("a", "a");
    assertThat(actual2).isTrue();
  }

  @Test
  void areNotEqualIgnoreCase()
  {
    final boolean actual = EqualableString.areNotEqualIgnoreCase("a", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.areNotEqualIgnoreCase("a", "a");
    assertThat(actual2).isFalse();
  }

  @Test
  void areEqualIgnoreCase()
  {
    final boolean actual = EqualableString.areEqualIgnoreCase("a", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.areEqualIgnoreCase("a", "a");
    assertThat(actual2).isTrue();
  }

  @Test
  void bothAreNotBlank()
  {
    final boolean actual = EqualableString.bothAreNotBlank(" ", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.bothAreNotBlank("", " ");
    assertThat(actual2).isFalse();
  }

  @Test
  void bothAreBlank()
  {
    final boolean actual = EqualableString.bothAreBlank(" ", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.bothAreBlank("", " ");
    assertThat(actual2).isTrue();
  }

  @Test
  void areNotTheSame()
  {
    final boolean actual = EqualableString.areNotTheSame("", "A");
    assertThat(actual).isTrue();
    final boolean actual2 = EqualableString.areNotTheSame("", "");
    assertThat(actual2).isFalse();
    final boolean actual3 = EqualableString.areNotTheSame(BLANK, BLANK);
    assertThat(actual3).isFalse();
  }

  @Test
  void areTheSame()
  {
    final boolean actual = EqualableString.areTheSame("", "A");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.areTheSame("", "");
    assertThat(actual2).isTrue();
    final boolean actual3 = EqualableString.areTheSame(BLANK, BLANK);
    assertThat(actual3).isTrue();
  }

  @Test
  void element()
  {
    final boolean actual = EqualableString.of("String").isEqualTo("string");
    assertThat(actual).isFalse();
    final boolean actual2 = EqualableString.of("String").isEqualTo("String");
    assertThat(actual2).isTrue();
  }

  @Test
  void equalableOfLong() {
    EqualableLong actual = Equalable.ofLong(23L);
    assertThat(actual).isEqualTo(actual);
    assertThat(actual).isSameAs(actual);
    assertThat(actual).isEqualTo(EqualableLong.of(23L));
    assertThat(actual).isNotSameAs(EqualableLong.of(23L));
    assertThat(actual).isNotEqualTo(EqualableLong.of(24L));
    assertThat(actual).isNotSameAs(EqualableLong.of(23L));
    assertThat(actual.isEqualTo(23L)).isTrue();
    assertThat(actual.isSameAs(23L)).isTrue();
    assertThat(actual.isSameAs(EqualableLong.of(23L))).isFalse();
    assertThat(actual.isEqualTo(EqualableLong.of(23L))).isTrue();
  }

  public enum State implements Fluent<@NotNull State>
  {
    OK,
    ERROR,
    PENDING;
  }

}
