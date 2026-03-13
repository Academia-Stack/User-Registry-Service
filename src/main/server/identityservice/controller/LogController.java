package identityservice.controller;

import helpers.FormattedLog;
import identityservice.entity.LogEntry;
import identityservice.exception.LogNotFoundException;
import identityservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("")
    public List<FormattedLog> getLogs() {
        List<LogEntry> logs = logService.getAllLogs();
        List<FormattedLog> formattedLogs = new ArrayList<>();
        for(LogEntry log: logs)
            formattedLogs.add(
              new FormattedLog(
                      log.getLogId(),
                      log.getFormattedDate(),
                      log.getFormattedTime(),
                      log.getEndPoint(),
                      log.getMethod(),
                      log.getExceptionClass(),
                      log.getMessage())
            );
        return formattedLogs;
    }

    @GetMapping("{logId}")
    public ResponseEntity<FormattedLog> getLogById(@PathVariable int logId) {
        LogEntry log = logService.findLogById(logId)
                .orElseThrow(() -> new LogNotFoundException("Log Not Found with ID: " + logId));
        FormattedLog formattedLog = new FormattedLog(
                log.getLogId(),
                log.getFormattedDate(),
                log.getFormattedTime(),
                log.getEndPoint(),
                log.getMethod(),
                log.getExceptionClass(),
                log.getMessage());
        return new ResponseEntity<>(formattedLog, HttpStatus.OK);
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
