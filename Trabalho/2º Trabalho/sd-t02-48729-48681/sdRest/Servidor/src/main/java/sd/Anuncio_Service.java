package sd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Service
public class Anuncio_Service {

    private Anuncio_Entity anuncio;
    private final Anuncio_Repository anuncioRepository;

    @Autowired
    public Anuncio_Service(Anuncio_Repository anuncioRepository){
        this.anuncioRepository = anuncioRepository;
    }

    @PostMapping
    public void addAnuncio(String localizacao, Integer preco, String data, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio){
        anuncio = new Anuncio_Entity(localizacao,preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio);
        anuncioRepository.save(anuncio);
    }

    @GetMapping
    public Optional<Anuncio_Entity> getAnuncio(Integer aid){
       return anuncioRepository.findAnuncioEntityByAid(aid);
    }

    @GetMapping
    public List<Anuncio_Entity> getAllAnuncios(){
        return anuncioRepository.findAll();
    }

    @GetMapping
    public List<Anuncio_Entity> getAllAnunciosByEstado(String estado){
        
        List<Anuncio_Entity> list;
        list = anuncioRepository.findAllByEstado(estado).orElseThrow( () -> new IllegalStateException("Não existem anúncios com este estado no sistema de momento."));
        
        return list;
    }
    
    @GetMapping
    public List<Anuncio_Entity> getAllAnunciosByTipoanuncio(String tipo_anuncio){
        
        List<Anuncio_Entity> list;
        list = anuncioRepository.findAllByTipoanuncio(tipo_anuncio).orElseThrow( () -> new IllegalStateException("Não existem anúncios deste tipo no sistema de momento."));
        
        return list;
    }
    
    @GetMapping
    public List<Anuncio_Entity> getAllAnunciosByAnunciante(String anunciante){
        
        List<Anuncio_Entity> list;
        list = anuncioRepository.findAllByAnunciante(anunciante).orElseThrow( () -> new IllegalStateException("Não existem anúncios deste tipo no sistema de momento."));
        
        return list;
    }
    
    @GetMapping
    public void change_state(Integer aid){
        
        Anuncio_Entity a= anuncioRepository.findById(aid).orElseThrow( () -> new IllegalStateException("Anuncio non Existant!\n"));
        
        if(a.getestado().equals("inativo"))
            a.set_estado("ativo");
        else
            a.set_estado("inativo");
        
        anuncioRepository.save(a);
    }
    
    @GetMapping
    public void aprove_state(Integer aid){
        
        Anuncio_Entity a= anuncioRepository.findById(aid).orElseThrow( () -> new IllegalStateException("Anuncio non Existant!\n"));
        
        if(a.getestado().equals("inativo"))
            a.set_estado("ativo");
        
        anuncioRepository.save(a);
    }
   
}
