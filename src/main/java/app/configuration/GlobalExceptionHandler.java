package app.configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NullPointerException.class,
            IllegalArgumentException.class,
            SQLException.class,
            Throwable.class})
    public ModelAndView handleOtherException() {
        return new ModelAndView("error");
    }
}

