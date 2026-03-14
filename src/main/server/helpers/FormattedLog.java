package helpers;

public record FormattedLog(
        long logId,
        String createdAtDate,
        String createdAtTime,
        String endPoint,
        String requestMethod,
        String exceptionClass,
        String message
) {}
