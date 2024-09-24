package com.stadlerrail.diag.dias.immutables.fluent;

import com.stadlerrail.diag.dias.immutables.immutable.ImmutableCollection;
import org.jetbrains.annotations.NotNull;

public interface FCollection<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends ImmutableCollection<@NotNull E>
{
}
