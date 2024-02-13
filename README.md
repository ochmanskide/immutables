# Immutables - strongly typed immutable Java Collections

## 1. Highlights

### 1.1. No more `UnsupportedOperationException` at runtime.

Authors of `java.util.ImmutableCollections.java` class attempted to make this collection immutable,
but they didn't do it because they wanted to maintain compatibility with the old Java `Collection` interface.
I Improved their attempt to make `java.util.ImmutableCollections.java`, and I made the library truly immutable.
Instead of throwing an exception to the caller, I removed the following mutators from the library:

```java
// all mutating methods throw UnsupportedOperationException
@Override public boolean add(E e){throw uoe();}
@Override public boolean addAll(Collection<?extends E> c){throw uoe();}
@Override public void clear(){throw uoe();}
@Override public boolean remove(Object o){throw uoe();}
@Override public boolean removeAll(Collection<?> c){throw uoe();}
@Override public boolean removeIf(Predicate<? super E>filter){throw uoe();}
@Override public boolean retainAll(Collection<?> c){throw uoe();}
```

This way, mutable code cannot be invoked at all.  
Therefore, you cannot get an `UnsupportedOperationException` anymore.  
The project will not compile if you try to perform an illegal operation.

### 1.2. All collections are checked at construction time.

In addition, in order to remove a known `Type Erasure` limitation of the Java programming language,
all collections have been enriched with the type object. The generic type is no longer erased during compilation.
The class type object is available at all times, during the program execution.
The type argument must be passed to the factory method, and it cannot be omitted during construction.  
This will ensure that the object is present during runtime.  
The code will not compile without specifying the generic type of the collection.

Example:

```java
IList<DayOfWeek> list = ImmutableList.ofGenerator(DayOfWeek[]::new);
```

It means that it is now possible to call `.toArray()` method without parameters,
and it will return the true generic type, instead of a simple `Object[]` array.

Example:

```java
DayOfWeek[] array = list.toArray();
```

as you see, the `.toArray()`, method no longer accepts `IntFunction<T>` as an argument.  
The method `public T[] toArray(IntFunction<T> generator)` has been removed from this API permanently.  
The type is checked at construction time, and it is mandatory.  
You will not be able to create a collection without specifying the type, even if the collection,
you are trying to create, is empty.


Obviously, this will not solve the problem completely, for example, for nested types:
```java
IList immutableList = ImmutableList.ofGenerator(IList<IList<IList<IList>>>[]::new);
```
I am aware that you can pass the `?` type or cast it, which will later throw `ClassCastException` at runtime.  
This is why it is important to always declare full types,
and let the compiler handle the generic types.  
If you skip type declaration, there is nothing I can do to stop you.  
I can only check the first generic type in the chain. The rest is on you, and on the java compiler.

### 1.3. Bridge methods

