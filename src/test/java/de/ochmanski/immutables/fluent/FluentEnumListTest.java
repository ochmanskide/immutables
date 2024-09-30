package de.ochmanski.immutables.fluent;

import annotations.UnitTest;
import de.ochmanski.immutables.fluent.Fluent;
import de.ochmanski.immutables.fluent.FluentEnumList;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@ExtendWith(MockitoExtension.class)
public class FluentEnumListTest
{
  @Test
  void ofNullClass()
  {
    assertThatThrownBy(() -> FluentEnumList.noneOf(null))
      .isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class)
      .satisfiesAnyOf(
        npe -> assertThat(npe).isInstanceOfAny(NullPointerException.class, IllegalArgumentException.class),
        p -> assertThat(p).hasMessage("Argument for @NotNull parameter 'constructor' of de/ochmanski/immutables/ImmutableList.of must not be null")
      );
  }

  @Test
  void empty()
  {
    @NotNull final FluentEnumList<Fluent.@NotNull Dummy> actual = FluentEnumList.empty();
    assertThat(actual).isInstanceOf(FluentEnumList.class);
    final Class<Fluent.@NotNull Dummy> componentTypeFromKey = actual.getComponentTypeFromKey();
    assertThat(componentTypeFromKey).isSameAs(Fluent.Dummy.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    final Fluent.@NotNull Dummy[] array = actual.toArray();
    assertThat(array).isEmpty();
  }

  @Test
  void of0()
  {
    final FluentEnumList<@NotNull Dummy> actual = FluentEnumList.<@NotNull Dummy>noneOf(
      Dummy[]::new);
    assertThat(actual.getClass()).isSameAs(FluentEnumList.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(Dummy.class);
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(Dummy.class);
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass()).isSameAs(Dummy[].class);
    assertThat(actual.unwrap().toArray()).isNotNull();
    assertThat(actual.unwrap().toArray()).isEmpty();
    assertThat(actual.unwrap().toArray().getClass()).isSameAs(Object[].class);
  }

  @Test
  void of1()
  {
    final Dummy e1 = Dummy.A;
    final FluentEnumList<@NotNull Dummy> actual = FluentEnumList.of(e1,
      Dummy[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(Dummy.A)
      .hasSize(1)
      .containsExactlyElementsOf(actual.unwrap());
  }

  @Test
  void of2()
  {
    final Dummy e1 = Dummy.A;
    final Dummy e2 = Dummy.A;
    final FluentEnumList<@NotNull Dummy> actual = FluentEnumList.of(e1, e2,
      Dummy[]::new);
    assertThat(actual.unwrap())
      .containsExactly(Dummy.A, Dummy.A)
      .hasSize(2)
      .containsExactlyElementsOf(actual.unwrap());
  }

  private enum Dummy implements Fluent<@NotNull Dummy>
  {
    A,
    B,
    C;
  }

}
