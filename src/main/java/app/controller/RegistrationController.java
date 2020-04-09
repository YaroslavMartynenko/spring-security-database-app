package app.controller;

import app.entity.ConfirmationToken;
import app.entity.User;
import app.exception.UserExistsException;
import app.repository.ConfirmationTokenRepository;
import app.repository.UserRepository;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
//        try {
        //           userService.addNewUser(username, password);
//        } catch (UserExistsException e) {
//            model.addAttribute("message", e.getMessage());
//            return "registration";
//        }

        User user = userService.addNewUser(username, email, password);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("springbootapp321@gmail.com");
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getToken());

        emailSenderService.sendConfirmationEmail(mailMessage);

        return "redirect:/successful-registration";
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findConfirmationTokenByToken(token);


            User user = userRepository.findUserByEmailIgnoreCase(confirmationToken.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);

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
