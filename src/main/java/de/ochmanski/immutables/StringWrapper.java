package de.ochmanski.immutables;

import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Arrays;
import java.util.List;

@Value
@UnmodifiableView
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class StringWrapper implements Equalable<@NotNull StringWrapper>
{
  @NonNull
  @NotNull
  @Builder.Default
  String raw = Constants.BLANK;

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static StringWrapper of(@NotNull final String raw)
  {
    return StringWrapper.builder().raw(raw).build();
  }

  @Contract(value = "null -> true", pure = true)
  public boolean isNotEqualToIgnoreCase(@Nullable final String other)
  {
    return !isEqualToIgnoreCase(other);
  }

  @Contract(value = "null -> false", pure = true)
  public boolean isEqualToIgnoreCase(@Nullable final String other)
  {
    return raw.equalsIgnoreCase(other);
  }

  public boolean anyMatch(@NotNull final StringWrapper @NotNull ... array)
  {
    return isIn(array);
  }

  public boolean anyMatch(@NotNull final List<@NotNull StringWrapper> elements)
  {
    return isIn(elements);
  }

  public boolean allMatch(@NotNull final StringWrapper @NotNull ... array)
  {
    return allMatch(Arrays.asList(array));
  }

  public boolean allMatch(@NotNull final List<@NotNull StringWrapper> elements)
  {
    return elements.stream().allMatch(this::isEqualTo);
  }

  public boolean noneMatch(@NotNull final StringWrapper @NotNull ... array)
  {
    return isNotIn(array);
  }

  public boolean noneMatch(@NotNull final List<@NotNull StringWrapper> elements)
  {
    return isNotIn(elements);
  }

  public boolean isNotIn(@NotNull final StringWrapper @NotNull ... array)
  {
    return !isIn(array);
  }

  public boolean isNotIn(@NotNull final List<@NotNull StringWrapper> elements)
  {
    return !isIn(elements);
  }

  public boolean isIn(@NotNull final StringWrapper @NotNull ... array)
  {
    return isIn(Arrays.asList(array));
  }

  public boolean isIn(@NotNull final List<@NotNull StringWrapper> elements)
  {
    return elements.contains(this);
  }

}
