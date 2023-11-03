package de.ochmanski.immutables.fluent;

import annotations.UnitTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FluentEnumSetTest
{


  @Test
  void toArray()
  {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.DDS,
      FluentExample.DDS,
      FluentExample.OPDATA,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA).hasSize(2);
  }

  @Test
  void toArray2()
  {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.DDS,
      FluentExample.DDS,
      FluentExample.OPDATA,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA).hasSize(2);
  }

  @Test
  void toArrayGenerator()
  {
    FluentEnumSet<@NotNull FluentExample> unitUnderTest = FluentEnumSet.of(
      FluentExample.DDS,
      FluentExample.DDS,
      FluentExample.OPDATA,
      FluentExample[]::new);
    final FluentExample[] actual = unitUnderTest.toArray();
    assertThat(actual).containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA).hasSize(2);
  }

  @Test
  void of()
  {
    final FluentExample e1 = FluentExample.DDS;
    final FluentExample e2 = FluentExample.DDS;
    final FluentExample e3 = FluentExample.OPDATA;
    final FluentExample[] enums = { e1, e2, e3 };
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.ofArray(enums, FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA)
      .hasSize(2);
  }

  @Test
  void of0()
  {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.<@NotNull FluentExample>ofGenerator(
      FluentExample[]::new);
    assertThat(actual.getClass()).isSameAs(FluentEnumSet.class);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual.unwrap().toArray()).isNotNull();
    assertThat(actual.unwrap().toArray()).isEmpty();
    assertThat(actual.unwrap().toArray().getClass()).isSameAs(Object[].class);
  }

  @Test
  void ofGivenClass()
  {
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.<@NotNull FluentExample>ofGenerator(
      FluentExample[]::new);
    assertThat(actual.unwrap()).isEmpty();
    assertThat(actual.isEmpty()).isTrue();
    assertThat(actual.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual.toArray()).isNotNull();
    assertThat(actual.toArray()).isEmpty();
    assertThat(actual.toArray()).extracting(FluentExample::getClass).isEmpty();
    assertThat(actual.toArray()).extracting(FluentExample::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).extracting(Object::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).extracting(Object::getClass).isEmpty();
    assertThat(actual.unwrap().toArray()).hasSize(0);
    assertThat(actual.unwrap().toArray()).isNotNull();
    assertThat(actual.unwrap().toArray()).isEmpty();
    assertThat(actual.unwrap().toArray().getClass()).isNotNull();

  }

  @Test
  void ofElement()
  {
    final FluentEnumSet<@NotNull FluentExample> actual2 = FluentEnumSet.of(FluentExample.DDS,
      FluentExample[]::new);
    assertThat(actual2.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual2.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual2.toArray()).isNotEmpty();
    assertThat(actual2.toArray()).isNotNull();
    assertThat(actual2.toArray()).containsExactly(FluentExample.DDS);
    assertThat(actual2.toArray().getClass()).isSameAs(FluentExample[].class);
    assertThat(actual2.toArray()).extracting(Object::getClass).containsExactly(new Class[] { FluentExample.class });
    assertThat(actual2.toArray().getClass().getComponentType()).isSameAs(FluentExample.class);
    assertThat(actual2.unwrap().toArray().getClass()).isSameAs(Object[].class);
    assertThat(actual2.unwrap().toArray().getClass().getComponentType()).isSameAs(Object.class);
    assertThat(actual2.unwrap().toArray().getClass().getComponentType()).isEqualTo(Object.class);
    assertThat(actual2.unwrap().toArray()).isNotNull();
    assertThat(actual2.unwrap().toArray()).isNotEmpty();
    assertThat(actual2.unwrap().toArray()).containsExactly(FluentExample.DDS);
    assertThat(actual2.unwrap().toArray().getClass()).isSameAs(Object[].class);
  }

  @Test
  void of1()
  {
    final FluentExample e1 = FluentExample.DDS;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.DDS)
      .hasSize(1)
      .containsExactlyElementsOf(actual.unwrap());
  }

  @Test
  void of2()
  {
    final FluentExample e1 = FluentExample.DDS;
    final FluentExample e2 = FluentExample.DDS;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.DDS)
      .hasSize(1)
      .containsExactlyElementsOf(actual.unwrap());
  }

  @Test
  void of3()
  {
    final FluentExample e1 = FluentExample.DDS;
    final FluentExample e2 = FluentExample.DDS;
    final FluentExample e3 = FluentExample.OPDATA;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2, e3,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA)
      .hasSize(2)
      .containsExactlyInAnyOrderElementsOf(actual.unwrap());
  }

  @Test
  void of4()
  {
    final FluentExample e1 = FluentExample.DDS;
    final FluentExample e2 = FluentExample.DDS;
    final FluentExample e3 = FluentExample.OPDATA;
    final FluentExample e4 = FluentExample.SWV;
    final FluentEnumSet<@NotNull FluentExample> actual = FluentEnumSet.of(e1, e2, e3, e4,
      FluentExample[]::new);
    assertThat(actual.unwrap())
      .containsOnlyOnce(FluentExample.DDS, FluentExample.OPDATA, FluentExample.SWV)
      .hasSize(3)
      .containsExactlyInAnyOrderElementsOf(actual.unwrap());
  }
}
