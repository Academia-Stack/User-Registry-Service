package identityservice.repository;

import identityservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    java.util.Optional<Teacher> findByTeacherId(UUID teacherId);

    @Query("SELECT t FROM Teacher t WHERE t.teacherName LIKE %?1%")
    java.util.List<Teacher> findTeachersByName(String modelName);

    @Query("SELECT t FROM Teacher t JOIN Subject c ON c.teacher.teacherId = t.teacherId WHERE c.courseName LIKE %?1%")
    java.util.List<Teacher> findTeachersBySubject(String subject);

    @Query("SELECT t FROM Teacher t JOIN Subject c ON c.teacher.teacherId = t.teacherId WHERE c.courseId = :courseId")
    java.util.List<Teacher> findTeachersBySubject(UUID courseId);
}
