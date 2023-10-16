package de.ochmanski.immutables;

import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.equalable.EqualableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ISet<E>
{

  /**
   * This method is not supported.
   * <p>You must provide a generic type for an empty collection.
   * <p>use method: {@link #ofGenerator(IntFunction)} instead.
   * <p>Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ISet.ofGenerator(Dummy[]::new);
   *   final ISet<String> actual = ISet.ofGenerator(String[]::new);
   *   final ISet<Integer> actual = ISet.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @Contract(value = "-> fail", pure = true)
  private static void of()
  {
    throw new UnsupportedOperationException("Please pass array generator type to the method. "
        + "For example: ISet.ofGenerator(String[]::new)");
  }

  /**
   * Example usage:
   * <pre>
   *   {@code
   *   final ISet<Dummy> actual = ISet.ofGenerator(Dummy[]::new);
   *   final ISet<String> actual = ISet.ofGenerator(String[]::new);
   *   final ISet<Integer> actual = ISet.ofGenerator(Integer[]::new);
   *   }
   * </pre>
   */
  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> ISet<@NotNull S> ofGenerator(
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().constructor(constructor).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> ISet<@NotNull S> of(@NotNull final S s1,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(Set.of(s1)).constructor(constructor).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> ISet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(Set.of(s1, s2)).constructor(constructor).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> ISet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(Set.of(s1, s2, s3)).constructor(constructor).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _, _, _, _ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> ISet<@NotNull S> of(
      @NotNull final S s1,
      @NotNull final S s2,
      @NotNull final S s3,
      @NotNull final S s4,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return EqualableSet.<@NotNull S>builder().set(Set.of(s1, s2, s3, s4)).constructor(constructor).build();
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _ -> new", pure = true)
  static <K extends Equalable<@NotNull K>, V extends Equalable<@NotNull V>>
  ISet<IMap.@NotNull Entry<@NotNull K, @NotNull V>> copyOfEntries(
      @NotNull final Set<Map.@NotNull Entry<@NotNull K, @NotNull V>> entrySet)
  {
    @NotNull
    final Set<IMap.@NotNull Entry<@NotNull K, @NotNull V>> set = entrySet.stream()
        .map(ISet::toEqualableEntry)
        .collect(Collectors.toUnmodifiableSet());
    final IntFunction<IMap.Entry<@NotNull K, @NotNull V> @NotNull []> constructor = IMap.Entry[]::new;
    return copyOf(set, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> EqualableSet<@NotNull S> empty(
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Set<@NotNull S> of = Set.of();
    return copyOf(of, constructor);
  }

  @NotNull
  @UnmodifiableView
  @Contract(value = " _, _ -> new", pure = true)
  static <S extends Equalable<@NotNull S>> EqualableSet<@NotNull S> copyOf(
      @NotNull final Set<@NotNull S> keySet,
      @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Set<@NotNull S> set = Set.copyOf(keySet);
    return EqualableSet.<@NotNull S>builder().set(set).constructor(constructor).build();
  }

  @NotNull
  @Contract(value = " _ -> new", pure = true)
  static <V extends Equalable<@NotNull V>, K extends Equalable<@NotNull K>>
  IMap.@NotNull Entry<@NotNull K, @NotNull V> toEqualableEntry(
      @NotNull final Map.@NotNull Entry<@NotNull K, @NotNull V> p)
  {
    return IMap.Entry.<@NotNull K, @NotNull V>builder().key(p.getKey()).value(p.getValue()).build();
  }

  int size();

  /**
   * Returns {@code true} if this set contains no elements.
   *
   * @return {@code true} if this set contains no elements
   */
  boolean isEmpty();

  /**
   * Returns {@code true} if this set contains the specified element. More formally, returns {@code true} if and only if
   * this set contains at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this set is to be tested
   * @return {@code true} if this set contains the specified element
   */
  boolean contains(@NotNull final E o);

  /**
   * Returns a deep copy of this {@code ArraySet} instance.  (The elements themselves are also copied.)
   *
   * @return a clone of this {@code ArraySet} instance
   */
  @NotNull
  @Contract(value = " -> new", pure = true)
  ISet<@NotNull E> deepClone();

  /**
   * Returns an array containing all the elements in this set in proper sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this set.  (In other words, this method must allocate a new array).  The caller is thus free to
   * modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all the elements in this set in proper sequence
   */
  @NotNull
  @Contract(value = " -> new", pure = true)
  E @NotNull [] toArray();

  /**
   * Returns an iterator over the elements in this set.  The elements are returned in no particular order (unless this
   * set is an instance of some class that provides a guarantee).
   *
   * @return an iterator over the elements in this set
   */
  @NotNull
  @Contract(pure = true)
  Iterator<@NotNull E> iterator();

  @NotNull
  @Contract(value = " -> new", pure = true)
  Stream<@NotNull E> stream();

  @NotNull
  @Contract(value = " -> new", pure = true)
  Set<@NotNull E> unwrap();

  @NotNull
  @Contract(pure = true)
  Optional<@Nullable E> findFirst();

}
