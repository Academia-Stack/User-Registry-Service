package identityservice.controller.common;

import identityservice.dto.ExceptionDTO;
import identityservice.entity.LogEntry;
import identityservice.service.IKafkaClient;
import jakarta.annotation.PostConstruct;
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
    private IKafkaClient kafkaClient;

    @PostConstruct
    public void init() {
        System.out.println("GlobalErrorHandler loaded...");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO<Map<String, Object>>> handleGlobalException(
        Exception exception, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String[] classPath = exception.getClass().getName().split("\\.");
        String exceptionClass = classPath[classPath.length - 1];

        LogEntry log = new LogEntry(
            exception.getMessage(),
            request.getRequestURI(),
            request.getMethod().toUpperCase(),
            exceptionClass);
        kafkaClient.sendLogEntry(log);

        response.put("error", exception.getMessage());
        response.put("endpoint", log.getEndPoint());
        response.put("httpMethod", log.getMethod());

        ExceptionDTO<Map<String, Object>> responseObj =
                ExceptionDTO.<Map<String, Object>>builder().data(response).build();

        return new ResponseEntity<ExceptionDTO<Map<String, Object>>>(responseObj,
           exceptionClass.contains("NotFound") ? HttpStatus.NOT_FOUND
           : exceptionClass.contains("AlreadyExists") ? HttpStatus.FORBIDDEN
           : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}