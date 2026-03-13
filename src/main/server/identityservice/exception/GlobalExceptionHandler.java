package identityservice.exception;

import identityservice.entity.LogEntry;
import identityservice.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// import jakarta.validation.ConstraintViolationException;
// import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private LogService logService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception exception, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String[] classPath = exception.getClass().getName().split("\\.");
        String exceptionClass = classPath[classPath.length - 1];

        LogEntry log = new LogEntry(
                exception.getMessage(),
                request.getRequestURI(),
                request.getMethod().toUpperCase(),
                exceptionClass);
        logService.saveLog(log);

        response.put("success", false);
        response.put("error", exception.getMessage());
        return new ResponseEntity<>(response,
                exceptionClass.contains("NotFound") ? HttpStatus.NOT_FOUND :HttpStatus.INTERNAL_SERVER_ERROR);
    }
}