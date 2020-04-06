package app.exception;

public class WrongUsernameException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such username does not exist!";
    }
}
