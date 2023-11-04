package de.ochmanski.immutables;


import de.ochmanski.immutables.constants.Constants;
import de.ochmanski.immutables.equalable.Equalable;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Value
@UnmodifiableView
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class Id implements Equalable<@NotNull Id>, Comparable<@NotNull Id>
{
  @NonNull
  @NotNull
  @Builder.Default
  String id = Constants.BLANK;

  @NotNull
  @Contract(value = "_ -> new", pure = true)
  public static Id of(@NotNull final String raw)
  {
    return Id.builder().id(raw).build();
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  public static Id blank()
  {
    return BLANK;
  }

  public static final Id BLANK = Id.builder().build();

  @Contract(value = "null -> true", pure = true)
  public boolean isNotEqualToIgnoreCase(@Nullable final String other)
  {
    return !isEqualToIgnoreCase(other);
  }

  @Contract(value = "null -> false", pure = true)
  public boolean isEqualToIgnoreCase(@Nullable final String other)
  {
    return id.equalsIgnoreCase(other);
  }

  public boolean anyMatch(@NotNull final Id @NotNull ... array)
  {
    return isIn(array);
  }

  public boolean anyMatch(@NotNull final List<? extends @NotNull Id> elements)
  {
    return isIn(elements);
  }

  public boolean allMatch(@NotNull final Id @NotNull ... array)
  {
    return allMatch(Arrays.asList(array));
  }

  public boolean allMatch(@NotNull final List<? extends @NotNull Id> elements)
  {
    return elements.stream().allMatch(this::isEqualTo);
  }

  public boolean noneMatch(@NotNull final Id @NotNull ... array)
  {
    return isNotIn(array);
  }

  public boolean noneMatch(@NotNull final List<? extends @NotNull Id> elements)
  {
    return isNotIn(elements);
  }

  public boolean isNotIn(@NotNull final Id @NotNull ... array)
  {
    return !isIn(array);
  }

  public boolean isNotIn(@NotNull final List<? extends @NotNull Id> elements)
  {
    return !isIn(elements);
  }

  public boolean isIn(@NotNull final Id @NotNull ... array)
  {
    return isIn(Arrays.asList(array));
  }

  public boolean isIn(@NotNull final List<? extends @NotNull Id> elements)
  {
    return elements.contains(this);
  }

  @NotNull
  public String orElse(@NotNull final String s)
  {
    return isBlank() ? s : id;
  }

  public boolean isNotBlank()
  {
    return !isBlank();
  }

  public boolean isBlank()
  {
    return null == id || id.isBlank();
  }

  //<editor-fold defaultstate="collapsed" desc="implementation of Comparable<Id> interface">
  @NonNull
  @NotNull
  @javax.validation.constraints.NotNull
  private static Comparator<@NonNull @NotNull
  @javax.validation.constraints.NotNull Id> ORDER_ASC = createOrderAscComparator();

  @NotNull
  @Contract(pure = true)
  static Comparator<@NotNull Id> orderAsc()
  {
    return ORDER_ASC;
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static Comparator<@NotNull Id> createOrderAscComparator()
  {
    return Comparator.nullsLast(
      Comparator.comparing(Id::getId, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(t -> t.getClass().getName(), Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(Id::hashCode));
  }

  @Override
  public int compareTo(@Nullable final Id o)
  {
    return orderAsc(this, o);
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     moduleMap.keySet().stream()
   *         .min(Id::orderAsc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     moduleMap.keySet().stream()
   *             .min(Id.orderAsc());
   *   }
   * </pre>
   * Compares two objects for order. Returns a negative integer, zero, or a positive integer if the first object is less
   * than, equal to, or greater than the second object. Any of the objects can be null. A null value is considered to be
   * less than a non-null value.
   *
   * @param a the first object.
   * @param b the second object.
   * @return an integer value.
   */
  public static int orderAsc(@Nullable final Id a, @Nullable final Id b)
  {
    if(a == b)
    {
      return 0;
    }
    return a != null ? b != null ? Id.orderAsc().compare(a, b) : -1 : 1;
  }
  //</editor-fold>

}
