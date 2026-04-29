package identityservice.entity;

import helpers.DbColumns;
import helpers.DbTables;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = DbTables.TEACHER_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher implements Serializable {
    @Id
    @UuidGenerator
    //@JdbcTypeCode(SqlTypes.BINARY)  // very important for type conversion since MySQL doesn't have any UUID type. Not necessary for Postgres
    @Column(name = DbColumns.SUBJECT_TEACHER_ID, length = 16, nullable = false, updatable = false)
    private UUID teacherId;

    @Column(nullable = false)
    @NotBlank(message = "Teacher needs to have a first name")
    @Pattern(regexp = "^[A-Za-z ]+$",
            message = "Only letters and spaces allowed")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Teacher needs to have a last name")
    @Pattern(regexp = "^[A-Za-z ]+$",
            message = "Only letters and spaces allowed")
    private String lastName;

    @NotNull(message = "Experience field cannot be empty")  // jackson annotation for request body validation
    @Min(value = 1, message = "Experience must be at least 1 year")
    @Max(value = 40, message = "Experience cannot exceed 40 years")
    @Column(nullable = false)
    private int experience;

    // we're setting all fields as NotBlank because during form submissions, all the fields will be passed

    /*@OneToMany(
        mappedBy = "teacher"       // variable name by which this entity is referenced in Subject
        // cascade = CascadeType.REMOVE,
        // orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)  // hibernate specific. won't work with EclipseLink
    List<Subject> courses;*/
}

// In case of One-To-Many relationship, the mappedBy attribute is used to tell Hibernate which variable in the child class is the owner of the relationship.
// Do not use ArrayList. Use List.
// Read docs: https://docs.jboss.org/hibernate/orm/6.2/javadocs/org/hibernate/collection/spi/PersistentBag.html