package app.exception;

public class InvalidTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid confirmation token!";
    }
}
