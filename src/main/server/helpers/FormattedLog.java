package helpers;

public record FormattedLog(
        int logId,
        String createdAtDate,
        String createdAtTime,
        String endPoint,
        String requestMethod,
        String exceptionClass,
        String message
) {}
