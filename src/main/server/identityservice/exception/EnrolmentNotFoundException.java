package identityservice.exception;

public class EnrolmentNotFoundException extends RuntimeException {
    public EnrolmentNotFoundException(String message) {
        super(message);
    }
}
