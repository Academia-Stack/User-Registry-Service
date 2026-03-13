package identityservice.controller;

import identityservice.entity.Subject;
import identityservice.entity.Teacher;
import identityservice.exception.TeacherNotFoundException;
import identityservice.service.ConnectorService;
import identityservice.service.SubjectService;
import identityservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Teacher")
public class TeacherController {

    @Autowired
    private TeacherService TeacherService;

    @Autowired
    private SubjectService subjectService;

    /*@Autowired
    private LogService logService;*/

    @Autowired
    private ConnectorService connectorService;

    @GetMapping("")
    public String getMsg() {
        return "Welcome to Teacher Management System";
    }

    @GetMapping("showAllTeachers")
    public List<Teacher> getAllTeacherDetails(){
        return TeacherService.getAllTeacherDetails();
    }

    @GetMapping("showTeacher/{TeacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int TeacherId) {
        Teacher teacher = TeacherService.findTeacherById(TeacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher Not Found with ID: " + TeacherId));
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @PostMapping(value = "addTeacher",
                consumes = {"application/json", "application/xml"},
                produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> addTeacher(@RequestBody Teacher teacher) {
        System.out.println(teacher);
        TeacherService.addTeacher(teacher);
        Map<String, Object> response = new HashMap<>();
        response.put("TeacherId", teacher.getTeacherId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "getSubjects/{teacherId}")
    public ResponseEntity<List<Subject>> getAllSubjects(@PathVariable int teacherId){
        List<Subject> subjects = subjectService.findAllSubjectOfTeacher(teacherId);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @PostMapping(value = "deleteTeacher",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Map<String, Object>> deleteTeacher(@RequestBody List<Integer> idArray) {
        TeacherService.deleteTeacher(idArray);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Teacher Deleted Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("assignTeacher/{teacherId}/{subjectId}")
    public ResponseEntity<Map<String, Object>> assignTeacherToStudent(@PathVariable int teacherId, @PathVariable int subjectId) {
        connectorService.assignTeacherToSubject(subjectId, teacherId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Teacher Assigned Successfully!");
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