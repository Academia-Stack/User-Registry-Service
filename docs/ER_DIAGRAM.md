# Entity relation case study
## Scenario 1
We have a **Student** entity and a **Course** entity. Any number of students can be enrolled in any number of courses, i.e, **Student** and **Entity** has ManyToMany relationship.
<br/>We can model this relationship using 2 ways.
### 1. Without using a joining entity

```java
@Entity
public class Student {
    // TODO: declare other attributes

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> courses;
}
```
## 2. Using joining entity

```java
import jakarta.persistence.Entity;

@Entity
public class Enrollment {
    private Student;
    private Course;
}
```
## ER Diagram
```mermaid
erDiagram
    STUDENT ||--o{ ENROLLMENT : has
    COURSE  ||--o{ ENROLLMENT : has
    TEACHER ||--o{ COURSE : teaches

    STUDENT {
        UUID studentId PK
        VARCHAR name
        DATE dob
    }

    COURSE {
        UUID courseId PK
        VARCHAR title
        INT credits
        UUID teacherId FK
    }

    TEACHER {
        UUID teacherId PK
        VARCHAR teacher_name
        INT experience
    }

    ENROLLMENT {
        BIGINT studentId FK
        BIGINT courseId  FK
        DATE admission_date
        VARCHAR status
        %% Composite PK (student_id, course_id) is common
    }
```
***