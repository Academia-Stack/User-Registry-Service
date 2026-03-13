package identityservice.repository;

import identityservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    java.util.Optional<Student> findByStudentId(int student_Id);

    @Query("SELECT s FROM Student s WHERE s.studentName LIKE %?1%")
    java.util.List<Student> findStudentsByName(String studentName);
}
