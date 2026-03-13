package identityservice.entity;

import helpers.DbColumns;
import helpers.DbTables;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = DbTables.TEACHER_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumns.SUBJECT_TEACHER_ID, length = 30, nullable = false, unique = true)
    private int teacherId;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private int experience;

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