# Dependency Injection
Dependency Injection (DI) is a design pattern used by **Spring IoC** (Inversion of control) to auto-inject dependencies into fields and parameters of methods and constrcutors annotated by the `@Autowired` annotation.

Consider this code segment
```java
class Service {
  Repository repo;
}
```
There are 3 ways to initialize the `repo` varaible using DI.
## Field Injection
```java
class Service {
  @Autowired
  Repository repo;
}
```
***
## Constructor Injection
```java
class Service {
  Repository repo;

  @Autowired
  Service (Repository repo_){
    this.repo = repo_;
  }

  Service (Repository repo_, String message_){
    this.repo = repo_;
    System.out.println(message_);
  }
}
```
**Note:** `@Autowired` is only necessary when there are multiple constructors.
***
## Setter Injection
```java
class Service {
  Repository repo;

  @Autowired
  void setRepo(Repository repo_){
    this.repo = repo_;
  }
}
```
***
# Other ways to use DI
## Using `@Bean` annotation

The `@Bean` annotation is used on a method to indicate that it instantiates, configures, and initializes a new object to be managed by the Spring IoC container. This is typically used within a `@Configuration` class.

Example
---

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

    @Bean
    public Repository repository() {
        return new Repository(); // Assuming Repository is a simple class
    }

    @Bean
    public Service service() {
        // Spring will inject the 'repository' bean created above
        return new Service(repository());
    }
}

// Placeholder classes for the example
class Repository {
    public String getData() {
        return "Data from Repository";
    }
}

class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public String performService() {
        return "Service performing: " + repo.getData();
    }
}
```
***
