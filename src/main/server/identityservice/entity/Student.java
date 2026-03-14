package identityservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import helpers.DbColumns;
import helpers.DbTables;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = DbTables.STUDENT_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)  // very important for type conversion
    @Column(name = DbColumns.ENROLLMENT_STUDENT_ID, length = 16, nullable = false, updatable = false)
    private UUID studentId;

    @Column(nullable = false)
    @NotBlank(message = "Student needs to have a name")
    private String studentName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Student email field cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false, name = "date_of_birth")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dob;

    /*@OneToMany(
        mappedBy = "student"       // // variable name by which this entity is referenced in Enrollment
        // cascade = CascadeType.REMOVE,
        // orphanRemoval = true
    )
    //@OnDelete(action = OnDeleteAction.CASCADE)  // hibernate specific. won't work with EclipseLink
    private List<Enrolment> enrolments;*/
}

// In a Many-To-Many relationship, neither side owns a foreign key column directly.
// Instead, Hibernate creates (or uses) a join table containing both foreign keys.
//
// Use the 'mappedBy' attribute on the inverse side to indicate which collection
// in the other entity is the owning side of the join table.
//
// Always use List instead of ArrayList in entity fields. Hibernate will replace
// your collection with its own implementation (e.g., PersistentBag, PersistentList).
//
// For details on Hibernate's ManyToMany collection handling, refer to:
// https://docs.jboss.org/hibernate/orm/6.2/javadocs/org/hibernate/collection/spi/PersistentBag.html