package sd.rmi;

import java.io.Serializable;
import java.sql.Date;

public class Anuncio_Entity implements Serializable{
    private String localizacao;
    private int preco;
    private String data;
    private String genero;
    private String anunciante;
    private String tipo_alojamento;
    private String tipo_anuncio;
    private String estado;
    private int aid;

    public Anuncio_Entity(int aid,String localizacao, int preco, String data, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio, String estado){
        this.aid=aid;
        this.localizacao=localizacao;
        this.preco=preco;
        this.data=data;
        this.genero=genero;
        this.anunciante=anunciante;
        this.tipo_alojamento=tipo_alojamento;
        this.tipo_anuncio=tipo_anuncio;
        this.estado=estado;
    }

    public String getlocalizacao(){
        return localizacao;
    }

    public int getpreco(){
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
        return tipo_anuncio;
    }

    public String getestado(){
        return estado;
    }

    public int getaid(){
        return aid;
    }

}
