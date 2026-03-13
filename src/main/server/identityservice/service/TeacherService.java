package identityservice.service;

import identityservice.entity.Teacher;
import identityservice.exception.TeacherNotFoundException;
import identityservice.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository TeacherRepository;

    public Teacher addTeacher(Teacher teacher) {
        return TeacherRepository.save(teacher);
    }

    public Optional<Teacher> findTeacherById(int student_Id){
        return TeacherRepository.findById(student_Id);
    }

    public List<Teacher> getAllTeacherDetails(){
        return TeacherRepository.findAll();
    }

    public List<Teacher> findTeachersBySubjectName(String subjectName){
        return TeacherRepository.findTeachersBySubject(subjectName);
    }
    public List<Teacher> findTeachersBySubjectId(int subjectId){
        return TeacherRepository.findTeachersBySubject(subjectId);
    }

    public void deleteTeacher(List<Integer> arrayOfIds){
        arrayOfIds.forEach(id ->{
            if(!TeacherRepository.existsById(id)){
                throw new TeacherNotFoundException("Teacher Not Found with ID: "+id);
            }
            TeacherRepository.deleteById(id);
        });
    }
}
