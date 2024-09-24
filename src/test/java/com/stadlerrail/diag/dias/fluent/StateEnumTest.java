package com.stadlerrail.diag.dias.fluent;

import annotations.UnitTest;
import com.stadlerrail.diag.dias.fluent.FluentTest.State;
import com.stadlerrail.diag.dias.immutables.equalable.Equalable;
import com.stadlerrail.diag.dias.immutables.fluent.Fluent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.stadlerrail.diag.dias.fluent.FluentTest.State.*;
import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
class StateEnumTest
{

  @Test
  void isEqualTo()
  {
    final boolean actual = OK.isEqualTo(OK);
    assertThat(actual).isTrue();
    final boolean actual2 = OK.isEqualTo(ERROR);
    assertThat(actual2).isFalse();
  }

  @Test
  void isNotEqualTo()
  {
    final boolean actual = OK.isNotEqualTo(ERROR);
    assertThat(actual).isTrue();
    final boolean actual2 = OK.isNotEqualTo(OK);
    assertThat(actual2).isFalse();
  }

  @Test
  void isIn()
  {
    final boolean actual = OK.isIn(OK, ERROR);
    assertThat(actual).isTrue();
    final boolean actual2 = OK.isIn(ERROR, PENDING);
    assertThat(actual2).isFalse();
  }

  @Test
  void isNotIn()
  {
    final boolean actual = OK.isNotIn(ERROR, PENDING);
    assertThat(actual).isTrue();
    final boolean actual2 = OK.isNotIn(OK, ERROR);
    assertThat(actual2).isFalse();
  }

  @Test
  void stream()
  {
    final List<@NotNull State> actual = State.stream().toList();
    assertThat(actual).containsExactlyInAnyOrder(ERROR, UNKNOWN, PENDING, OK);
  }

  @Test
  void sortedStream()
  {
    final List<@NotNull State> actual = State.sortedStream().toList();
    assertThat(actual).containsExactly(ERROR, UNKNOWN, PENDING, OK);
  }

  @Test
  void forEach()
  {
    final List<@NotNull Equalable<@NotNull Fluent<@NotNull State>>> actual = new LinkedList<>();
    State.forEach(actual::add);
    assertThat(actual).containsExactlyInAnyOrder(UNKNOWN, OK, PENDING, ERROR);
  }

  @Test
  void forEachOrdered()
  {
    LinkedList<State> actual = new LinkedList<>();
    State.forEachOrdered(actual::add);
    assertThat(actual).containsExactly(ERROR, UNKNOWN, PENDING, OK);
    final State[] expected = State.values();
    assertThat(actual).containsExactly(expected);
  }
}
