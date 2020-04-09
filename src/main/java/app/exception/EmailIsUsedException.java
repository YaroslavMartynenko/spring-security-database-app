package app.exception;

public class EmailIsUsedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Such email already is used!";
    }
}
