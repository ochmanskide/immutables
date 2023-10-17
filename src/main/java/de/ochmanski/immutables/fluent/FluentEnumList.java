package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.IList;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Immutable wrapper of <pre>{@code java.util.EnumList<K,V>}</pre>
 * <p>This Read-Only implementation of <pre>{@code List<>}</pre> interface
 * doesn't accept Null elements.
 * <p>This List doesn't allow modifications. All mutators/listters were disabled.
 *
 * @param <E> {@code @NotNull E } key
 */
@Value
@UnmodifiableView
@ParametersAreNonnullByDefault
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class FluentEnumList<E extends @NotNull Enum<@NotNull E> & Fluent<@NotNull E>> implements IList<@NotNull E>
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

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = ImmutableEnumList.ofGenerator(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.ofGenerator(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: ImmutableEnumList.ofGenerator(Day[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final IList<Dummy> actual = ImmutableEnumList.ofGenerator(Dummy[]::new);
   *   final IList<DayOfWeek> actual = ImmutableEnumList.ofGenerator(DayOfWeek[]::new);
   *   final IList<Month> actual = ImmutableEnumList.ofGenerator(Month[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> FluentEnumList<@NotNull S> ofGenerator(
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Class<@NotNull S> componentType = getComponentTypeFromConstructor(constructor);
    return FluentEnumList.<@NotNull S>builder().constructor(constructor).list(List.of()).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  @SuppressWarnings("unchecked")
  private static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> Class<@NotNull S> getComponentTypeFromConstructor(
      final @NotNull IntFunction<@NotNull S @NotNull []> constructor)
  {
    return (Class<@NotNull S>)constructor.apply(0).getClass().getComponentType();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(@NotNull final S s1)
  {
    return FluentEnumList.<@NotNull S>builder().list(List.of(s1)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2)
  {
    return FluentEnumList.<@NotNull S>builder().list(List.of(s1, s2)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3)
  {
    return FluentEnumList.<@NotNull S>builder().list(List.of(s1, s2, s3)).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends Enum<@NotNull S> & Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3,
      @NotNull final S s4)
  {
    return FluentEnumList.<@NotNull S>builder().list(List.of(s1, s2, s3, s4)).build();
  }

  @NotNull
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<S> of(
      @NotNull final S @NotNull [] array)
  {
    final List<@NotNull S> list = Arrays.asList(array);
    return FluentEnumList.<@NotNull S>of(list);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<S> of(
      @NotNull final Collection<@NotNull S> collection)
  {
    return FluentEnumList.<@NotNull S>builder().list(List.copyOf(collection)).build();
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in this list
   */
  @Override
  public int size()
  {
    return list.size();
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
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
   *
   * @param o
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
   *
   * @param o
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
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public FluentEnumList<@NotNull E> deepClone()
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
  @Override
  @NotNull
  @Contract(value = "-> new", pure = true)
  public E @NotNull [] toArray()
  {
    return isEmpty()
        ? list.toArray(getConstructor())
        : list.toArray(newArrayNative());
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Override
  public E get(final int index)
  {
    return list.get(index);
  }

  @NotNull
  @SuppressWarnings("unchecked")
  @Contract(value = "-> new", pure = true)
  private E @NotNull [] newArrayNative()
  {
    final Class<@NotNull E> componentType = getComponentType();
    final int size = size();
    final Object a = Array.newInstance(componentType, size);
    return (E[])a;
  }

  @NotNull
  @SuppressWarnings("unchecked")
  private Class<@NotNull E> getComponentType()
  {
    return isEmpty() ? getComponentTypeFromConstructor(getConstructor()) : (Class<E>)iterator().next().getClass();
  }

  /**
   * Returns an iterator over the elements in this list.  The elements are returned in no particular order (unless this
   * list is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this list
   */
  @NotNull
  @Contract(pure = true)
  public Iterator<@NotNull E> iterator()
  {
    return unwrap().iterator();
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   *     {@code Spliterator}.
   * @since 1.8
   */
  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream()
  {
    return unwrap().stream();
  }

  @Override
  @NotNull
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> unwrap()
  {
    return List.copyOf(list);
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst()
  {
    return stream().findFirst();
  }

}