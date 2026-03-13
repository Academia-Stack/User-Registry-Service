package helpers;

public enum EnrolmentStatus {
    ACTIVE,         // Currently enrolled and participating
    COMPLETED,      // Finished the course successfully
    PASSED,         // Passed the course (optional alternative to COMPLETED)
    FAILED,         // Completed but did not meet passing criteria
    WITHDRAWN,      // Student withdrew after starting
    CANCELLED,      // Enrollment canceled before start
    DEFAULTER,      // Not meeting requirements (fees, attendance, discipline)
    SUSPENDED,      // Temporarily barred from course
    ON_HOLD,        // Temporarily paused enrollment
    IN_PROGRESS,    // Actively studying but not complete
    NOT_STARTED,    // Enrolled but course has not been started
    EXPELLED,       // Permanently removed due to violations
    AUDIT,          // Attending without earning credits
    WAITLISTED ;     // Awaiting seat availability
}