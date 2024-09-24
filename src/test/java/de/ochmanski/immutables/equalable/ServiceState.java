package de.ochmanski.immutables.equalable;

import de.ochmanski.immutables.fluent.Fluent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static de.ochmanski.immutables.equalable.ServiceState.Constants.State.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ServiceState implements Fluent<@NotNull ServiceState>, Comparable<@NotNull ServiceState>
{

  UNKNOWN(0, UNKNOWN_DEFAULT),
  START(1, START_DEFAULT),
  CREATE(2, CREATE_DEFAULT),
  INIT(3, INIT_DEFAULT),
  RUN_SLAVE(4, RUN_SLAVE_DEFAULT),
  RUN_MASTER(5, RUN_MASTER_DEFAULT),
  SHUTDOWN(6, SHUTDOWN_DEFAULT),
  SLEEP(7, SLEEP_DEFAULT),
  ERROR(8, ERROR_DEFAULT),
  UNIMPLEMENTED(9, UNIMPLEMENTED_DEFAULT);

  /**
   * State position roughly in expected progression order should not be used for determining previous or next state
   */
  @PositiveOrZero
  @MagicConstant(intValues = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
  @Min(value = 0, message = "ServiceState.position should not be less than {value}. Actual value: ${validatedValue}")
  int position;

  @NotNull
  @MagicConstant(valuesFromClass = ServiceState.Constants.State.class)
  @NotBlank(message = "ServiceState.text should not be blank. Actual value: ${validatedValue}")
  String text;

  @NotNull
  public static final EnumSet<@NotNull ServiceState> EMPTY = EnumSet.noneOf(ServiceState.class);

  /**
   * Load and store values eagerly, for performance boost.
   */
  @NotNull
  private static final ServiceState @NotNull [] ENTRIES = ServiceState.values();

  @NotNull
  @Contract(value = " -> new", pure = true)
  public static Stream<@NotNull Equalable<@NotNull Fluent<@NotNull ServiceState>>> stream()
  {
    return Fluent.createStream(ENTRIES);
  }

  @Contract(pure = true)
  public static void forEach(
    @NotNull final Consumer<? super @NotNull Equalable<@NotNull Fluent<@NotNull ServiceState>>> consumer)
  {
    Fluent.forEach(ENTRIES, consumer);
  }

  @Contract(pure = true)
  public boolean isNotUnknown()
  {
    return !isUnknown();
  }

  @Contract(pure = true)
  public boolean isUnknown()
  {
    return isSameAs(UNKNOWN);
  }

  @Contract(pure = true)
  public boolean isNotStart()
  {
    return !isStart();
  }

  @Contract(pure = true)
  public boolean isStart()
  {
    return isSameAs(START);
  }

  @Contract(pure = true)
  public boolean isNotCreate()
  {
    return !isCreate();
  }

  @Contract(pure = true)
  public boolean isCreate()
  {
    return isSameAs(CREATE);
  }

  @Contract(pure = true)
  public boolean isNotInit()
  {
    return !isInit();
  }

  @Contract(pure = true)
  public boolean isInit()
  {
    return isSameAs(INIT);
  }

  @Contract(pure = true)
  public boolean isNotRunSlave()
  {
    return !isRunSlave();
  }

  @Contract(pure = true)
  public boolean isRunSlave()
  {
    return isSameAs(RUN_SLAVE);
  }

  @Contract(pure = true)
  public boolean isNotRunMaster()
  {
    return !isRunMaster();
  }

  @Contract(pure = true)
  public boolean isRunMaster()
  {
    return isSameAs(RUN_MASTER);
  }

  @Contract(pure = true)
  public boolean isNotShutdown()
  {
    return !isShutdown();
  }

  @Contract(pure = true)
  public boolean isShutdown()
  {
    return isSameAs(SHUTDOWN);
  }

  @Contract(pure = true)
  public boolean isNotSleep()
  {
    return !isSleep();
  }

  @Contract(pure = true)
  public boolean isSleep()
  {
    return isSameAs(SLEEP);
  }

  @Contract(pure = true)
  public boolean isNotError()
  {
    return !isError();
  }

  @Contract(pure = true)
  public boolean isError()
  {
    return isSameAs(ERROR);
  }

  public interface Constants
  {
    interface State
    {
      String UNKNOWN_DEFAULT = "UNKNOWN";
      String START_DEFAULT = "START";
      String CREATE_DEFAULT = "CREATE";
      String INIT_DEFAULT = "INIT";
      String RUN_SLAVE_DEFAULT = "RUN_SLAVE";
      String RUN_MASTER_DEFAULT = "RUN_MASTER";
      String SHUTDOWN_DEFAULT = "SHUTDOWN";
      String SLEEP_DEFAULT = "SLEEP";
      String ERROR_DEFAULT = "ERROR";
      String UNIMPLEMENTED_DEFAULT = "UNIMPLEMENTED";
    }
  }
}
