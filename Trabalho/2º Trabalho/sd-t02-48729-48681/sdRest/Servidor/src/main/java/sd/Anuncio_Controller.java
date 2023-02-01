package sd;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/anuncio")

public class Anuncio_Controller {

    private Anuncio_Service anuncioService;

    @Autowired
    public Anuncio_Controller(Anuncio_Service anuncioService){
        this.anuncioService = anuncioService;
    }

    @PostMapping(value="/addAnuncio", produces = "application/json")
    public void addAnuncio(@PathParam("localizacao") String localizacao, @PathParam("preco") Integer preco,
            @PathParam("data") String data,@PathParam("genero") String genero, @PathParam("anunciante") String anunciante,
            @PathParam("tipo_alojamento") String tipo_alojamento, @PathParam("tipo_anuncio") String tipo_anuncio){
        anuncioService.addAnuncio(localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio);
    }

    @GetMapping(value="/getAnuncio")
    public Optional<Anuncio_Entity> getAnuncio(@PathParam("aid") Integer aid){
        return anuncioService.getAnuncio(aid);
    }

    @GetMapping(value="/getAllAnuncios")
    public List<Anuncio_Entity> getAllAnuncios(){
        return anuncioService.getAllAnuncios();
    }

    @GetMapping(value="/getAnunciosByEstado")
    public List<Anuncio_Entity> getAllAnunciosByEstado(@PathParam("estado") String estado){
        return anuncioService.getAllAnunciosByEstado(estado);
    }
    
    @GetMapping(value="/getAnunciosByTipoAnuncio")
    public List<Anuncio_Entity> getAllAnunciosByTipoanuncio(@PathParam("tipo_anuncio") String tipo_anuncio){
        return anuncioService.getAllAnunciosByTipoanuncio(tipo_anuncio);
    }
    
    @GetMapping(value="/getAnunciosByAnunciante")
    public List<Anuncio_Entity> getAllAnunciosByAnunciante(@PathParam("anunciante") String anunciante){
        return anuncioService.getAllAnunciosByAnunciante(anunciante);
    }
    
    @PutMapping(value="/changestate")
    public void change_state(@PathParam("aid") Integer aid){
        anuncioService.change_state(aid);
    }
    
    @PutMapping(value="/aprovestate")
    public void aprove_state(@PathParam("aid") Integer aid){
        anuncioService.change_state(aid);
    }
}