In order to provide better portability, the collections contain the bridge methods, which return a
`@UnmodifiableView` of the backed collection.  
The collection stays immutable, and the contract is not violated.  
Be cautious when using this old API because it may throw, known `UnsupportedOperationException` at runtime.  
This defeats the purpose of using the library. The point of
creating [Immutables](https://gitlab.com/stadlerrail/sw/dias/libs/immutables)
was not to get rid of this exception, and if you use the bridge methods, the benefit is partially lost.  
Which is still better than it was up to this point because you always got the exception. Now you get it only sometimes.  
Use it at your discretion.

Example:

```java
IList<DayOfWeek> immutableList = ImmutableList.ofGenerator(DayOfWeek[]::new);
List<DayOfWeek> normalList = immutableList.unwrap();
```

## 2. Features

- [ ] no `NULL` values allowed,
- [ ] compile time check for `NULL`s,
- [ ] improves IDE assistance, by adding Java annotations, such as `@Contract`, `@NotNull`, `@Nullable`, `@UmodifiableView`
- [ ] lombok support,
- [ ] not more null objects, instead you get `Optional<@Nullable T>`
- [ ] compile-time immutability with new `IList<T>`, `ISet<T>`, `IMap<K,V>` interfaces
- [ ] not possible to modify a collection during run-time or even at compile-time
- [ ] wraps third party code into your own, which gives you flexibility, and frees you from adhering to old Java
  standards
  for
  legacy reasons,
- [ ] no need to maintain annoying and limiting, backward-compatibility with old API,
- [ ] no more casts in code,
- [ ] removes `@SuppressWarning`
- [ ] returns `V` instead of `Object`
- [ ] accepts `K` for `map.get()` instead of `Object`
- [ ] resolve known problems with Reflection type erasure, by forcing the programmer to provide `K`, `V` instances at
  compile/build
  time, which makes it easier to work later (they are now available at run-time),
- [ ] `.toArray()` returns an array of `K[]` instead of an array of `Object[]`
- [ ] no need to provide the generator for `.toArray()` function. Now it knows the return type.
  so instead of:  
  `String[] a = stream.toArray(String[]::new)`  
  you can now shorten it:  
  `String[] a = stream.toArray();`
- [ ] no more `equals(Object)` comparison, now you can compare with `.isEqualTo()`
- [ ] more fluent API such
  as `.isEqualTo()`, `.isNotEqualTo()`, `.areEqualTo()`, `.areNotEqualTo()`, `.isIn()`, `.isNotIn()`, etc.
- [ ] fully implemented method `toString()`, which calls `p -> p.toString()`
- [ ] `prettyPrint()` method which prints `JSON`
- [ ] `prettyPrint(Function f)` method which overwrites default `p.toString()` method
- [ ] adds additional `Stream Collectors`
- [ ] new types for storing `Enums` in `Maps`, `Sets`

new immutable interface definitions of Java `Collections`, such as `List<T>`, `Set<T>`, `Map<T>`

## Prerequisites

### SDKMAN

`SDKMAN` is a software development kit manager, to download and manage different versions of Java.
`SDKMan` can be used via the command line, which can make the process easier for developers who prefer this method.

### TODO:

- [ ] fully implemented method `toString()`, which `calls p -> p.toString()`
- [ ] `prettyPrint()` method which prints JSON
- [ ] `prettyPrint(Function f)` method which overwrites default `p.toString()` method
- [ ] rename `Fluent` into `FluentEnum`
- [ ] create a new interface `Fluent`
- [ ] create a new package for `.fluent`. interfaces/classes
- [ ] make `FluentEnum<E> interface extends Fluent<FluentEnum<E>>`
- [ ] uncomment all code that was not implemented yet,
- [ ] add special collector for `Collectors.toUnmodifiableMap()`
- [ ] allow passing a constructor to the `.toArray(constructor)` and mark as unsafe
- [ ] create overload variants for all `.of()` methods without the `.of(construct)`
- [ ] mark methods as `@UmodifiableView`
- [ ] each method in interface should not have `public` modifier
- [ ] all other classes should have public static methods (I forgot to change it, after copying from interface)
- [ ] add `ImmutableSortedSet` class
- [ ] add methods for elements up to 10,
- [ ] create override methods for Strings for each above, where `StringWrapper[]::new` is passed,
- [ ] split project into `Fluent` and `not Fluent`,
- [ ] provide some Utility/Factory classes for EqualableWrappers
- [ ] create a new variant of `ImmutableStream<> stream()` instead of `Stream<> stream()`
- [ ] add `Optional.empty()` "-like" API, and delegate calls to `.of()` methods, so that both are available.
- [ ] add `.map()` method to shorten the `.stream().map()` syntax.
- [ ] create a Collector for advanced `Set`/`Map`/`List` for example:

```java
@NotNull
private SortedSet<@NotNull OidDto> oids(@NotNull final Device entity) {
    return entity.getOids().stream()
      .map(p->OidDto.builder()
      .sourceOid(p.getSourceOid())
      .destSignalName(p.getDestSignalName())
      .destSignalDescription(p.getDestSignalDescription())
      .build()
    )
    .collect(collectingAndThen(toSet(),this::toUnmodifiableSortedSet));
}

@NotNull
@UnmodifiableView
@Contract(value = "_ -> new", pure = true)
private SortedSet<@NotNull OidDto> toUnmodifiableSortedSet(@NotNull final Set<@NotNull OidDto> collection) {
  final SortedSet<OidDto> sorted=new TreeSet<>(collection);
  return Collections.unmodifiableSortedSet(sorted);
}
```

```java
.collect(Collectors.toMap(this::jobSettingPropertyKey,this::getValueWrapper));
```

should be:

```java
.collect(FluentCollectors.toMap(this::jobSettingPropertyKey,this::getValueWrapper));
```

```java
return ImmutableEnumMap.of(toMap(properties),JobSettingPropertyKey[]::new,JobSettingPropertyKey.class);
```

should be:

```java
return ImmutableEnumMap.of(properties,JobSettingPropertyKey[]::new);
```

```java
  @NotNull
public ImmutableEnumMap<@NotNull JobSettingPropertyKey, StringWrapper> getPropertiesIntern(
final List<@NotNull JobProperty> properties) {
  return ImmutableEnumMap.of(toMap(properties),JobSettingPropertyKey[]::new,JobSettingPropertyKey.class);
}

@NotNull
private Map<@NotNull JobSettingPropertyKey, @NotNull StringWrapper> toMap(
@NotNull final List<@NotNull JobProperty> properties) {
  return properties.stream()
  .filter(this::matchingKeyPropertyExists)
  .collect(FluentCollectors.toMap(this::jobSettingPropertyKey,this::getValueWrapper));
}
```


## Template

```java
//<editor-fold defaultstate="collapsed" desc="1. eager static initializers">

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="2. static factory methods">

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="3. implementation of IList interface">

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="4. Positional Access Operations">

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="5. converters">

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="6. bridge for Java Collection API">

//</editor-fold>
```
