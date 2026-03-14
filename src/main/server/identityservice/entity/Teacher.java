package identityservice.entity;

import helpers.DbColumns;
import helpers.DbTables;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.*;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = DbTables.TEACHER_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)  // very important for type conversion
    @Column(name = DbColumns.SUBJECT_TEACHER_ID, length = 16, nullable = false, updatable = false)
    private UUID teacherId;

    @NotBlank(message = "Teacher name cannot be empty")  // jackson annotation for request body validation
    @Column(nullable = false)
    private String teacherName;

    @NotBlank(message = "We don't hire inexperience teachers")  // jackson annotation for request body validation
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