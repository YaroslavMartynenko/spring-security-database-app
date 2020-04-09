package app.exception;

public class UsernameIsUsedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Such username already is used!";
    }
}
