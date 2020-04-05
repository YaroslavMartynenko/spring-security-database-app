package app.exception;

public class UserExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such username already exists!";
    }
}
