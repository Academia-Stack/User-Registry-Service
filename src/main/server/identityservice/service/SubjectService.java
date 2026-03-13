package identityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import identityservice.entity.Subject;
import identityservice.exception.StudentNotFoundException;
import identityservice.exception.SubjectNotFoundException;
import identityservice.repository.EnrolmentRepository;
import identityservice.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    EnrolmentRepository enrolmentRepository;

    @Cacheable(value = "subject", key = "#subjectId", unless = "#result == null")  // value is bucket_name
    public Optional<Subject> findSubjectById(int subjectId){
        return subjectRepository.findByCourseId(subjectId);
    }

    public Subject addSubject(Subject subject){
        return subjectRepository.save(subject);
    }

    //@Cacheable(value = "subject", key = "'subList'") // value is bucket_name
    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    public List<Subject> findAllSubjectsOfStudent(int studentId){
        return enrolmentRepository.findSubjectsOfStudent(studentId);
    }

    public List<Subject> findAllSubjectOfTeacher(int teacherId){
        return subjectRepository.getAllSubjectsByTeacher(teacherId);
    }

    public List<Subject> findAllSubjectsByName(String name){
        return subjectRepository.getAllSubjectsByName(name);
    }

    @CacheEvict(value = "subject", key = "#subject.subjectId")
    public void updateSubjectDetails(Subject subject){
        Optional<Subject> existingSubjectDetails = subjectRepository.findByCourseId(subject.getCourseId());
        if(existingSubjectDetails.isEmpty())
            throw new SubjectNotFoundException("Student Not Found with ID: " + subject.getCourseId());

        existingSubjectDetails.get().setCourseName(subject.getCourseName());
        subjectRepository.save(existingSubjectDetails.get());
    }

    @CacheEvict(value = "subject", allEntries = true)  // delete all entries
    public void deleteSubject(List<Integer> arrayOfIds){
        arrayOfIds.forEach(id ->{
            if(!subjectRepository.existsById(id)){
                throw new StudentNotFoundException("Student Not Found with ID: "+id);
            }
            subjectRepository.deleteById(id);
        });
    }
}
