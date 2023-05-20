package de.ochmanski.immutables;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.PositiveOrZero;

@Value
@Unmodifiable
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class IList
{

  @NonNull
  @NotNull
  @javax.validation.constraints.NotNull
  @PositiveOrZero
  @Builder.Default
  BigInteger value = BigInteger.ZERO;

}
