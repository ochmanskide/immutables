package de.ochmanski.immutables.fluent;

import annotations.UnitTest;
import com.stadlerrail.diag.dias.servicestate.state.StateModule;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.stadlerrail.diag.dias.servicestate.state.StateModule.State.*;
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
    final List<StateModule.State> actual = StateModule.State.stream()
      .toList();
    assertThat(actual).containsExactly(ERROR, UNKNOWN, PENDING, OK);
  }

  @Test
  void forEach()
  {
    final List<Equalable<Fluent<StateModule.State>>> list = new LinkedList<>();
    StateModule.State.forEach(list::add);
    assertThat(list).containsExactly(ERROR, UNKNOWN, PENDING, OK);
  }

}
