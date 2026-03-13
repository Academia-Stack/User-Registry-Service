package identityservice.exception;

public class EnrolmentAlreadyExists extends RuntimeException {
    public EnrolmentAlreadyExists(String message) {
        super(message);
    }
}
