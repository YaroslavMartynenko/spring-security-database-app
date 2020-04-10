package app.controller;

import app.entity.Message;
import app.entity.User;
import app.exception.EmptyListException;
import app.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
@RequestMapping("/main")
public class HomeController {

    private final MessageService messageService;

    @GetMapping()
    public ModelAndView getHomePage(@AuthenticationPrincipal User user, ModelAndView modelAndView) {
        String username = user.getUsername();
        List<Message> messages = null;
        try {
            messages = messageService.getAllMessages();
        } catch (EmptyListException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        modelAndView.addObject("username", username);
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView addNewMessage(@AuthenticationPrincipal User user,
                                      @RequestParam String text,
                                      ModelAndView modelAndView) {
        messageService.saveNewMessage(text, user.getUsername());
        List<Message> messages = messageService.getAllMessages();
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("main");
        return modelAndView;
    }

}
