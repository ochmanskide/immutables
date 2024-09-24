package com.stadlerrail.diag.dias.immutables.fluent;

import com.stadlerrail.diag.dias.immutables.equalable.Equalable;
import com.stadlerrail.diag.dias.immutables.immutable.IMap;
import org.jetbrains.annotations.NotNull;

public interface FMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  extends IMap<@NotNull K, @NotNull V>
{
}
