package de.ochmanski.immutables.equalable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;

public interface EqualableArray<T> extends Equalable<@NotNull EqualableArray<@NotNull T>> {

  @Contract(pure = true)
  static <T> boolean areNotEqual(@NotNull final T @NotNull [] a, @NotNull final T @NotNull [] b) {
    return !EqualableArray.areEqual(a, b);
  }

  @Contract(pure = true)
  static <T> boolean areEqual(@NotNull final T @NotNull [] a, @NotNull final T @NotNull [] b) {
    return EqualableArray.areTheSame(a, b);
  }

  @Contract(pure = true)
  static <T> boolean areNotTheSame(@NotNull final T @NotNull [] a, @NotNull final T @NotNull [] b) {
    return !EqualableArray.areTheSame(a, b);
  }

  @Contract(pure = true)
  static <T> boolean areTheSame(@NotNull final T @NotNull [] a, @NotNull final T @NotNull [] b) {
    return a == b;
  }

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  static <T> EqualableArray.@Unmodifiable ArrayHolder<@NotNull T> of(@NotNull final T @NotNull [] s) {
    return ArrayHolder.<@NotNull T>builder().s(s).build();
  }

  @Value
  @Builder
  @Unmodifiable
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class ArrayHolder<T> {

    @Nullable T @NotNull [] s;

    @Contract(pure = true)
    public boolean isNotDeepEqualTo(@NotNull final T @NotNull [] other) {
      return !isDeepEqualTo(other);
    }

    @Contract(pure = true)
    public boolean isDeepEqualTo(@Nullable final T @NotNull [] other) {
      return Arrays.deepEquals(s, other);
    }

    @Contract(pure = true)
    public boolean isNotSameAs(@NotNull final T @NotNull [] other) {
      return !isSameAs(other);
    }

    @Contract(pure = true)
    public boolean isSameAs(@NotNull final T @NotNull [] other) {
      return EqualableArray.<@NotNull T>areTheSame(s, other);
    }

    @Contract(pure = true)
    public boolean isNotNullAndNotEmpty() {
      return !isNullOrEmpty();
    }

    @Contract(pure = true)
    public boolean isNullOrEmpty() {
      return isNull() || isEmpty();
    }

    @Contract(pure = true)
    public boolean isNotNull() {
      return !isNull();
    }

    @Contract(pure = true)
    public boolean isNull() {
      return null == s;
    }

    @Contract(pure = true)
    public boolean isNotEmpty() {
      return !isEmpty();
    }

    @Contract(pure = true)
    public boolean isEmpty() {
      if (isNull()) {
        return false;
      }
      return hasSize(0);
    }

    @Contract(pure = true)
    public boolean doesntHaveSize(final int size) {
      return !hasSize(size);
    }

    @Contract(pure = true)
    public boolean hasSize(final int expected) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isSameAs(expected);
    }

    /**
     * Verifies that the number of values in the actual iterable is greater than the given boundary.
     * <p>
     * Example:
     * <pre><code class='java'> // assertion will pass
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeGreaterThan(2);
     *
     * // assertion will fail
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeGreaterThan(3);</code></pre>
     *
     * @param boundary the given value to compare the actual size to.
     * @return {@code this} assertion object.
     * @throws AssertionError if the number of values of the actual iterable is not greater than the boundary.
     * @since 3.12.0
     */
    public boolean hasSizeGreaterThan(final int boundary) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isGreaterThan(boundary);
    }

    /**
     * Verifies that the number of values in the actual iterable is greater than or equal to the given boundary.
     * <p>
     * Example:
     * <pre><code class='java'> // assertions will pass
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeGreaterThanOrEqualTo(1)
     *                                   .hasSizeGreaterThanOrEqualTo(3);
     *
     * // assertion will fail
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeGreaterThanOrEqualTo(4);</code></pre>
     *
     * @param boundary the given value to compare the actual size to.
     * @return {@code this} assertion object.
     * @throws AssertionError if the number of values of the actual iterable is not greater than or equal to the boundary.
     * @since 3.12.0
     */
    public boolean hasSizeGreaterThanOrEqualTo(final int boundary) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isGreaterThanOrEqualTo(boundary);
    }

    /**
     * Verifies that the number of values in the actual iterable is less than the given boundary.
     * <p>
     * Example:
     * <pre><code class='java'> // assertion will pass
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeLessThan(4);
     *
     * // assertion will fail
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeLessThan(3);</code></pre>
     *
     * @param boundary the given value to compare the actual size to.
     * @return {@code this} assertion object.
     * @throws AssertionError if the number of values of the actual iterable is not less than the boundary.
     * @since 3.12.0
     */
    public boolean hasSizeLessThan(final int boundary) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isLessThan(boundary);
    }

    /**
     * Verifies that the number of values in the actual iterable is less than or equal to the given boundary.
     * <p>
     * Example:
     * <pre><code class='java'> // assertions will pass
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeLessThanOrEqualTo(5)
     *                                   .hasSizeLessThanOrEqualTo(3);
     *
     * // assertion will fail
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeLessThanOrEqualTo(2);</code></pre>
     *
     * @param boundary the given value to compare the actual size to.
     * @return {@code this} assertion object.
     * @throws AssertionError if the number of values of the actual iterable is not less than or equal to the boundary.
     * @since 3.12.0
     */
    public boolean hasSizeLessThanOrEqualTo(final int boundary) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isLessThanOrEqualTo(boundary);
    }

    /**
     * Verifies that the number of values in the actual iterable is between the given boundaries (inclusive).
     * <p>
     * Example:
     * <pre><code class='java'> // assertions will pass
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeBetween(2, 3)
     *                                   .hasSizeBetween(3, 4)
     *                                   .hasSizeBetween(3, 3);
     *
     * // assertion will fail
     * assertThat(Arrays.asList(1, 2, 3)).hasSizeBetween(4, 6);</code></pre>
     *
     * @param lowerBoundary  the lower boundary compared to which actual size should be greater than or equal to.
     * @param higherBoundary the higher boundary compared to which actual size should be less than or equal to.
     * @return {@code this} assertion object.
     * @throws AssertionError if the number of values of the actual iterable is not between the boundaries.
     * @since 3.12.0
     */
    public boolean hasSizeBetween(final int lowerBoundary, final int higherBoundary) {
      if (isNull()) {
        return false;
      }
      return EqualableInteger.element(s.length).isBetweenInclusive(lowerBoundary, higherBoundary);
    }
  }
}
