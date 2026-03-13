package identityservice.entity;

import helpers.DbColumns;
import helpers.DbTables;
import helpers.EnrolmentStatus;
import helpers.EnrolmentId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = DbTables.ENROLMENT_TABLE)
public class Enrolment {
    @EmbeddedId
    private EnrolmentId enrolmentId = new EnrolmentId();

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENROLMENT_ID", length = 30, nullable = false)
    private int enrolmentId;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("studentIdentifier")  // maps to studentIdentifier value of enrolmentId
    @JoinColumn(name = DbColumns.ENROLLMENT_STUDENT_ID + "_PK", referencedColumnName = DbColumns.ENROLLMENT_STUDENT_ID)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate hint
    //@Column(nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("courseIdentifier")  // maps to courseIdentifier value of enrolmentId
    @JoinColumn(name = DbColumns.ENROLLMENT_COURSE_ID + "_PK", referencedColumnName = DbColumns.ENROLLMENT_COURSE_ID)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate hint
    //@Column(nullable = false)
    private Subject course;

    @Column(nullable = false)
    private LocalDate enrolmentDate;

    @Column(insertable = false)  // important: otherwise it will be set to NULL during inert
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private EnrolmentStatus enrolmentStatus;
}