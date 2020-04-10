package app.service;

import app.entity.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    void sendConfirmationEmail(User user);
}
