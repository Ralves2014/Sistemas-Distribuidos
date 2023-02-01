package sd.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;



public class AnuncioImpl extends UnicastRemoteObject implements Anuncio, java.io.Serializable  {
     
    PostgresConnector connect_db;
    Statement statement;
    
    public AnuncioImpl(PostgresConnector c) throws java.rmi.RemoteException {
    	super();
        this.connect_db = c;
        statement=connect_db.getStatement();
        
    }

    @Override
    public int regist_anuncio(String localizacao,int preco,String genero,String anunciante,String tipo_alojamento,String tipo_anuncio) throws Exception {
       
        ResultSet rs;
        int current_aid=1;
        
        String max_aid = "SELECT MAX(aid) FROM anuncios";
        statement.executeQuery(max_aid);
         
        rs = statement.executeQuery(max_aid);
        if(rs.next()) {
            current_aid = rs.getInt(1);
        }
        
        String insert_to_database = "INSERT INTO anuncios(localizacao,preco,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,data) VALUES('" + localizacao + "','" + preco + "','" +genero+ "','" +anunciante+ "','" +tipo_alojamento+ "','" + tipo_anuncio + "','Inativo','"+ LocalDateTime.now().toString() + "')";
        
        statement.executeUpdate(insert_to_database);
        
        System.out.println("Anúncio Inserido!");
        print_anuncio(current_aid, localizacao, preco, LocalDateTime.now().toString(), genero, anunciante, tipo_alojamento, tipo_anuncio, "Inativo");
        return current_aid;
    }

    @Override
    public LinkedList<Anuncio_Entity> listarAnuncios_oferta() throws Exception {
        LinkedList<Anuncio_Entity> list = new LinkedList<>();
     
        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");
            System.out.println("Lista de anúncios de oferta retornada.");

            while (result.next()) {
                String localizacao = result.getString(1);
                int preco = result.getInt(2);
                String data = result.getString(3);
                String genero = result.getString(4);
                String anunciante = result.getString(5);
                String tipo_alojamento = result.getString(6);
                String tipo_anuncio = result.getString(7);
                String estado = result.getString(8);
                int aid = result.getInt(9);

                Anuncio_Entity a = new Anuncio_Entity(aid, localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);
                
                if ((estado.equals("Ativo")||estado.equals("ativo"))&&(tipo_anuncio.equals("oferta")||tipo_anuncio.equals("Oferta"))) {
                    list.add(a);
                }
            }
        }
        
        if (list.isEmpty())
            System.out.println("Não há anúncios de oferta ativos neste momento.");
        
