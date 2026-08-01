package identityservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import identityservice.entity.Subject;
import identityservice.exception.EntityNotFoundException;
import identityservice.service.IConnectorService;
import identityservice.service.ISubjectService;

import java.util.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IConnectorService connectorService;

    @GetMapping("")
    public String getMsg() {
        return "Welcome to Subject Management System";
    }

    @GetMapping("showAllSubjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("showSubject/{subjectId}")
    public Subject getStudentById(@PathVariable UUID subjectId) {
        System.out.println("Subject ID: " + subjectId);
        return subjectService.findSubjectById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject Not Found with ID: " + subjectId));
    }

    @GetMapping("showStudentsByName/{subjectName}")
    public List<Subject> getSubjectByName(@PathVariable String subjectName) {
        return subjectService.findAllSubjectsByName(subjectName);
    }

    @PostMapping(value = "addSubject",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Map<String, Object> addSubject(@RequestBody Subject subject, Errors errors) throws Exception {
        if(errors.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        //System.out.println(subject);
        subjectService.addSubject(subject);
        Map<String, Object> response = new HashMap<>();
        response.put("subjectId", subject.getCourseId());
        return response;
    }

    @PostMapping(value = "updateSubject/{subjectId}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Subject updateSubject(@PathVariable UUID subjectId, @RequestBody @Valid Subject subject, Errors errors) throws Exception {
        if(errors.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        subject.setCourseId(subjectId);
        subjectService.updateSubjectDetails(subject);
        return subject;
    }

    @PostMapping(value = "deleteSubject",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Map<String, Object> deleteStudent(@RequestBody List<UUID> idArray) {
        subjectService.deleteSubject(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Subject Deleted Successfully!");
        return response;
    }
}
