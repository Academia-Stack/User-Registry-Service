package identityservice.service;

import identityservice.entity.Student;
import identityservice.exception.StudentAlreadyExists;
import identityservice.exception.StudentNotFoundException;
import identityservice.repository.EnrolmentRepository;
import identityservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    public Student addStudent(Student student) {
        int isPresent = studentRepository.findStudentByEmail(student.getEmail());
        if(isPresent >= 1)
            throw new StudentAlreadyExists("Email already in use");
        return studentRepository.save(student);  // constraint errors can occur
    }

    public Optional<Student> findByStudentId(UUID studentId){
        return studentRepository.findById(studentId);
    }

    public List<Student> getAllStudentDetails(){
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByName(String student_Name){
        return studentRepository.findStudentsByName(student_Name);
    }

    public List<Student> findStudentBySubject(UUID subjectCode){
        return enrolmentRepository.findStudentsBySubject(subjectCode);
    }

    public void updateStudentDetails(Student student){
        Optional<Student> existingStudentDetails = studentRepository.findByStudentId(student.getStudentId());
        if(existingStudentDetails.isEmpty())
            throw new StudentNotFoundException("Student Not Found with ID: " + student.getStudentId());

        existingStudentDetails.get().setStudentName(student.getStudentName());
        existingStudentDetails.get().setEmail(student.getEmail());
        existingStudentDetails.get().setDob(student.getDob());
        studentRepository.save(existingStudentDetails.get());
    }

    public void deleteStudent(List<UUID> arrayOfIds){
        arrayOfIds.forEach(id ->{
            if(!studentRepository.existsById(id)){
                throw new StudentNotFoundException("Student Not Found with ID: "+id);
            }
            studentRepository.deleteById(id);
        });
    }
}
