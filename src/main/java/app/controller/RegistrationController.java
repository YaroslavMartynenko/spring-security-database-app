package app.controller;

import app.entity.User;
import app.repository.ConfirmationTokenRepository;
import app.service.EmailSenderService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.addNewUser(username, email, password);
        SimpleMailMessage mailMessagee = userService.getConfirmationMessage(user);
        emailSenderService.sendConfirmationEmail(mailMessagee);
        return "redirect:/complete-registration";
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam String token) {
        userService.confirmUserAccount(token);
        model.addAttribute("message", "Email is confirmed, registration successful!");
        return "redirect:/login";
    }

    @PostMapping("/login-error")
    public String loginError(Model model) {
        String message = "Invalid username and password!";
        model.addAttribute("message", message);
        return "login";
    }
}
