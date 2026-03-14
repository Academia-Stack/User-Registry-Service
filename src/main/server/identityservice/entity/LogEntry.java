package identityservice.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID", nullable = false, length = 30, unique = true)
    private long logId;

    private LocalDate createdDate;
    private LocalTime createdTime;

    private String message, endPoint, method, exceptionClass;

    public LogEntry(String message_, String endPoint_, String method_, String exceptionClass_) {
        this.message = message_;
        this.endPoint = endPoint_;
        this.method = method_;
        this.exceptionClass = exceptionClass_;
    }

    // Since we're fetching the default timezone from config file (application.yaml)
    // we need use the TimeZoneConfig bean. So this logic will go in the service layer
    /*@PrePersist
    protected void onCreate() {
        var now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Kolkata"));
        this.createdDate = now.toLocalDate();
        this.createdTime = now.toLocalTime();
    }*/

    public String getFormattedDate(){
        DateTimeFormatter LONG_DATE_FMT = DateTimeFormatter.ofPattern("d MMMM, uuuu", Locale.ENGLISH);
        return createdDate.format(LONG_DATE_FMT);
    }

    public String getFormattedTime(){
        DateTimeFormatter TIME_LOWER_AMPM_FMT = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        return createdTime.format(TIME_LOWER_AMPM_FMT);
    }
}
