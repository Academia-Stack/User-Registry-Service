# User Registry Service

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.2/reference/web/servlet.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.2/reference/using/devtools.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.2/reference/data/sql.html#data.sql.jpa-and-spring-data)
***
## Guides
The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
***
## Important Annotations
***
`@JsonIgnore`
- 
- Ignore a property during serialization (no persistance while creating the object) and desrialization (fetching an object the entity).
- This annotation is often used for nested entities.
### Usage
```java
@JsonIgnore
private Entity entityObject;
```
Fully-qualified classpath: `com.fasterxml.jackson.annotation.JsonIgnore`.
***
`@JoinColumn`
- 
- This annotation is used to create foreign keys.
- In a many-to-one relation, the foreign key is always created in the many-entity.
- Fully-qualified classpath: `jakarta.persistence.ManyToOne`.
### Usage
Let's say we have course entity (many) and teacher entity (one), i.e, one teacher teaches many courses.
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "teacher_id")
private Teacher teacher;
```
Here `fetch = FetchType.LAZY` signifies that we don't automatically fetch the **teacher** object when we're fetching the **course** object. It is only fetched when we call `course.getTeacher()`.
<br/>Fully-qualified classpath for both these annotations:
- `@JoinColumn`: `jakarta.persistence.JoinColumn`.
- `@ManyToOne`: `jakarta.persistence.ManyToOne`.
***
## Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
***

