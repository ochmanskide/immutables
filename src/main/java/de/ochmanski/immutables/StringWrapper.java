package de.ochmanski.immutables;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;

@Value
@Unmodifiable
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class StringWrapper implements Equalable<@NotNull StringWrapper> {

  @NonNull
  @NotNull
  @Builder.Default
  String raw = Constants.BLANK;

  @NotNull
  @Unmodifiable
  @Contract(value = "_ -> new", pure = true)
  public static StringWrapper of(@NotNull final String raw) {
    return StringWrapper.builder().raw(raw).build();
  }

  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  public static StringWrapper blank() {
    return BLANK;
  }

  @NotNull
  @Unmodifiable
  private static final StringWrapper BLANK = create();

  @NotNull
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  private static StringWrapper create() {
    return StringWrapper.builder().build();
  }

  @Contract(value = "null -> true", pure = true)
  public boolean isNotEqualToIgnoreCase(@Nullable final StringWrapper other) {
    return !isEqualToIgnoreCase(other);
  }

  @Contract(value = "null -> false", pure = true)
  public boolean equalsIgnoreCase(@Nullable final String other) {
    return isEqualToIgnoreCase(other);
  }

  @Contract(value = "null -> false", pure = true)
  public boolean isEqualToIgnoreCase(@Nullable final StringWrapper other) {
    if (null == other) {
      return false;
    }
    return EqualableString.areEqualIgnoreCase(raw, other.getRaw());
  }

  public boolean anyMatch(@NotNull final String @NotNull ... array) {
    return isIn(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean allMatch(@NotNull final String @NotNull ... array) {
    return allMatch(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean noneMatch(@NotNull final String @NotNull ... array) {
    return isNotIn(array);
  }

  public boolean isNotIn(@NotNull final String @NotNull ... array) {
    return !isIn(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean isIn(@NotNull final String @NotNull ... array) {
    return isIn(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean isNotInIgnoreCase(@NotNull final String @NotNull ... array) {
    return isNotInIgnoreCase(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean isNotInIgnoreCase(@NotNull final List<@NotNull StringWrapper> elements) {
    return Equalable.<@NotNull StringWrapper>noneMatchT(elements, p -> p.isEqualToIgnoreCase(this));
  }

  public boolean isInIgnoreCase(@NotNull final String @NotNull ... array) {
    return isInIgnoreCase(Arrays.stream(array).map(StringWrapper::of).toList());
  }

  public boolean isInIgnoreCase(@NotNull final List<@NotNull StringWrapper> elements) {
    return Equalable.<@NotNull StringWrapper>anyMatchT(elements, p -> p.isEqualToIgnoreCase(this));
  }

  @NotNull
  public String orElse(@NotNull final String s) {
    return isBlank() ? s : raw;
  }

  @JsonIgnore
  public boolean isNotBlank() {
    return !isBlank();
  }

  @JsonIgnore
  public boolean isBlank() {
    return Equalable.<@NotNull StringWrapper>areTheSame(this, BLANK) || raw.isBlank();
  }

  public boolean isNotEqualTo(final String a) {
    return !isEqualTo(a);
  }

  @Contract(value = "null -> false", pure = true)
  public boolean isEqualTo(@Nullable final String other) {
    return Equalable.<@NotNull String>areEqual(raw, other);
  }


  /**
   * This method is Null-safe. It cannot throw {@link NullPointerException}
   */
  @Contract(value = "null -> true", pure = true)
  public boolean isNotEqualToIgnoreCase(@Nullable final String other) {
    return !isEqualToIgnoreCase(other);
  }

  /**
   * This method is Null-safe. It cannot throw {@link NullPointerException}
   */
  @Contract(value = "null -> false", pure = true)
  public boolean isEqualToIgnoreCase(@Nullable final String other) {
    return EqualableString.areEqualIgnoreCase(raw, other);
  }

  public boolean anyMatch(@NotNull final StringWrapper @NotNull ... array) {
    return isIn(array);
  }

  public boolean anyMatch(@NotNull final List<@NotNull StringWrapper> elements) {
    return isIn(elements);
  }

  public boolean allMatch(@NotNull final StringWrapper @NotNull ... array) {
    return allMatch(Arrays.asList(array));
  }

  public boolean allMatch(@NotNull final List<@NotNull StringWrapper> elements) {
    return elements.stream().allMatch(this::isEqualTo);
  }

  public boolean noneMatch(@NotNull final StringWrapper @NotNull ... array) {
    return isNotIn(array);
  }

  public boolean noneMatch(@NotNull final List<@NotNull StringWrapper> elements) {
    return isNotIn(elements);
  }

  public boolean isNotIn(@NotNull final StringWrapper @NotNull ... array) {
    return !isIn(array);
  }

  public boolean isNotIn(@NotNull final List<@NotNull StringWrapper> elements) {
    return !isIn(elements);
  }

  public boolean isIn(@NotNull final StringWrapper @NotNull ... array) {
    return isIn(Arrays.asList(array));
  }

  public boolean isIn(@NotNull final List<@NotNull StringWrapper> elements) {
    return elements.contains(this);
  }

  @NotNull
  @Override
  @Unmodifiable
  public String toString() {
    return raw;
  }
}
