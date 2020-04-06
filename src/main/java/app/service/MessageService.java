package app.service;

import app.entity.Message;
import app.entity.User;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    List<Message> getAllMessagesByUsername(String username);

    Message getMessageById(Long messageId);

    Message saveNewMessage(String text, String username);

    void deleteMessageById(Long messageId);
}
