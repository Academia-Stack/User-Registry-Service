# LettuceConnectionFactory and Design Patterns

## LettuceConnectionFactory

`LettuceConnectionFactory` is a Spring Data Redis component responsible for creating and managing Redis connections using the **Lettuce Redis client**. It acts as a factory that provides Redis connections to Spring components such as `RedisTemplate` and `CacheManager`, hiding the complexity of connection setup and lifecycle management.

Example configuration:

```java
@Bean
public LettuceConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory("localhost", 6379);
}
```

Spring uses this factory internally whenever Redis operations are performed.

---

# Factory Design Pattern

The **Factory Design Pattern** is a creational design pattern that provides a centralized method for creating objects without exposing the object creation logic to the client. Instead of instantiating classes directly using `new`, the client requests objects from a factory.

Benefits:

* Encapsulates object creation logic
* Reduces tight coupling between classes
* Makes the code easier to maintain and extend

Example:

```java
interface Shape {
    void draw();
}
```

Concrete implementations:

```java
class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing Square");
    }
}
```

Factory class:

```java
class ShapeFactory {

    public static Shape createShape(String type) {

        if ("circle".equalsIgnoreCase(type)) {
            return new Circle();
        }

        if ("square".equalsIgnoreCase(type)) {
            return new Square();
        }

        throw new IllegalArgumentException("Unknown shape type");
    }
}
```

Usage:

```java
Shape shape1 = ShapeFactory.createShape("circle");
shape1.draw();

Shape shape2 = ShapeFactory.createShape("square");
shape2.draw();
```

The client does not directly instantiate `Circle` or `Square`; the factory handles object creation.

---

# Builder Design Pattern

The **Builder Design Pattern** is used to construct complex objects step-by-step. It is particularly useful when objects have many optional parameters, improving readability and avoiding multiple overloaded constructors.

Benefits:

* Improves readability
* Allows step-by-step object creation
* Avoids telescoping constructors

Example:

```java
class User {

    private String name;
    private int age;
    private String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    public static class Builder {

        private String name;
        private int age;
        private String email;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

Usage:

```java
User user = new User.Builder()
        .setName("Saptarshi")
        .setAge(24)
        .setEmail("saptarshi@example.com")
        .build();
```

The builder pattern provides a flexible and readable way to construct complex objects.
