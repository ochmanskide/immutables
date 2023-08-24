# immutables - Strongly typed immutable Java Collections

## Features

* not NULL values allowed,
* compile time check for NULLs,
* improved IDE assistance, by adding Java annotations, such as @Contract, @NotNull, @Nullable
* lombok support,
* not more null objects, instead you get Optional<T>
* compile-time immutability with new IList<T>, ISet<T>, IMap<K,V> interfaces
* not possible to modify a collection during run-time or even at compile-time
* wraps third party code into your own, which gives you flexibility, and frees you from sticking to Java standards for
  legacy reasons,
* no need to maintain annoying and limiting, backward-compatibility with old API,
* no more casts in code,
* remove @SuppressWarning
* return V instead of Object
* accept K for map.get() instead of object
* resolve known problems with Reflection type erasure, by forcing the programmer to provde K, V instances at
  compile/build
  time, which makes it easier to work later (they are now available at run-time),
* toArray() returns an array of K[] instead of an array of Object[]
* no need to provide the generator for toArray() function. Now it knows the return type.
  so instead of:
  String[] a = stream.toArray(String[]::new)
  you can now shorten it:
  String[] a = stream.toArray();
* no more equals(Object) comparison, now you can compare with .isEqualTo()
* more fluent API such as .isEqualTo(), .isNotEqualTo(), .areEqualTo(), .areNotEqualTo(), .isIn(), .isNotIn(), etc.

* improved Stream Collectors
* new types for storing Enums in Maps, Sets

new immutable interface definitions of Java Collections, such as List&lt;T>, Set&lt;T>, Map&lt;T>

## Prerequisites

### SDKMAN

SDKMAN is a software development kit manager, to download and manage different versions of Java.
SDKMan can be used via the command line, which can make the process easier for developers who prefer this method.

### TODO:

* mark methods as @UmodifiableView
* each method in interface should not have public modifier
* all other classes should have public static methods (I forgot to change it, after copying from interface)
* create a Collector for advanced map for example:

```java
  @NotNull
private SortedSet<@NotNull OidDto> oids(@NotNull final Device entity){
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
private SortedSet<@NotNull OidDto> toUnmodifiableSortedSet(@NotNull final Set<@NotNull OidDto> collection)
    {
final SortedSet<OidDto> sorted=new TreeSet<>(collection);
    return Collections.unmodifiableSortedSet(sorted);
    }
```

```java
.collect(Collectors.toMap(this::jobSettingPropertyKey,this::getValueWrapper));
```

should be:

```java
.collect(Collectors.toEnumMap(this::jobSettingPropertyKey,this::getValueWrapper));
```

```java
return ImmutableEnumMap.of(toMap(properties),JobSettingPropertyKey[]::new,JobSettingPropertyKey.class);
```

should be:

```java
return ImmutableEnumMap.of(properties,JobSettingPropertyKey[]::new,JobSettingPropertyKey.class);
```

```java
  @NotNull
public ImmutableEnumMap<@NotNull JobSettingPropertyKey, StringWrapper> getPropertiesIntern(
final List<@NotNull JobProperty> properties)
    {
    return ImmutableEnumMap.of(toMap(properties),JobSettingPropertyKey[]::new,JobSettingPropertyKey.class);
    }

@NotNull
private Map<@NotNull JobSettingPropertyKey, @NotNull StringWrapper> toMap(
@NotNull final List<@NotNull JobProperty> properties)
    {
    return properties.stream()
    .filter(this::matchingKeyPropertyExists)
    .collect(Collectors.toMap(this::jobSettingPropertyKey,this::getValueWrapper));
    }
```

* split project into Fluent and not Fluent,
* provide some Utility/Factory classes for EqualableWrappers
* 
