package identityservice.service;

import identityservice.entity.Enrolment;

import java.util.UUID;

public interface IConnectorService {
    void assignTeacherToSubject(UUID subjectId, UUID teacherId);
    Enrolment enrolStudent(UUID subjectId, UUID studentId);
    Enrolment unenrolStudent(UUID subjectId, UUID studentId);
    boolean doesRecordExist(UUID subjectId, UUID studentId);
    int countRecords(UUID subjectId, UUID studentId);
}
