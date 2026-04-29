package identityservice.entity;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEntry implements Serializable {
    private UUID logId;

    private Instant timestamp;

    private String message, endPoint, method, exceptionClass;

    public LogEntry(String message_, String endPoint_, String method_, String exceptionClass_) {
        this.message = message_;
        this.endPoint = endPoint_;
        this.method = method_;
        this.exceptionClass = exceptionClass_;
    }
}
