package app.service.impl;

import app.configuration.UserRole;
import app.entity.ConfirmationToken;
import app.entity.User;
import app.exception.*;
import app.repository.ConfirmationTokenRepository;
import app.repository.UserRepository;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findUserById(userId);
        if (Objects.isNull(user)) {
            throw new WrongIdException();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyListException();
        }
        return users;
    }

    @Override
    public User addNewUser(String username, String email, String password) {
        User user = userRepository.findUserByUsernameIgnoreCase(username);
        if (Objects.nonNull(user)) {
            throw new UsernameIsUsedException();
        }
        user = userRepository.findUserByEmailIgnoreCase(email);
        if (Objects.nonNull(user)) {
            throw new EmailIsUsedException();
        }
        user = User.builder()
                .id(null)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authorities(new HashSet<>(Collections.singletonList(UserRole.ROLE_USER)))
                .build();
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = userRepository.findUserById(userId);
        if (Objects.nonNull(user)) {
            throw new WrongIdException();
        }
        userRepository.deleteUserById(userId);
    }

    @Override
    public void confirmUserAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findConfirmationTokenByToken(token);
        if (Objects.isNull(confirmationToken)) {
            throw new InvalidTokenException();
        }
        User user = userRepository.findUserByEmailIgnoreCase(confirmationToken.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public SimpleMailMessage getConfirmationMessage(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("springbootapp321@gmail.com");
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getToken());
        return mailMessage;
    }
}
