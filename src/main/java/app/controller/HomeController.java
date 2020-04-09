package app.controller;

import app.configuration.UserRole;
import app.entity.Message;
import app.entity.User;
import app.exception.EmptyListException;
import app.service.MessageService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
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
        List<Message> messages = Collections.emptyList();
       try {
            messages = messageService.getAllMessages();
       } catch (EmptyListException e) {
           model.addAttribute("username", username);
           model.addAttribute("messages", messages);
           model.addAttribute("message", e.getMessage());
       }
        model.addAttribute("username", username);
        model.addAttribute("messages", messages);
        return getViewForUser(user.getAuthorities());
    }

    @PostMapping()
    public String addNewMessage(@AuthenticationPrincipal User user, @RequestParam String text, Model model) {
        messageService.saveNewMessage(text, user.getUsername());
        List<Message> messages = messageService.getAllMessages();
        String username = user.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("messages", messages);
        return "home";
    }

    private String getViewForUser (Collection<? extends GrantedAuthority> authorities){
        if (authorities.contains(UserRole.ROLE_ADMIN)){
            return "admin-home";
        }
        if (authorities.contains(UserRole.ROLE_USER)) {
            return "home";
        }
        return "error";
    }


}
