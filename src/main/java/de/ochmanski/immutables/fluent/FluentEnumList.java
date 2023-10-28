package de.ochmanski.immutables.fluent;

import de.ochmanski.immutables.IList;
import de.ochmanski.immutables.immutable.enums.ImmutableEnumList;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.RAWTYPES;
import static de.ochmanski.immutables.constants.Constants.Warning.UNCHECKED;
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
public class FluentEnumList<E extends @NotNull Enum<@NotNull E> & @NotNull Fluent<? extends @NotNull E>>
  implements IList<@NotNull E>
{

  @UnmodifiableView
  @NonNull
  @NotNull("Given list cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  ImmutableEnumList<@NonNull @NotNull E> list = ImmutableEnumList.empty();

  @NonNull
  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NonNull @NotNull E @NonNull @NotNull []> key = defaultConstructor();

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
  public static <S extends Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> ofGenerator(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.ofGenerator(constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  public static <S extends Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  public static <S extends Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  public static <S extends Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, constructor));
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  public static <S extends Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S s1,
    @NotNull final S s2,
    @NotNull final S s3,
    @NotNull final S s4,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>of(ImmutableEnumList.of(s1, s2, s3, s4, constructor));
  }

  @NotNull
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  private static <S extends Enum<@NotNull S>> IntFunction<@NotNull S @NotNull []> defaultConstructor()
  {
    return (IntFunction)Enum @NotNull []::new;
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final S @NotNull [] array,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final List<@NotNull S> list = List.of(array);
    return FluentEnumList.<@NotNull S>of(list, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final Collection<@NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return FluentEnumList.<@NotNull S>builder()
      .list(ImmutableEnumList.<@NotNull S>copyOf(collection, constructor))
      .key(constructor)
      .build();
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
    return list.toArray();
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

  @Override
  @Contract(pure = true)
  public void forEach(@NotNull final Consumer<? super @NotNull E> consumer) {
    list.forEach(consumer);
  }

  @Override
  @Contract(pure = true)
  public void forEachRemaining(@NotNull final Consumer<? super @NotNull E> consumer) {
    list.forEachRemaining(consumer);
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   *   {@code Spliterator}.
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
    return list.unwrap();
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findFirst()
  {
    return stream().findFirst();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  private static <S extends @NotNull Enum<@NotNull S> & @NotNull Fluent<@NotNull S>> FluentEnumList<@NotNull S> of(
    @NotNull final ImmutableEnumList<@NotNull S> immutableList) {
    return FluentEnumList.builder().list(immutableList).key(immutableList.getKey()).build();
  }
}
