package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getAllMessagesByID(int idNum) {
        return messageRepository.findByMessage_id(idNum);
    }

    public List<Message> getAllMessagesByUserID(int idNum) {
        return messageRepository.findByPostedBy(idNum);
    }

    public Integer updateMessage(int messId, Message mess){
        Optional<Message> messageOptional = messageRepository.findById(messId);
        if(mess.getMessage_text().length() >0 && mess.getMessage_text().length() < 255 && messageOptional.isPresent()){
            Message message = messageOptional.get();
            message.setMessage_text(mess.getMessage_text());
            messageRepository.save(message);
            return 1;
        }
        else{
            return 0;
        }
    }

    public void deleteMessage(int idNum){
        // code to delete a message with the given ID number
        messageRepository.deleteById(idNum);
    }
}
