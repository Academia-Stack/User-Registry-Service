package identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import identityservice.entity.Student;
import identityservice.entity.Subject;
import identityservice.exception.EnrolmentAlreadyExists;
import identityservice.exception.EntityNotFoundException;
import identityservice.service.IConnectorService;
import identityservice.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;
import identityservice.service.ISubjectService;

import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IConnectorService connectorService;

    @Autowired
    private ISubjectService subjectService;

    /*@Autowired
    private LogService logService;*/

    @GetMapping("")
    public String getMsg() {
        return "Welcome to Student Management System";
    }

    @GetMapping("showAllStudents")
    public List<Student> getAllStudentDetails(){
        return studentService.getAllStudentDetails();
    }

    @GetMapping("showStudent/{studentId}")
    public Student getStudentById(@PathVariable UUID studentId) {
        System.out.println("Student ID: " + studentId);
        return studentService.findByStudentId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student Not Found with ID: " + studentId));
    }

    @GetMapping("showStudentsByName/{studentName}")
    public List<Student> getStudentByName(@PathVariable String studentName) {
        return studentService.findStudentsByName(studentName);
    }

    @PostMapping(value = "addStudent",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> addStudent(@RequestBody @Valid Student student, BindingResult result) throws Exception {
        if(result.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(result.getFieldError()).getDefaultMessage());

        System.out.println(student);
        studentService.addStudent(student);
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", student.getStudentId());
        return response;
    }

    @PostMapping(value = "updateStudent/{studentId}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Student updateStudent(@PathVariable UUID studentId, @RequestBody Student student, BindingResult result) throws Exception {
        if(result.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(result.getFieldError()).getDefaultMessage());

        student.setStudentId(studentId);
        studentService.updateStudentDetails(student);
        return student;
    }

    @GetMapping("getStudentSubjects/{studentId}")
    public List<Subject> getStudentSubjects(@PathVariable UUID studentId){
        return subjectService.findAllSubjectsOfStudent(studentId);
    }

    @PostMapping(value = "deleteStudent",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Map<String, Object> deleteStudent(@RequestBody List<UUID> idArray) {
        studentService.deleteStudent(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student Deleted Successfully!");
        return response;
    }

    @PostMapping("enrolStudent/{studentId}/{subjectId}")
    public Map<String, Object> enrolStudent(@PathVariable UUID studentId, @PathVariable UUID subjectId) {
        if(connectorService.countRecords(subjectId, studentId) >= 1)
            throw new EnrolmentAlreadyExists("Student already enrolled");
        connectorService.enrolStudent(subjectId, studentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student Enrolled Successfully!");
        return response;
    }
}