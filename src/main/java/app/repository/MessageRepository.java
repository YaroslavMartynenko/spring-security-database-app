package app.repository;

import app.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAll();

    List<Message> findAllByUser_Username(String username);

    Message findMessageById(Long messageId);

    void deleteMessageById(Long messageId);
}
