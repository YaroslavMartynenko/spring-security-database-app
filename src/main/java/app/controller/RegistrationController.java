package app.controller;

import app.entity.User;
import app.exception.EmailIsUsedException;
import app.exception.UsernameIsUsedException;
import app.service.EmailSenderService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @GetMapping("/registration")
    public ModelAndView getRegistrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView addNewUser(@RequestParam String username, @RequestParam String email,
                                   @RequestParam String password, ModelAndView modelAndView) {
        User user = null;
        try {
            user = userService.addNewUser(username, email, password);
        } catch (UsernameIsUsedException | EmailIsUsedException e) {
            modelAndView.addObject("message", e.getMessage());
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        emailSenderService.sendConfirmationEmail(user);
        modelAndView.setViewName("complete-registration");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(@RequestParam String token, ModelAndView modelAndView) {
        userService.confirmUserAccount(token);
        modelAndView.addObject("message", "Email is confirmed, registration is successful!");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView loginError(ModelAndView modelAndView) {
        String message = "Invalid username and password!";
        modelAndView.addObject("message", message);
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
