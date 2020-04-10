package app.service.impl;

import app.entity.ConfirmationToken;
import app.entity.User;
import app.repository.ConfirmationTokenRepository;
import app.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("application");


    @Override
    public void sendConfirmationEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        String port = resourceBundle.getString("server.port");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:" + port + "/confirm-account?token=" + confirmationToken.getToken());
        send(mailMessage);
    }

    @Async
    public void send (SimpleMailMessage mailMessage){
        javaMailSender.send(mailMessage);
    }

}
