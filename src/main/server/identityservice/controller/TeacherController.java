package identityservice.controller;

import identityservice.entity.Subject;
import identityservice.entity.Teacher;
import identityservice.exception.EntityNotFoundException;
import identityservice.service.IConnectorService;
import identityservice.service.ISubjectService;
import identityservice.service.ITeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Teacher")
public class TeacherController {

    @Autowired
    private ITeacherService TeacherService;

    @Autowired
    private ISubjectService subjectService;

    /*@Autowired
    private LogService logService;*/

    @Autowired
    private IConnectorService connectorService;

    @GetMapping("")
    public String getMsg() {
        return "Welcome to Teacher Management System";
    }

    @GetMapping("showAllTeachers")
    public List<Teacher> getAllTeacherDetails(){
        return TeacherService.getAllTeacherDetails();
    }

    @GetMapping("showTeacher/{TeacherId}")
    public Teacher getTeacherById(@PathVariable UUID TeacherId) {
        return TeacherService.findTeacherById(TeacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher Not Found with ID: " + TeacherId));
    }

    @PostMapping(value = "addTeacher",
                consumes = {"application/json", "application/xml"},
                produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> addTeacher(@RequestBody @Valid Teacher teacher, Errors errors) throws Exception {
        if(errors.hasFieldErrors())
            throw new Exception(
                    Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());

        TeacherService.addTeacher(teacher);
        Map<String, Object> response = new HashMap<>();
        response.put("teacherId", teacher.getTeacherId());
        return response;
    }

    @GetMapping(value = "getSubjects/{teacherId}")
    public List<Subject> getAllSubjects(@PathVariable UUID teacherId){
        return subjectService.findAllSubjectOfTeacher(teacherId);
    }

    @PostMapping(value = "deleteTeacher",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public Map<String, Object> deleteTeacher(@RequestBody List<UUID> idArray) {
        TeacherService.deleteTeacher(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Teacher Deleted Successfully!");
        return response;
    }

    @PostMapping("assignTeacher/{teacherId}/{subjectId}")
    public Map<String, Object> assignTeacherToStudent(
            @PathVariable UUID teacherId, @PathVariable UUID subjectId) {
        connectorService.assignTeacherToSubject(subjectId, teacherId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Teacher Assigned Successfully!");
        return response;
    }
}