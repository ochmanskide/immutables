package com.stadlerrail.diag.dias.immutables.fluent;

import com.stadlerrail.diag.dias.immutables.equalable.Equalable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.IntFunction;

public interface FluentMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  extends FMap<@NotNull K, @NotNull V>
{
}
