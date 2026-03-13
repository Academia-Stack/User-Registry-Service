package identityservice.repository;

import helpers.EnrolmentId;
import identityservice.entity.Student;
import identityservice.entity.Subject;
import identityservice.entity.Enrolment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {
    @Query("SELECT s FROM Student s JOIN Enrolment e ON e.student.studentId = s.studentId WHERE e.course.courseId = :courseId")
    java.util.ArrayList<Student> findStudentsBySubject(int courseId);

    @Query("SELECT s FROM Subject s JOIN Enrolment e ON e.course.courseId = s.courseId WHERE e.student.studentId = :studentId")
    java.util.List<Subject> findSubjectsOfStudent(int studentId);

    @Query("SELECT e from Enrolment e WHERE e.student.studentId = :studentId AND e.course.courseId = :subjectCode")
    Optional<Enrolment> findEnrolmentByDetails(int studentId, int subjectCode);

    @Query("SELECT COUNT(e) from Enrolment e WHERE e.student.studentId = :studentId AND e.course.courseId = :subjectCode")
    int countEnrolmentByDetails(int studentId, int subjectCode);
}
