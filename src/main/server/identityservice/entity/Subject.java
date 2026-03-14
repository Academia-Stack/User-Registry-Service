package identityservice.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import helpers.DbColumns;
import helpers.DbTables;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = DbTables.COURSE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {
    @Id
    @JsonProperty("subjectCode")  // jackson annotation for request body parsing
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)  // very important for type conversion
    @Column(name = DbColumns.ENROLLMENT_COURSE_ID, length = 16, nullable = false)
    private UUID courseId;

    @NotBlank(message = "Course name cannot be empty")  // jackson annotation for request body validation
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
