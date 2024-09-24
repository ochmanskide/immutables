package com.stadlerrail.diag.dias.immutables.fluent;

import org.jetbrains.annotations.NotNull;

public interface FluentList<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends FList<@NotNull E>
{
}
