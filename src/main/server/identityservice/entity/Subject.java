package identityservice.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import helpers.DbColumns;
import helpers.DbTables;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = DbTables.COURSE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumns.ENROLLMENT_COURSE_ID, length = 30, nullable = false)
    private int courseId;

    @Column(nullable = false)
    private String courseName;

    @ManyToOne
    @JoinColumn(name = DbColumns.SUBJECT_TEACHER_ID)
    @JsonIgnore
    private Teacher teacher;

    /*@OneToMany(
        mappedBy = "course"       // // variable name by which this entity is referenced in Enrollment
        // cascade = CascadeType.REMOVE,
        // orphanRemoval = true
    )
    //@OnDelete(action = OnDeleteAction.CASCADE)  // hibernate specific. won't work with EclipseLink
    @JsonIgnore
    private List<Enrolment> enrolments;*/
}
