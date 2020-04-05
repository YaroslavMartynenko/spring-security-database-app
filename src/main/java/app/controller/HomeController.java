package app.controller;

import app.exception.UserExistsException;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    private final UserService userService;


//    @PostMapping("/login")
//    public String getAuthentication (){
//        return ""; kagis ne nugno etogo metoda Spring sam sdelaet atentifikaciu
//    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userService.addNewUser(username, password);
        } catch (UserExistsException e) {
            String message = "Achtung!"; // needs attach some methods for cheking
            model.addAttribute("message", message);
            return "redirect:/registration";
        }
        return "redirect:/login";
    }

}
