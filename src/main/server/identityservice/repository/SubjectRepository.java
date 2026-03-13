package identityservice.repository;

import org.springframework.data.jpa.repository.Query;
import identityservice.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    java.util.Optional<Subject> findByCourseId(int courseId);

    @Query("SELECT s FROM Subject s WHERE s.teacher.teacherId = :teacherId")
    java.util.List<Subject> getAllSubjectsByTeacher(int teacherId);

    @Query("SELECT s FROM Subject s WHERE s.courseName LIKE %?1%")
    java.util.List<Subject> getAllSubjectsByName(String sName);
}
