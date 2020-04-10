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
@RequestMapping("/main-admin")
public class MainAdminController {

    private final MessageService messageService;


}
