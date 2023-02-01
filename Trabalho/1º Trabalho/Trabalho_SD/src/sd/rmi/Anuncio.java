package sd.rmi;

import java.util.LinkedList;

public interface Anuncio extends java.rmi.Remote{
    
    // Geral
    
    int regist_anuncio(String localizacao, int preco, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio) throws Exception;

    LinkedList<Anuncio_Entity> listarAnuncios_oferta() throws Exception;

    LinkedList<Anuncio_Entity> listarAnuncios_procura() throws Exception;

    LinkedList<Anuncio_Entity> listarAnuncios(String anunciante) throws Exception;

    String get_details(int aid) throws Exception;

    void send_message(String aid ,String message,String remetente) throws Exception;

    LinkedList<String> get_messages(int aid) throws Exception;
    
    // Administração
    
    LinkedList<Anuncio_Entity> listarAnuncios_ativos() throws Exception;

    LinkedList<Anuncio_Entity> listarAnuncios_inativos() throws Exception;
    
    String aprove_anuncio(String aid) throws Exception;

    String change_state(String aid) throws Exception;
    
    //Print
    
    String print_anuncio(int aid,String localizacao, int preco, String data, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio, String estado) throws Exception;
}
