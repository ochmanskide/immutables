package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.immutable.ImmutableCollection;
import org.jetbrains.annotations.NotNull;

public interface FCollection<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends ImmutableCollection<@NotNull E>
{
}
