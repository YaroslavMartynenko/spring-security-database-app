package app.service;

import app.entity.User;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findUserByEmail(String email);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User addNewUser(String username, String email, String password);

    void deleteUserById(Long userId);

    void confirmUserAccount(String token);
}
