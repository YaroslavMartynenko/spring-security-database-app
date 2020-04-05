package app.service.impl;

import app.configuration.UserRole;
import app.entity.User;
import app.exception.EmptyListException;
import app.exception.UserExistsException;
import app.exception.WrongIdException;
import app.repository.UserRepository;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
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
    public User addNewUser(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (Objects.nonNull(user)) {
            throw new UserExistsException();
        }
        user = User.builder()
                .id(null)
                .username(username)
                .password(passwordEncoder.encode(password))
                .authorities(new HashSet<>(Arrays.asList(UserRole.ROLE_USER)))
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
}
