package identityservice.repository;

import identityservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByStudentId(UUID studentId);

    @Query("SELECT s FROM Student s WHERE s.studentName LIKE %?1%")
    List<Student> findStudentsByName(String studentName);

    @Query("SELECT COUNT(s) from Student s WHERE s.email = :email_")
    int findStudentByEmail(String email_);
}
