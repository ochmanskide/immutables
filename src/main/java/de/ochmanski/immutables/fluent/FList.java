package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.immutable.IList;
import org.jetbrains.annotations.NotNull;

public interface FList<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends IList<@NotNull E>
{
}
