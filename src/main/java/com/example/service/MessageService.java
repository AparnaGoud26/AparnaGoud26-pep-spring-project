package com.example.service;
import com.example.entity.Message;
import com.example.exception.SocialMediaException;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@ComponentScan
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
 @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new SocialMediaException("Message text cannot be empty.", 400);
        }
        if (message.getMessageText().length() > 255) {
            throw new SocialMediaException("Message text exceeds 255 characters.", 400);
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new SocialMediaException("User does not exist.", 400);
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageRepository.findMessagesByUserId(userId);
    }


    public int deleteMessage(int messageId) {
        return messageRepository.deleteMessageById(messageId); // Return number of rows deleted
    }
    


    public int updateMessage(Integer id, String newMessageText) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new SocialMediaException("Message not found.", 404));
    
        if (newMessageText == null || newMessageText.trim().isEmpty()) {
            throw new SocialMediaException("Message text cannot be empty.", 400);
        }
        if (newMessageText.length() > 255) {
            throw new SocialMediaException("Message text exceeds 255 characters.", 400);
        }
    
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }
   

}
