package de.ochmanski.immutables.immutable;

import com.stadlerrail.diag.dias.diasexport.main.collection.IList;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.RAWTYPES;
import static com.stadlerrail.diag.dias.servicestate.property.Constants.Warning.UNCHECKED;

@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ImmutableList<E> implements IList<@NotNull E>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given list cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  List<@NonNull @NotNull E> list = List.of();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> constructor = defaultConstructor();

  @NotNull
  @Contract(value = "-> new", pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  private static <S> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Object @NotNull []::new;
  }

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
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings(UNCHECKED)
  public static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> copyOf(@NotNull final Collection<? extends @NotNull S> values,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(values, constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  public static <E> ImmutableList<E> empty()
  {
    return EMPTY;
  }

  private static final ImmutableList EMPTY = ImmutableList.builder().build();

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in this list
   */
  public int size()
  {
    return list.size();
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */
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
  public boolean contains(@NotNull final E o)
  {
    return list.contains(o);
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not
   * contain the element. More formally, returns the lowest index {@code i} such that {@code Objects.equals(o, get(i))},
   * or -1 if there is no such index.
   */
  public int indexOf(@NotNull final E o)
  {
    return list.indexOf(o);
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain
   * the element. More formally, returns the highest index {@code i} such that {@code Objects.equals(o, get(i))}, or -1
   * if there is no such index.
   */
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
  @Contract(value = " -> new", pure = true)
  public E @NotNull [] toArray()
  {
    return isEmpty()
        ? list.toArray(getConstructor())
        : list.toArray(newArrayNative());
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  @Contract(value = "-> new", pure = true)
  private E @NotNull [] newArrayNative()
  {
    final Class<E> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @SuppressWarnings(UNCHECKED)
  private Class<@NotNull E> getComponentType()
  {
    return (Class<E>)get(0).getClass();
  }

  // Positional Access Operations

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Contract(pure = true)
  public E get(final int index)
  {
    return list.get(index);
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   *   {@code Spliterator}.
   * @since 1.8
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream()
  {
    return unwrap().stream();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> unwrap()
  {
    return List.copyOf(list);
  }

  @NotNull
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst()
  {
    return stream().findFirst();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final Collection<? extends @NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>builder().list(List.copyOf(collection)).constructor(constructor).build();
  }

}
