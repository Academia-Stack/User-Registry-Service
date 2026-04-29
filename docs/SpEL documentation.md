# Spring Expression Language (SpEL)

**Spring Expression Language (SpEL)** is a powerful expression language used in the Spring Framework to dynamically evaluate expressions at runtime. It is commonly used in annotations for caching, security, configuration, and conditional logic.

---

# SpEL in Spring Caching

Spring Cache supports SpEL expressions in three main attributes inside caching annotations.

## 1. `key`

Used to compute the **cache key dynamically** based on method parameters.

### Example

```java
@Cacheable(value = "subject", key = "#subjectId")
public Subject getSubject(int subjectId)
```

Cache entry created:

```
subject:10
```

### Using object properties

```java
@CachePut(value = "subject", key = "#subject.subjectId")
public Subject updateSubject(Subject subject)
```

### Using multiple parameters

```java
@Cacheable(value = "subject", key = "#courseId + ':' + #subjectId")
public Subject getSubject(int courseId, int subjectId)
```

---

## 2. `condition`

Controls **whether caching should happen before the method executes**.

If the condition evaluates to `false`, the cache is bypassed.

### Example

```java
@Cacheable(value = "subject", key = "#id", condition = "#id > 0")
public Subject getSubject(int id)
```

Meaning:

```
Cache only if id > 0
```

### Example using object properties

```java
@CachePut(value = "subject", key = "#subject.subjectId",
          condition = "#subject.subjectId != null")
public Subject updateSubject(Subject subject)
```

---

## 3. `unless`

Controls **whether the result should be cached after the method executes**.

If the expression evaluates to `true`, the result is **not cached**.

### Example

```java
@Cacheable(value = "subject", key = "#id", unless = "#result == null")
public Subject getSubject(int id)
```

Meaning:

```
Do not cache null results
```

### Example with Optional

```java
@Cacheable(value = "subject", key = "#id",
           unless = "!#result.isPresent()")
public Optional<Subject> getSubject(int id)
```

---

# SpEL Context Variables

Spring provides several built-in variables for use inside SpEL expressions.

| Variable            | Description                           | Example                |
| ------------------- | ------------------------------------- | ---------------------- |
| `#root.methodName`  | Name of the method                    | `#root.methodName`     |
| `#root.method`      | Method object                         | `#root.method.name`    |
| `#root.target`      | Target bean object                    | `#root.target`         |
| `#root.targetClass` | Class of the bean                     | `#root.targetClass`    |
| `#root.args`        | Array of method arguments             | `#root.args[0]`        |
| `#p0`, `#p1`        | Access arguments by index             | `#p0`                  |
| `#a0`, `#a1`        | Alias for parameter index             | `#a1`                  |
| `#parameterName`    | Access argument by name               | `#subjectId`           |
| `#result`           | Method return value (after execution) | `#result != null`      |
| `#root.caches`      | Collection of caches involved         | `#root.caches[0].name` |

---

# Example Using Multiple SpEL Features

```java
@Cacheable(
    value = "subject",
    key = "#root.methodName + ':' + #subjectId",
    condition = "#subjectId > 0",
    unless = "#result == null"
)
public Subject findSubjectById(int subjectId) {
    return repository.findById(subjectId).orElse(null);
}
```

### Resulting Cache Key

```
findSubjectById:10
```

---

# Example with Cache Eviction

```java
@Caching(evict = {
    @CacheEvict(value="subject", key="#subject.subjectId"),
    @CacheEvict(value="subject", key="'subList'")
})
public Subject updateSubject(Subject subject){
    return repository.save(subject);
}
```

This ensures both:

```
subject:<id>
subject:subList
```

remain consistent after updates.

---

# Summary

| SpEL Attribute | When Evaluated          | Purpose                            |
| -------------- | ----------------------- | ---------------------------------- |
| `key`          | Before caching          | Defines cache key                  |
| `condition`    | Before method execution | Determines if caching should occur |
| `unless`       | After method execution  | Prevents caching based on result   |

---

SpEL enables dynamic behavior in Spring annotations, making caching logic flexible without requiring additional code.
