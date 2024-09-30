package de.ochmanski.immutables.equalable;

import annotations.UnitTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EqualableSortedSetTest
{

  @Test
  void of1()
  {
    final Iterable<Equalable.@NotNull Dummy> actual = EqualableSortedSet.of(Equalable.Dummy.DUMMY_ENUM_ITEM, Equalable.Dummy[]::new);
    assertThat(actual).containsExactly(Equalable.Dummy.DUMMY_ENUM_ITEM);
  }

  @Test
  void ofStream()
  {
    final Iterable<Equalable.@NotNull Dummy> actual = Stream.of(
        Equalable.Dummy.DUMMY_ENUM_ITEM,
        Equalable.Dummy.DUMMY_ENUM_ITEM,
        Equalable.Dummy.DUMMY_ENUM_ITEM,
        Equalable.Dummy.DUMMY_ENUM_ITEM)
      .collect(EqualableCollectors.toSortedSet(Equalable.Dummy[]::new));
    assertThat(actual).containsExactly(Equalable.Dummy.DUMMY_ENUM_ITEM);
  }

  @Test
  void ofStream2()
  {
    final Iterable<VersionDto> actual = Stream.of(
        VersionDto.builder()
          .name(Equalable.of("Version 3"))
          .state(ServiceState.RUN_SLAVE)
          .valid(true)
          .build(),
        VersionDto.builder()
          .name(Equalable.of("Version 1"))
          .state(ServiceState.START)
          .valid(true)
          .build(),
        VersionDto.builder()
          .name(Equalable.of("Version 3"))
          .state(ServiceState.INIT)
          .valid(true)
          .build(),
        VersionDto.builder()
          .name(Equalable.of("Version 2"))
          .state(ServiceState.CREATE)
          .valid(true)
          .build())
      .collect(EqualableCollectors.toSortedSet(VersionDto[]::new));
    assertThat(actual)
      .extracting(VersionDto::getName)
      .extracting(Equalable.EqualableString::orElseBlank)
      .containsExactly("Version 1", "Version 2", "Version 3", "Version 3");
  }
}
