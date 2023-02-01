package sd;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="message")
public class Message_Entity {
    @Id
    
    private int aid;
    private String message;
    private String emissor;
    
    public Message_Entity() {
        this.aid = 0;
        this.message = "";
        this.emissor = "";
    }

    public Message_Entity(String message, String emissor, int aid) {
        this.aid = aid;
        this.message = message;
        this.emissor = emissor;
    }

    public String getMessage(String message, String emissor, int aid) {
        String str = emissor + "para o anuncio " + aid + ": " + message;
        return str;
    }

}
