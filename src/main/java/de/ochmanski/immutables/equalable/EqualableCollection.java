package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.immutable.ImmutableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.function.IntFunction;

public interface EqualableCollection<E extends @NotNull Comparable<@NotNull E> & @NotNull Equalable<@NotNull E>> extends ECollection<@NotNull E> {

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #noneOf(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = EqualableList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = EqualableList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = EqualableList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
      + "For example: EqualableList.noneOf(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = EqualableList.noneOf(Dummy[]::new);
   *   final IList<DayOfWeek> actual = EqualableList.noneOf(DayOfWeek[]::new);
   *   final IList<Month> actual = EqualableList.noneOf(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends @NotNull Comparable<@NotNull S> & @NotNull Equalable<@NotNull S>> ECollection<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableList.<@NotNull S>of(ImmutableList.<@NotNull S>noneOf(constructor));
  }
  //</editor-fold>

  @Override
  @Contract(pure = true)
  int compareTo(@NotNull final ECollection<@NotNull E> o);
}
