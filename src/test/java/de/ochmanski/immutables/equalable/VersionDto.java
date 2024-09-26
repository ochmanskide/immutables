package de.ochmanski.immutables.equalable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotBlank;
import java.util.Comparator;

import static com.stadlerrail.diag.dias.immutables.equalable.Equalable.EqualableString.blank;

@Value
@Unmodifiable
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@FieldNameConstants
public class VersionDto implements Comparable<@NotNull VersionDto>, Equalable<@NotNull VersionDto> {

  @Builder.Default
  int id = 0;

  @NotNull
  @Unmodifiable
  @Builder.Default
  EqualableString
    name = blank(),
    originalName = blank(),
    currentVersion = blank(),
    rawVersion = blank(),
    location = blank(),
    system = blank(),
    signalName = blank();

  @Builder.Default
  boolean valid = false;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Builder.Default
  EqualableList<@NotNull EqualableString> expectedVersion = EqualableList.noneOf(EqualableString[]::new);

  @NotNull
  @Unmodifiable
  @Builder.Default
  ServiceState state = ServiceState.RUN_SLAVE;

  //<editor-fold defaultstate="collapsed" desc="helper methods">
  @Contract(pure = true)
  public boolean nameDoesNotStartWith(@NotNull final String prefix) {
    return getName().mapToObj(p -> !p.startsWith(prefix)).orElse(false);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="VersionDto.empty()">
  @Contract(pure = true)
  public boolean isNotEmpty() {
    return !isEmpty();
  }

  @Contract(pure = true)
  public boolean isEmpty() {
    return isEqualTo(EMPTY);
  }

  @Contract(value = "null -> false", pure = true)
  public static boolean isEmpty(@Nullable final VersionDto other) {
    return VersionDto.empty().isEqualTo(other);
  }

  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  public static VersionDto empty() {
    return EMPTY;
  }

  /**
   * Load and store value eagerly, for performance boost.
   */
  @NotNull
  @Unmodifiable
  private static final VersionDto EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  private static VersionDto createConstant() {
    return VersionDto.builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Optional<@Nullable T> getters for @Nullable fields">

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Comparable<VersionDto>">

  /**
   * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive integer
   * as this object is less than, equal to, or greater than the specified object.
   *
   * <p>The implementor must ensure {@link Integer#signum
   * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for all {@code x} and {@code y}.  (This implies that
   * {@code x.compareTo(y)} must throw an exception if and only if {@code y.compareTo(x)} throws an exception.)
   *
   * <p>The implementor must also ensure that the relation is transitive:
   * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies {@code x.compareTo(z) > 0}.
   *
   * <p>Finally, the implementor must ensure that {@code
   * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z)) == signum(y.compareTo(z))}, for all {@code z}.
   *
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
   * specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException   if the specified object's type prevents it from being compared to this object.
   * @apiNote It is strongly recommended, but <i>not</i> strictly required that
   * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any class that implements the
   * {@code Comparable} interface and violates this condition should clearly indicate this fact.  The recommended
   * language is "Note: this class has a natural ordering that is inconsistent with equals."
   */
  @Override
  public int compareTo(@NotNull final VersionDto o) {
    return orderAsc(this, o);
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().min(VersionDto::orderAsc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().min(VersionDto.orderAsc());
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
  @Contract(pure = true)
  public static int orderAsc(@Nullable final VersionDto a, @Nullable final VersionDto b) {
    return a == b ? 0 : a != null ? b != null ? orderAsc().compare(a, b) : -1 : 1;
  }

  /**
   * Special null-safe wrapper for default comparator method. Now it is possible to write code using lazy method
   * reference instead of eagerly invoking a builder:
   *
   * <pre>
   *   {@code
   *     dtos.stream().max(VersionDto::orderDesc);
   *   }
   * </pre>
   * instead of:
   * <pre>
   *   {@code
   *     dtos.stream().max(VersionDto.orderDesc());
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
  @Contract(pure = true)
  public static int orderDesc(@Nullable final VersionDto a, @Nullable final VersionDto b) {
    return a == b ? 0 : a != null ? b != null ? orderDesc().compare(a, b) : 1 : -1;
  }

  @NotNull
  @Contract(pure = true)
  public static Comparator<@NotNull VersionDto> orderAsc() {
    return ORDER_BY_ID_COMPARATOR_ASCENDING;
  }

  @NotNull
  @Contract(pure = true)
  public static Comparator<@NotNull VersionDto> orderDesc() {
    return ORDER_BY_ID_COMPARATOR_DESCENDING;
  }

  @NotNull
  private static final Comparator<@NotNull VersionDto> ORDER_BY_ID_COMPARATOR_ASCENDING = createComparatorAsc();

  @NotNull
  private static final Comparator<@NotNull VersionDto> ORDER_BY_ID_COMPARATOR_DESCENDING = createComparatorDesc();

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static Comparator<@NotNull VersionDto> createComparatorAsc() {
    return Comparator.nullsLast(
      Comparator.comparingInt(VersionDto::getId)
        .thenComparing(VersionDto::getName, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getCurrentVersion, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(VersionDto::isValid, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getSignalName, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getExpectedVersion, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getState, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(t -> t.getClass().getName(), Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparingInt(VersionDto::hashCode));
  }

  @NotNull
  @Contract(value = "-> new", pure = true)
  private static Comparator<@NotNull VersionDto> createComparatorDesc() {
    return Comparator.nullsFirst(
      Comparator.comparingInt(VersionDto::getId)
        .thenComparing(VersionDto::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getCurrentVersion, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(VersionDto::isValid, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getSignalName, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getExpectedVersion, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(VersionDto::getState, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(t -> t.getClass().getName(), Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparingInt(VersionDto::hashCode));
  }

  @NonNls
  @NotNull
  @NotBlank
  @Unmodifiable
  @Contract(pure = true)
  public String getClassName() {
    return this.getClass().getName();
  }
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="Constants">
  interface Constants {

  }
  //</editor-fold>
}
