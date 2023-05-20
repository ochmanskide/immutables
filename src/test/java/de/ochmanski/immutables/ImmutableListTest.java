package de.ochmanski.immutables;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImmutableListTest
{

  @Test
  void of()
  {
    final Dummy s = new Dummy();
    final IList<Dummy> unitUnderTest = IList.of(s);
    when(dependency.of(any())).thenReturn(dummy());
    actual = unitUnderTest.of();
    expected = "expectedValue";
    assertThat(actual).isEqualTo(expected);
  }

  private static class Dummy
  {
    String s = "dummy";

  }

}
