package app.service.impl;

import app.entity.Message;
import app.entity.User;
import app.exception.EmptyListException;
import app.exception.WrongIdException;
import app.exception.WrongUsernameException;
import app.repository.MessageRepository;
import app.repository.UserRepository;
import app.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            throw new EmptyListException();
        }
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new WrongUsernameException();
        }
        List<Message> messages = messageRepository.findAllByUser_Username(username);
        if (messages.isEmpty()) {
            throw new EmptyListException();
        }
        return messages;
    }

    @Override
    public Message getMessageById(Long messageId) {
        Message message = messageRepository.findMessageById(messageId);
        if (Objects.isNull(message)) {
            throw new WrongIdException();
        }
        return message;
    }

    @Override
    public Message saveNewMessage(String text, String username) throws WrongUsernameException {
        User user = userRepository.findUserByUsername(username);
        Message message = new Message(null, text, user);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessageById(Long messageId) {
        Message message = messageRepository.findMessageById(messageId);
        if (Objects.isNull(message)) {
            throw new WrongIdException();
        }
        messageRepository.deleteMessageById(messageId);
    }
}
