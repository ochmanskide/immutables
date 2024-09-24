package de.ochmanski.immutables.fluent;

import org.jetbrains.annotations.NotNull;

public interface FluentCollection<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends FCollection<@NotNull E>
{
}
