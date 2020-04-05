package app.service;

import app.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserById(Long userId);

    List<User> getAllUsers();

    User addNewUser(String username, String password);

    void deleteUserById(Long userId);
}
