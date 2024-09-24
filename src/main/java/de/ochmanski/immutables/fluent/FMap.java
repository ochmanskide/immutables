package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.immutable.IMap;
import org.jetbrains.annotations.NotNull;

public interface FMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  extends IMap<@NotNull K, @NotNull V>
{
}
