package de.ochmanski.immutables.immutable;

import de.ochmanski.immutables.Checked;
import de.ochmanski.immutables.IList;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableList<E> implements IList<@NotNull E>
{

  @NonNull
  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  List<@NonNull @NotNull E> list = List.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">
  @NotNull
  @SuppressWarnings({UNCHECKED, RAWTYPES})
  @Contract(value = "-> new", pure = true)
  private static <S> IntFunction<@NotNull S @NotNull []> defaultKey() {
    return (IntFunction) DEFAULT_KEY;
  }

  @NotNull
  private static final IntFunction<@NotNull Object @NotNull []> DEFAULT_KEY = Object @NotNull []::new;

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({UNCHECKED})
  public static <E> ImmutableList<E> empty() {
    return EMPTY;
  }

  @NotNull
  @SuppressWarnings(RAWTYPES)
  private static final ImmutableList EMPTY = ImmutableList.builder().build();
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(), constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1), constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _, _-> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2), constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3), constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return ImmutableList.<@NotNull S>of(List.of(array), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final Collection<? extends @NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final List<@NotNull S> checkedList = Collections.checkedList(List.copyOf(collection), getComponentTypeFromConstructor(constructor));
    return ImmutableList.<@NotNull S>builder().list(checkedList).key(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return Checked.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">

  /**
   * @return the number of elements in this list
   */
  @Override
  public int size()
  {
    return list.size();
  }

  /**
   * @return {@code true} if this list contains no elements
   */
  @Override
  public boolean isEmpty()
  {
    return list.isEmpty();
  }

  /**
   * Returns {@code true} if this list contains the specified element. More formally, returns {@code true} if and only
   * if this list contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this list is to be tested
   * @return {@code true} if this list contains the specified element
   */
  @Override
  public boolean contains(@NotNull final E o)
  {
    return list.contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  @Override
  public int indexOf(@NotNull final E o)
  {
    return list.indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
  @Override
  public int lastIndexOf(@NotNull final E o)
  {
    return list.lastIndexOf(o);
  }

  /**
   * Returns a deep copy of this {@code ArrayList} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArrayList} instance
   */
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableList<@NotNull E> deepClone()
  {
    return toBuilder().build();
  }

  /**
   * Returns an array containing all the elements in this list in proper sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this list.  (In other words, this method must allocate a new array).  The caller is thus free to
   * modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all the elements in this list in proper sequence
   */
  @NotNull
  @Override
  @Contract(value = " -> new", pure = true)
  public E @NotNull [] toArray()
  {
    return list.toArray(getKey().apply(size()));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Override
  @Contract(pure = true)
  public E get(final int index)
  {
    return list.get(index);
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst() {
    return isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public ImmutableSet<@NotNull E> toSet() {
    return ImmutableSet.of(this);
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> unwrap()
  {
    return list.isEmpty()
      ? Collections.checkedList(List.of(), getComponentType())
      : Collections.checkedList(List.copyOf(list), getComponentType());
  }
  //</editor-fold>
}
