package sd;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface Anuncio_Repository extends JpaRepository<Anuncio_Entity, Integer> {

    Optional<Anuncio_Entity> findAnuncioEntityByAid(Integer aid);
    Optional<List<Anuncio_Entity>> findAllByTipoanuncio(String tipo_anuncio);
    Optional<List<Anuncio_Entity>> findAllByAnunciante(String anunciante);
    Optional<List<Anuncio_Entity>> findAllByEstado(String estado);

}
