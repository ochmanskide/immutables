package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.equalable.Equalable;
import org.jetbrains.annotations.NotNull;

public interface FluentMap<K extends @NotNull Enum<@NotNull K> & @NotNull Fluent<? extends @NotNull K>, V extends @NotNull Equalable<? extends @NotNull V>>
  extends FMap<@NotNull K, @NotNull V>
{
}
