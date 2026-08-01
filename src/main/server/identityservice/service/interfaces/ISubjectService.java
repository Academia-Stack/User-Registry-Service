package identityservice.service;

import identityservice.entity.Subject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISubjectService {
    Optional<Subject> findSubjectById(UUID subjectId);
    Subject addSubject(Subject subject);
    List<Subject> getAllSubjects();
    List<Subject> findAllSubjectsOfStudent(UUID studentId);
    List<Subject> findAllSubjectOfTeacher(UUID teacherId);
    List<Subject> findAllSubjectsByName(String name);
    void updateSubjectDetails(Subject subject);
    void deleteSubject(List<UUID> arrayOfIds);
}
