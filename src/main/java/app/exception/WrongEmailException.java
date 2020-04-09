package app.exception;

public class WrongEmailException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such email does not exist!";
    }
}
