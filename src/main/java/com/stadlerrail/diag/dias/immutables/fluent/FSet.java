package com.stadlerrail.diag.dias.immutables.fluent;

import com.stadlerrail.diag.dias.immutables.immutable.ISet;
import org.jetbrains.annotations.NotNull;

public interface FSet<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>> extends FluentCollection<@NotNull E>, ISet<@NotNull E>
{
}
