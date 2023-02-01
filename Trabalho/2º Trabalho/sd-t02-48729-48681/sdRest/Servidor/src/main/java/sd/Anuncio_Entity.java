package sd;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="anuncio")
public class Anuncio_Entity{

    private @Id @GeneratedValue Integer aid;
    private String localizacao;
    private Integer preco;
    private String data;
    private String genero;
    private String anunciante;
    private String tipo_alojamento;
    private String tipoanuncio;
    private String estado;
    
    public Anuncio_Entity(){
        this.localizacao="";
        this.preco=0;
        this.data="";
        this.genero="";
        this.anunciante="";
        this.tipo_alojamento="";
        this.tipoanuncio="";
        this.estado="inativo";
    }
    
    
    public Anuncio_Entity(String localizacao, Integer preco, String data, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio){
        this.localizacao=localizacao;
        this.preco=preco;
        this.data=data;
        this.genero=genero;
        this.anunciante=anunciante;
        this.tipo_alojamento=tipo_alojamento;
        this.tipoanuncio=tipo_anuncio;
        this.estado="inativo";
    }
    
    public Integer getaid(){
        return aid;
    }

    public String getlocalizacao(){
        return localizacao;
    }

    public Integer getpreco(){
        return preco;
    }

    public String getdata(){
        return data;
    }

    public String getgenero(){
        return genero;
    }

    public String getanunciante(){
        return anunciante;
    }

    public String gettipo_alojamento(){
        return tipo_alojamento;
    }

    public String gettipo_anuncio(){
        return tipoanuncio;
    }

    public String getestado(){
        return estado;
    }
    
    public void set_estado(String estado){
        this.estado=estado;
    }
}
