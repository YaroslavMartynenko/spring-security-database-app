package app.controller;

import app.entity.Message;
import app.entity.User;
import app.service.MessageService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping()
    public String getHomePage(@AuthenticationPrincipal User user, Model model) {
        String username = user.getUsername();
        List<Message> messages = messageService.getAllMessages();
        model.addAttribute("username", username);
        model.addAttribute("messages", messages);
        return "home";
    }

    @PostMapping()
    public String addNewMessage (@AuthenticationPrincipal User user, @RequestParam String text, Model model){
        messageService.saveNewMessage(text, user.getUsername());
        List<Message> messages = messageService.getAllMessages();
        String username = user.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("messages", messages);
        return "home";
    }


}
