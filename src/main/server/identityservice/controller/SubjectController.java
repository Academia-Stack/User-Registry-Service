package identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import identityservice.entity.Subject;
import identityservice.exception.StudentNotFoundException;
import identityservice.service.ConnectorService;
import identityservice.service.SubjectService;

import java.util.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ConnectorService connectorService;

    @GetMapping("")
    public String getMsg() {
        return "Welcome to Subject Management System";
    }

    @GetMapping("showAllSubjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("showSubject/{subjectId}")
    public ResponseEntity<Subject> getStudentById(@PathVariable UUID subjectId) {
        System.out.println("Subject ID: " + subjectId);
        Subject subject = subjectService.findSubjectById(subjectId)
                .orElseThrow(() -> new StudentNotFoundException("Subject Not Found with ID: " + subjectId));
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping("showStudentsByName/{subjectName}")
    public List<Subject> getSubjectByName(@PathVariable String subjectName) {
        return subjectService.findAllSubjectsByName(subjectName);
    }

    @PostMapping(value = "addSubject",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> addSubject(@RequestBody Subject subject, Errors errors) throws Exception {
        if(errors.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        //System.out.println(subject);
        subjectService.addSubject(subject);
        Map<String, Object> response = new HashMap<>();
        response.put("subjectId", subject.getCourseId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "updateSubject/{subjectId}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Subject> updateSubject(@PathVariable UUID subjectId, @RequestBody @Valid Subject subject, Errors errors) throws Exception {
        if(errors.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        subject.setCourseId(subjectId);
        subjectService.updateSubjectDetails(subject);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @PostMapping(value = "deleteSubject",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> deleteStudent(@RequestBody List<UUID> idArray) {
        subjectService.deleteSubject(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Subject Deleted Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
