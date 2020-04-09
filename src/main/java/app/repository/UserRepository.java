package app.repository;

import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByEmailIgnoreCase (String email);

    User findUserById(Long userId);

    List<User> findAll();

    void deleteUserById(Long userId);
}
