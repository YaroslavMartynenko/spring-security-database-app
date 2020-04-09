package app.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    void sendConfirmationEmail(SimpleMailMessage email);
}