        return list;
    }

    @Override
    public LinkedList<Anuncio_Entity> listarAnuncios_procura() throws Exception {
        LinkedList<Anuncio_Entity> list = new LinkedList<>();

        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");

            while (result.next()) {
                String localizacao = result.getString(1);
                int preco = result.getInt(2);
                String data = result.getString(3);
                String genero = result.getString(4);
                String anunciante = result.getString(5);
                String tipo_alojamento = result.getString(6);
                String tipo_anuncio = result.getString(7);
                String estado = result.getString(8);
                int aid = result.getInt(9);

                Anuncio_Entity a = new Anuncio_Entity(aid, localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);

                if ((estado.equals("Ativo")||estado.equals("ativo"))&&(tipo_anuncio.equals("procura")||tipo_anuncio.equals("Procura"))) {
                    list.add(a);
                }
            }
        }
        
        if (list.isEmpty())
            System.out.println("Não há anúncios de procura ativos neste momento.");
        else
            System.out.println("Lista de anúncios de procura retornada");
        
        return list;
    }
    
    @Override
    public LinkedList<Anuncio_Entity> listarAnuncios(String nome_anunciante) throws Exception {
        LinkedList<Anuncio_Entity> list = new LinkedList<>();

        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");
            
            while (result.next()) {
                String localizacao = result.getString(1);
                int preco = result.getInt(2);
                String data = result.getString(3);
                String genero = result.getString(4);
                String anunciante = result.getString(5);
                String tipo_alojamento = result.getString(6);
                String tipo_anuncio = result.getString(7);
                String estado = result.getString(8);
                int aid = result.getInt(9);

                Anuncio_Entity a = new Anuncio_Entity(aid,localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);

                if (anunciante.equals(nome_anunciante)) {
                    list.add(a);
                }
            }
        }
        
        if (list.isEmpty())
            System.out.println("Não há anúncios deste anunciante.");
        else
            System.out.println("Lista de anúncios do anunciante " + nome_anunciante + " :");
        return list;
    }

    @Override
    public String get_details(int aid) throws Exception {
        String str = "";
        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");
            
            String localizacao; 
            int preco; 
            String data; 
            String genero;
            String anunciante; 
            String tipo_alojamento; 
            String tipo_anuncio; 
            String estado; 
            while (result.next()) {
                int id = result.getInt(9);
                if (aid==id) {
                    localizacao = result.getString(1);
                    preco = result.getInt(2);
                    data = result.getString(3);
                    genero = result.getString(4);
                    anunciante = result.getString(5);
                    tipo_alojamento = result.getString(6);
                    tipo_anuncio = result.getString(7);
                    estado = result.getString(8); 
                    str=print_anuncio(aid, localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);
                }
            }
        }
        System.out.println("Detalhes do anúncio visíveis.");
        return str;
    }

    @Override
    public void send_message(String aid, String message, String remetente) throws Exception {
        
        if (statement!=null) {
            String insert_to_database = "INSERT INTO mensagens(aid,remetente,mensagens) VALUES('" + aid + "','" + remetente + "','" +message+"')";
            System.out.println(insert_to_database);
            statement.executeUpdate(insert_to_database);
        }
        System.out.println("Mensagem enviada com sucesso!");
    }

    @Override
    public LinkedList<String> get_messages(int aid) throws Exception {
        LinkedList<String> list = new LinkedList<>();
        
        if(statement!=null){
            ResultSet result = statement.executeQuery("SELECT mensagens,remetente FROM mensagens WHERE aid='"+Integer.toString(aid)+"'");
            String res;
            while(result.next()){
                String message = result.getString(1);
                String remetente = result.getString(2);
                
                res=remetente+":"+ message + "\n"; 
                list.add(res);
            }
        }
        System.out.println("Visualização de todas as mensagens de um anúncio.");
        return list;
    }
    
    //Admin
    
    @Override
    public LinkedList<Anuncio_Entity> listarAnuncios_ativos() throws Exception {
        LinkedList<Anuncio_Entity> list = new LinkedList<>();
     
        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");
            
            while (result.next()) {
                String localizacao = result.getString(1);
                int preco = result.getInt(2);
                String data = result.getString(3);
                String genero = result.getString(4);
                String anunciante = result.getString(5);
                String tipo_alojamento = result.getString(6);
                String tipo_anuncio = result.getString(7);
                String estado = result.getString(8);
                int aid = result.getInt(9);

                Anuncio_Entity a = new Anuncio_Entity(aid, localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);

                if (estado.equals("ativo") || estado.equals("Ativo")) {
                    list.add(a);
                }
            }
        }
        
        System.out.println("Visualização dos anúncios ativos.");
        return list;
    }

    @Override
    public LinkedList<Anuncio_Entity> listarAnuncios_inativos() throws Exception {
        LinkedList<Anuncio_Entity> list = new LinkedList<>();
      
        if (statement!=null) {
            ResultSet result = statement.executeQuery("SELECT localizacao,preco,data,genero,anunciante,tipo_alojamento,tipo_anuncio,estado,aid FROM anuncios");
            
            while (result.next()) {
                String localizacao = result.getString(1);
                int preco = result.getInt(2);
                String data = result.getString(3);
                String genero = result.getString(4);
                String anunciante = result.getString(5);
                String tipo_alojamento = result.getString(6);
                String tipo_anuncio = result.getString(7);
                String estado = result.getString(8);
                int aid = result.getInt(9);

                Anuncio_Entity a = new Anuncio_Entity(aid, localizacao, preco, data, genero, anunciante, tipo_alojamento, tipo_anuncio, estado);

                if (estado.equals("inativo") ||estado.equals("Inativo")) {
                    list.add(a);
                }
            }
        }
        System.out.println("Visualização dos anúncios inativos.");

        return list;
    }

    @Override
    public String aprove_anuncio(String aid) throws Exception {
          
        String str, state="";
        String r = "SELECT estado FROM anuncios WHERE aid = '" + aid + "'";
        ResultSet result = statement.executeQuery(r);
        
        while(result.next()){
            state = result.getString(1);
        }
        
        if (state.equals("ativo")||state.equals("Ativo")) {
            str="Este anúncio já estava aprovado.";
        }
        else if (state.equals("inativo")||state.equals("Inativo")) {
            String res = "UPDATE anuncios SET estado='Ativo' WHERE aid='" + aid+"'";
            statement.executeUpdate(res);
            str="O anúncio foi aprovado com sucesso";
        }
        else{
            str="Mudança não efetuada.";
        }
        
        return str;
    }

    @Override
    public String change_state(String aid) throws Exception {
        
        String str, state="";
        String r = "SELECT estado FROM anuncios WHERE aid = '" + aid + "'";
        ResultSet result = statement.executeQuery(r);
        
        while(result.next()){
            state = result.getString(1);
        }
        
        if (state.equals("ativo")||state.equals("Ativo")) {
            String res = "UPDATE anuncios SET estado='Inativo' WHERE aid='" + aid+"'";
            statement.executeUpdate(res);
            str="Estado do anúncio alterado para inativo.";
        }
        else if (state.equals("inativo")||state.equals("Inativo")) {
            String res = "UPDATE anuncios SET estado='Ativo' WHERE aid='" + aid+"'";
            statement.executeUpdate(res);
            str="Estado do anúncio alterado para ativo.";
        }
        else{
            str="Mudança não efetuada";
        }
        System.out.println(str);
        return str;
    }

    @Override
    public String print_anuncio(int aid, String localizacao, int preco, String data, String genero, String anunciante, String tipo_alojamento, String tipo_anuncio, String estado) throws Exception {
        
        String str = "Anúncio " + aid + ":\n" +
                "Localizacao :" + localizacao+"\n"+
                "Preço :" + preco+"\n"+
                "Data :" + data+"\n"+
                "Genero :" + genero+"\n"+
                "Anunciante :" + anunciante+"\n"+
                "Tipo de Alojamento :" + tipo_alojamento+"\n"+
                "Tipo de anuncio :" + tipo_anuncio+"\n"+
                "Estado :" + estado+"\n";
        return str;
    }
}
