package sd;

import jakarta.websocket.server.PathParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(value="/message")

public class Message_Controller {

    private final Message_Service messageService;

    @Autowired
    public Message_Controller(Message_Service messageService){
        this.messageService = messageService;
    }

    @PostMapping(value="/addMessage")
    public void addMessage(@PathParam("message") String message, @PathParam("emissor") String emissor, @PathParam("aid") Integer aid){
        messageService.addMessage(message,emissor,aid);
    }

    @GetMapping(value="/getAllMessagesByAid")
    public Optional<List<Message_Entity>> getAllMessagesByAid(@PathParam("aid") Integer aid){
        return messageService.getAllMessagesByAid(aid);
    }
}
