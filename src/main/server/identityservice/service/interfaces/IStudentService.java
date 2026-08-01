package identityservice.service;

import identityservice.entity.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStudentService {
    Student addStudent(Student student);
    Optional<Student> findByStudentId(UUID studentId);
    List<Student> getAllStudentDetails();
    List<Student> findStudentsByName(String student_Name);
    List<Student> findStudentBySubject(UUID subjectCode);
    void updateStudentDetails(Student student);
    void deleteStudent(List<UUID> arrayOfIds);
}
