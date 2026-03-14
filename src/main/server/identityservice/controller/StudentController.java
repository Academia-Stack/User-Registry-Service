package identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import identityservice.entity.Student;
import identityservice.entity.Subject;
import identityservice.exception.EnrolmentAlreadyExists;
import identityservice.exception.StudentNotFoundException;
import identityservice.service.ConnectorService;
import identityservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import identityservice.service.SubjectService;

import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ConnectorService connectorService;

    @Autowired
    private SubjectService subjectService;

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
    public ResponseEntity<Student> getStudentById(@PathVariable UUID studentId) {
        System.out.println("Student ID: " + studentId);
        Student student = studentService.findByStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found with ID: " + studentId));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("showStudentsByName/{studentName}")
    public List<Student> getStudentByName(@PathVariable String studentName) {
        return studentService.findStudentsByName(studentName);
    }

    @PostMapping(value = "addStudent",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> addStudent(@RequestBody @Valid Student student, BindingResult error) throws Exception {
        if(error.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(error.getFieldError()).getDefaultMessage());

        System.out.println(student);
        studentService.addStudent(student);
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", student.getStudentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "updateStudent/{studentId}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Student> updateStudent(@PathVariable UUID studentId, @RequestBody Student student, BindingResult error) throws Exception {
        if(error.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(error.getFieldError()).getDefaultMessage());

        student.setStudentId(studentId);
        studentService.updateStudentDetails(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("getStudentSubjects/{studentId}")
    public List<Subject> getStudentSubjects(@PathVariable UUID studentId){
        return subjectService.findAllSubjectsOfStudent(studentId);
    }

    @PostMapping(value = "deleteStudent",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> deleteStudent(@RequestBody List<UUID> idArray) {
        studentService.deleteStudent(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student Deleted Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("enrolStudent/{studentId}/{subjectId}")
    public ResponseEntity<Map<String, Object>> enrolStudent(@PathVariable UUID studentId, @PathVariable UUID subjectId) {
        if(connectorService.countRecords(subjectId, studentId) >= 1)
            throw new EnrolmentAlreadyExists("Student already enrolled");
        connectorService.enrolStudent(subjectId, studentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student Enrolled Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception exception, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        LogEntry log = new LogEntry(
                exception.getMessage(),
                request.getRequestURI(),
                request.getMethod().toUpperCase(),
                exception.getClass().getName());
        logService.saveLog(log);

        response.put("success", false);
        response.put("error", exception.getMessage());
        return new ResponseEntity<>(response,
                exception.getClass().getName().contains("NotFound") ? HttpStatus.NOT_FOUND :HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}