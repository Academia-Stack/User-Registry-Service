package identityservice.service;

import identityservice.entity.Subject;
import identityservice.entity.Teacher;
import identityservice.entity.Student;
import identityservice.entity.Enrolment;

import identityservice.exception.EnrolmentNotFoundException;
import identityservice.repository.SubjectRepository;
import identityservice.repository.TeacherRepository;
import identityservice.repository.StudentRepository;
import identityservice.repository.EnrolmentRepository;

import identityservice.exception.StudentNotFoundException;
import identityservice.exception.TeacherNotFoundException;
import identityservice.exception.SubjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ConnectorService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    public void assignTeacherToSubject(int subjectId, int teacherId) {
        Optional<Subject> course = subjectRepository.findByCourseId(subjectId);
        if(course.isEmpty()) throw new SubjectNotFoundException("Subject Not Found with ID: " + subjectId);

        Optional<Teacher> teacher = teacherRepository.findByTeacherId(teacherId);
        if(teacher.isEmpty()) throw  new TeacherNotFoundException("Teacher Not Found with ID: " + teacherId);

        course.get().setTeacher(teacher.get());
        subjectRepository.save(course.get());
    }

    public Enrolment enrolStudent(int subjectId, int studentId){
        Optional<Subject> course = subjectRepository.findByCourseId(subjectId);
        if(course.isEmpty()) throw new SubjectNotFoundException("Subject Not Found with ID: " + subjectId);

        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if(student.isEmpty()) throw  new StudentNotFoundException("Student Not Found with ID: " + studentId);

        Enrolment newEnrolment = new Enrolment();
        newEnrolment.setStudent(student.get());
        newEnrolment.setCourse(course.get());
        newEnrolment.setEnrolmentDate(LocalDate.now());

        enrolmentRepository.save(newEnrolment);
        return newEnrolment;
    }

    public Enrolment unenrolStudent(int subjectId, int studentId){
        Optional<Subject> course = subjectRepository.findByCourseId(subjectId);
        if(course.isEmpty()) throw new SubjectNotFoundException("Subject Not Found with ID: " + subjectId);

        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if(student.isEmpty()) throw  new StudentNotFoundException("Student Not Found with ID: " + studentId);

        Optional<Enrolment> record = enrolmentRepository.findEnrolmentByDetails(studentId, subjectId);
        if(record.isEmpty()) throw new EnrolmentNotFoundException("Enrolment Not Found");

        enrolmentRepository.delete(record.get());
        return record.get();
    }

    public boolean doesRecordExist(int subjectId, int studentId){
        return enrolmentRepository.findEnrolmentByDetails(studentId, subjectId).isPresent();
    }

    public int countRecords(int subjectId, int studentId){
        return enrolmentRepository.countEnrolmentByDetails(studentId, subjectId);
    }
}
