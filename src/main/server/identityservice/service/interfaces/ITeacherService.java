package identityservice.service;

import identityservice.entity.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITeacherService {
    Teacher addTeacher(Teacher teacher);
    List<Teacher> getTeacherByName(String name);
    Optional<Teacher> findTeacherById(UUID studentId);
    List<Teacher> getAllTeacherDetails();
    List<Teacher> findTeachersBySubjectName(String subjectName);
    List<Teacher> findTeachersBySubjectId(UUID subjectId);
    void deleteTeacher(List<UUID> arrayOfIds);
}
