package sd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Service
public class Message_Service {
    private Message_Entity message;
    private final Message_Repository messageRepository;

    @Autowired
    public Message_Service(Message_Repository messageRepository){
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public void addMessage(String message, String emissor, Integer aid){
        this.message = new Message_Entity(message,emissor,aid);
        messageRepository.save(this.message);
    }

    @GetMapping
    public Optional<List<Message_Entity>> getAllMessagesByAid(Integer aid){
       return this.messageRepository.findAllByAid(aid);
    }
}