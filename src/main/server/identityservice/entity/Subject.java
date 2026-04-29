package identityservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import helpers.DbColumns;
import helpers.DbTables;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = DbTables.COURSE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subject implements Serializable {
    @Id
    @JsonProperty("subjectCode")  // jackson annotation for request body parsing
    @UuidGenerator
    //@JdbcTypeCode(SqlTypes.BINARY)  // very important for type conversion since MySQL doesn't have any UUID type. Not necessary for Postgres
    @Column(name = DbColumns.ENROLLMENT_COURSE_ID, length = 16, nullable = false, updatable = false)
    private UUID courseId;

    @NotBlank(message = "Course name cannot be empty")  // jackson annotation for request body validation
    @Column(nullable = false)
    private String courseName;

    @ManyToOne
    @JoinColumn(name = DbColumns.SUBJECT_TEACHER_ID)
    @OnDelete(action = OnDeleteAction.SET_NULL)
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
