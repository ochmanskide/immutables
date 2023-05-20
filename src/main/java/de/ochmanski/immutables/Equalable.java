/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.ochmanski.immutables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface Equalable<T> {

    @Contract(value = "null -> false", pure = true)
    default boolean isEqualTo(@Nullable T other) {
        return areEqual(this, other);
    }

    @Contract(value = "null, !null -> false; !null, null -> false", pure = true)
    static <S> boolean areEqual(@Nullable S a, @Nullable S b) {
        return Objects.equals(a, b);
    }
}
