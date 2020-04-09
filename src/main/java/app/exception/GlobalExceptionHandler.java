package app.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NullPointerException.class,
            IllegalArgumentException.class,
            Throwable.class,
            SQLException.class})
    public String handleOtherException() {
        return "redirect:/error";
    }


//    @ExceptionHandler(value ={InternalAuthenticationServiceException.class, UsernameNotFoundException.class})
//    public String handleLoginException(){
//        return "redirect:/login-error";
//    }
}
