package sd;

 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Message_Repository extends JpaRepository<Message_Entity, Integer> {
    
    Optional<List<Message_Entity>> findAllByAid(Integer aid);
}
