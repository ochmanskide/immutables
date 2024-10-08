package de.ochmanski.immutables.immutable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ochmanski.immutables.collection.Checked;
import de.ochmanski.immutables.collection.ICollection;
import de.ochmanski.immutables.equalable.Equalable;
import de.ochmanski.immutables.equalable.Equalable.Dummy;
import lombok.*;
import org.jetbrains.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static de.ochmanski.immutables.constants.Constants.Warning.*;
import static java.util.function.Predicate.not;

@Value
@Unmodifiable
@UnmodifiableView
@ParametersAreNonnullByDefault
@EqualsAndHashCode(doNotUseGetters = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public class ImmutableList<E> implements IList<@NotNull E>
{

  @NotNull("Given list cannot be null.")
  @Unmodifiable
  @UnmodifiableView
  @javax.validation.constraints.NotNull(message = "Given list cannot be null.")
  @Builder.Default
  List<@NotNull E> list = (List)Collections.checkedList(List.of(), Equalable.Dummy.class);

  @NotNull("Given keyType cannot be null.")
  @javax.validation.constraints.NotNull(message = "Given keyType cannot be null.")
  @Builder.Default
  IntFunction<@NotNull E @NotNull []> key = Equalable.defaultKey();

  //<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(pure = true)
  @SuppressWarnings({ UNCHECKED, RAWTYPES })
  public static <S> ImmutableList<@NotNull S> empty()
  {
    return (ImmutableList) EMPTY;
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  private static final ImmutableList<@NotNull Dummy> EMPTY = createConstant();

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "-> new", pure = true)
  private static ImmutableList<@NotNull Dummy> createConstant()
  {
    return ImmutableList.<@NotNull Dummy>builder().build();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="2. static factory methods">

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1) {
    return ImmutableList.<@NotNull String>of(e1, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2) {
    return ImmutableList.<@NotNull String>of(e1, e2, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(@NotNull final ICollection<@NotNull S> immutableCollection) {
    return ImmutableList.<@NotNull S>of(immutableCollection.unwrap(), immutableCollection.getKey());
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5,
    @NotNull final String e6) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, e6, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5,
    @NotNull final String e6,
    @NotNull final String e7) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, e6, e7, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5,
    @NotNull final String e6,
    @NotNull final String e7,
    @NotNull final String e8) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, e6, e7, e8, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5,
    @NotNull final String e6,
    @NotNull final String e7,
    @NotNull final String e8,
    @NotNull final String e9) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, e6, e7, e8, e9, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String e1,
    @NotNull final String e2,
    @NotNull final String e3,
    @NotNull final String e4,
    @NotNull final String e5,
    @NotNull final String e6,
    @NotNull final String e7,
    @NotNull final String e8,
    @NotNull final String e9,
    @NotNull final String e10) {
    return ImmutableList.<@NotNull String>of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> noneOf(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2), constructor);
  }

  @NotNull
  @Unmodifiable
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
  @Unmodifiable
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
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final S e6,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5, e6), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final S e6,
    @NotNull final S e7,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5, e6, e7), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final S e6,
    @NotNull final S e7,
    @NotNull final S e8,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5, e6, e7, e8), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final S e6,
    @NotNull final S e7,
    @NotNull final S e8,
    @NotNull final S e9,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _, _, _, _, _, _, _, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> of(
    @NotNull final S e1,
    @NotNull final S e2,
    @NotNull final S e3,
    @NotNull final S e4,
    @NotNull final S e5,
    @NotNull final S e6,
    @NotNull final S e7,
    @NotNull final S e8,
    @NotNull final S e9,
    @NotNull final S e10,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    return ImmutableList.<@NotNull S>of(List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10), constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(
    @NotNull final String @NotNull [] array) {
    return ImmutableList.<@NotNull String>of(List.of(array));
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
  @Contract(value = "_ -> new", pure = true)
  public static ImmutableList<@NotNull String> of(@NotNull final Collection<@NotNull String> collection) {
    return ImmutableList.<@NotNull String>of(collection, String @NotNull []::new);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> merge(@NotNull final ImmutableList<@NotNull S> a,
                                                    @NotNull final ImmutableList<@NotNull S> b,
                                                    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final Collection<@NotNull S> collection = new ArrayList<>(a.unwrap());
    collection.addAll(b.unwrap());
    final List<@NotNull S> checkedList = Collections.checkedList(List.copyOf(collection), getComponentTypeFromConstructor(constructor));
    return ImmutableList.<@NotNull S>builder().list(checkedList).key(constructor).build();
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_, _ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> ofCollection(
    @NotNull final Collection<? extends @NotNull S> collection,
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor)
  {
    final List<@NotNull S> checkedList = Collections.checkedList(List.copyOf(collection), getComponentTypeFromConstructor(constructor));
    return ImmutableList.<@NotNull S>builder().list(checkedList).key(constructor).build();
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
  @Contract(value = "_ -> new", pure = true)
  static <S> Class<@NotNull S> getComponentTypeFromConstructor(
    @NotNull final IntFunction<@NotNull S @NotNull []> constructor) {
    return Checked.<@NotNull S>getComponentTypeFromConstructor(constructor);
  }

  @NotNull
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public static <S> ImmutableList<@NotNull S> copyOf(@NotNull final ICollection<@NotNull S> immutableCollection)
  {
    return ImmutableList.<@NotNull S>of(immutableCollection.unwrap(), immutableCollection.getKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">
  @Override
  @Contract(pure = true)
  public void forEach(@NotNull final Consumer<? super @NotNull E> consumer)
  {
    list.forEach(consumer);
  }

  @Override
  @Contract(pure = true)
  public void forEachOrdered(@NotNull final Consumer<? super @NotNull E> consumer)
  {
    list.stream().sorted().forEachOrdered(consumer);
  }

  @Override
  @Contract(pure = true)
  public @NotNull Iterator<@NotNull E> iterator()
  {
    return list.iterator();
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public <R> Stream<@NotNull R> map(@NotNull final Function<? super @NotNull E, ? extends @NotNull R> mapper)
  {
    return list.stream().map(mapper);
  }

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
  @Unmodifiable
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

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public ImmutableList<@NotNull E> getList() {
    return this;
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableList<@NotNull E> add(@NotNull final E c)
  {
    return addAll(c);
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableList<@NotNull E> addAll(@NotNull final E c)
  {
    return addAll(Stream.of(c));
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableList<@NotNull E> addAll(@NotNull final ImmutableCollection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableList<@NotNull E> addAll(@NotNull final Collection<? extends @NotNull E> c)
  {
    return addAll(c.stream());
  }

  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = "_ -> new", pure = true)
  public ImmutableList<@NotNull E> addAll(@NotNull final Stream<? extends @NotNull E> c)
  {
    return Stream.concat(stream(), c).collect(ImmutableCollectors.toList(getKey()));
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

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findLast() {
    return isEmpty() ? Optional.empty() : Optional.of(list.get(size() - 1));
  }

  @NotNull
  @Override
  @Contract(pure = true)
  public Optional<@Nullable E> findAny() {
    return findFirst();
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="5. converters to family classes">
  @NotNull
  @Override
  @Unmodifiable
  @UnmodifiableView
  @Contract(value = " -> this", pure = true)
  public ImmutableList<@NotNull E> toList() {
    return this;
  }

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

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
   * {@code Spliterator}.
   * @since 1.8
   */
  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public Stream<@NotNull E> stream() {
    return list.stream();
  }

  @NotNull
  @Override
  @UnmodifiableView
  @Contract(value = " -> new", pure = true)
  public List<@NotNull E> unwrap()
  {
    return list.isEmpty()
      ? Collections.checkedList(List.of(), getComponentTypeFromKey())
      : Collections.checkedList(List.copyOf(list), getComponentTypeFromKey());
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="8. custom methods">

  @NotNull
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public ImmutableSet<@NotNull E> findDuplicates()
  {
    Set<E> elements = new HashSet<>();
    return stream()
      .filter(not(elements::add))
      .collect(ImmutableCollectors.toSet(getKey()));
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   *
   * @return the element at the specified position in this list or {@code Optional.empty()} if the index is out of range ({@code index < 0 || index >= size()})
   */
  @NotNull
  @Unmodifiable
  @Contract(pure = true)
  public Optional<@Nullable E> findByIndex(final int index)
  {
    return isWithinBounds(index) ? Optional.of(get(index)) : Optional.empty();
  }

  @Contract(pure = true)
  private boolean isNotWithinBounds(final int index)
  {
    return !isWithinBounds(index);
  }

  @Contract(pure = true)
  private boolean isWithinBounds(final int index)
  {
    return !isIndexLessThanZero(index) && !isIndexGreaterOrEqualToSize(index);
  }

  @Contract(pure = true)
  public boolean isIndexLessThanZero(final int index)
  {
    return index < 0;
  }

  @Contract(pure = true)
  public boolean isIndexGreaterOrEqualToSize(final int index)
  {
    return index >= size();
  }

  @Contract(pure = true)
  public boolean isSizeGreaterThanZero()
  {
    return size() > 0;
  }

  @Contract(pure = true)
  public boolean hasExactlyOneElement()
  {
    return size() == 1;
  }

  @Contract(pure = true)
  public boolean hasMoreThanOneElement()
  {
    return hasMultipleElements();
  }

  @Contract(pure = true)
  public boolean hasMultipleElements()
  {
    return isSizeGreaterThanOne();
  }

  @Contract(pure = true)
  public boolean isSizeGreaterThanOne()
  {
    return size() > 1;
  }
  //</editor-fold>

  @NotNull
  @Override
  @Unmodifiable
  @Contract(value = "-> new", pure = true)
  public String toString() {
    try {
      final String s = new ObjectMapper().writeValueAsString(list);
      return limit(s, 1000);
    } catch (JsonProcessingException e) {
      return Arrays.toString(toArray());
    }
  }

  @NotNull
  @Unmodifiable
  @SuppressWarnings(SAME_PARAMETER_VALUE)
  @Contract(value = "_, _ -> new", pure = true)
  private String limit(@NotNull final String s, final int limit)
  {
    final int end = Math.min(s.length(), Math.abs(limit));
    return s.substring(0, end);
  }
}
