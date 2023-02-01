package sd;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClienteGeral {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String sendGet(String url) {

        HttpGet request = new HttpGet(url);
        String result = "";

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String sendPost(String url) {
        HttpPost post = new HttpPost(url);
        String s = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            s = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;

    }
    
    public void sendPut(String url) {
        HttpPut put = new HttpPut(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(put)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showMenu() {
        System.out.println("");
        System.out.println(" _____________               BEM VINDO UTILIZADOR                _____________");
        System.out.println("|	Insere o comando que quer executar:	                              |");
        System.out.println("|	ra - Registar novo anúncio                                            |");
        System.out.println("|	lo - Listar anúncios do tipo oferta                                   |");
        System.out.println("|	lp - Listar anúncios do tipo procura                                  |");
        System.out.println("|	la - Listar todos os anúncios de um anunciante                        |");
        System.out.println("|	od - Obter detalhes de um anúncio                                     |");
        System.out.println("|	em - Enviar mensagem ao anunciante de um anuncio  	              |");
        System.out.println("|	cm - Consultar todas as mensagens de um anuncio	                      |");
        System.out.println("|	s - Sair                                                              |");
        System.out.println("|_____________________________________________________________________________|");
        System.out.println("");
    }

    
    public static void main(String args[]) {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {

            ClienteGeral client = new ClienteGeral();
            String command_choice, message, remetente;
            String url;
            String str = "";
            int num;
            Object obj = new Object();
            
            showMenu();
            
            while(true){
                
                System.out.print("-> ");
                command_choice = input.readLine();
                
                switch(command_choice){
                    case "ra":
                        System.out.println("Insira os seguintes campos:");
                        System.out.print("localização -> ");
                        String localizacao = input.readLine();
                        System.out.print("preço -> ");
                        int preco = Integer.parseInt(input.readLine());
                        System.out.print("genero -> ");
                        String genero = input.readLine();
                        System.out.print("anunciante -> ");
                        String anunciante = input.readLine();
                        System.out.print("tipo de alojamento -> ");
                        String tipo_alojamento = input.readLine();
                        System.out.print("tipo de anúncio -> ");
                        String tipo_anuncio = input.readLine();
                        
                        String data = LocalDateTime.now().toString();
                        
                        url = "http://www.localhost:8090/anuncio/addAnuncio?" + "&localizacao=" + localizacao + "&preco=" + preco
                                + "&data=" + data + "&genero=" + genero + "&anunciante=" + anunciante + "&tipo_alojamento=" + tipo_alojamento
                                + "&tipo_anuncio=" + tipo_anuncio;
                        
                        System.out.println(url);
                        str = client.sendPost(url);
                        System.out.println(str);
                        
                        System.out.println("Anúncio inserido na base de dados com sucesso!");
                        System.out.println("#################################################################");
                        break;
                        
                    case "lo":
                        
                        ArrayList<String> lista_anuncios_oferta = new ArrayList<>();
                        url = "http://www.localhost:8090/anuncio/getAnunciosByTipoAnuncio?" + "tipo_anuncio=oferta";
                        
                        str = client.sendGet(url);
                        JSONArray arr = JsonPath.read(str, "$.[?(@['estado'] == 'ativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])].['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");
                        
                        for (int i = 0; i < arr.toArray().length; i++) {
                            obj = arr.get(i);

                            if (!lista_anuncios_oferta.contains(obj.toString()))
                                lista_anuncios_oferta.add(obj.toString());
                        }
                        
                        if(lista_anuncios_oferta.isEmpty())
                            System.out.println("Não há anúncios de oferta ativos neste momento.");
                        else{
                            System.out.println("Lista de anúncios de oferta:");
                            System.out.println(lista_anuncios_oferta);
                        }
                        
                        System.out.println("#################################################################");
                        break;
                    case "lp":
                        
                        ArrayList<String> lista_anuncios_procura = new ArrayList<>();
                        url = "http://www.localhost:8090/anuncio/getAnunciosByTipoAnuncio?" + "tipo_anuncio=procura";
                        
                        str = client.sendGet(url);
                        JSONArray arr2 = JsonPath.read(str, "$.[?(@['estado'] == 'ativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])].['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");
                        
                        for (int i = 0; i < arr2.toArray().length; i++) {
                            obj = arr2.get(i);

                            if (!lista_anuncios_procura.contains(obj.toString()))
                                lista_anuncios_procura.add(obj.toString());
                        }
                        
                        if(lista_anuncios_procura.isEmpty())
                            System.out.println("Não há anúncios de procura ativos neste momento.");
                        else{
                            System.out.println("Lista de anúncios de procura:");
                            System.out.println(lista_anuncios_procura);
                        }
                        
                        System.out.println("#################################################################");
                        break;
                    case "la":
                        System.out.print("Nome do anunciante -> ");
                        message = input.readLine();
                        
                        ArrayList<String> lista_anuncios_anunciante = new ArrayList<>();
                        
                        url = "http://www.localhost:8090/anuncio/getAnunciosByAnunciante" + "anunciante=" + message;
                        
                        str = client.sendGet(url);
                        
                        JSONArray arr3 = JsonPath.read(str, "$.[?(@['estado'] == 'ativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])]"
                                + ".['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");
                        
                        for (int i = 0; i < arr3.toArray().length; i++)
                        {
                            Object obj2 = arr3.get(i);

                            if (!lista_anuncios_anunciante.contains(obj2.toString()))
                                lista_anuncios_anunciante.add(obj2.toString());
                        }
                        
                        if(lista_anuncios_anunciante.isEmpty())
                            System.out.println("Não há anúncios ativos do anunciante "+ message+ " neste momento.");
                        else{
                            System.out.println("Lista de anúncios de anúncios do anunciante " + message + ":");
                            System.out.println(lista_anuncios_anunciante);
                        }

                        System.out.println("#################################################################");
                        break;
                    case "od":
                        System.out.print("Id do anúncio -> ");
                        num = Integer.parseInt(input.readLine());
                       
                        url = "http://www.localhost:8090/anuncio/getAnuncio?" + "aid=" + num;
                          
                        str = client.sendGet(url);
                       
                        JSONArray arr4 = JsonPath.read(str, "$.[?(@['estado'] == 'inativo' " + "&& @['aid'] && @['localizacao'] && @['preco'] "
                                + "&& @['data'] && @['genero'] && @['anunciante'] && @['tipo_alojamento'] && @['tipo_anuncio'] && @['estado'])]"
                                + ".['aid', 'localizacao', 'preco', 'data', 'genero', 'anunciante', 'tipo_alojamento', 'tipo_anuncio', 'estado']");
                        
                        Object obj2 = arr4.get(0);
                        
                        if(obj2.toString().equals("[]"))
                            System.out.println("Anúncio não existente");
                        else
                            System.out.println(obj2.toString());
                          
                        System.out.println("#################################################################");
                        break;
                    case "em":
                        
                        String num_men;
                        System.out.print("Id do anúncio -> ");
                        num_men =input.readLine();
                        System.out.print("Mensagem -> ");
                        message = input.readLine();
                        System.out.print("Remetente -> ");
                        remetente=input.readLine();
                        
                        url ="http://www.localhost:8090/anuncio/getAnuncio?" + "aid=" + num_men;
                        str = client.sendGet(url);
                        
                        System.out.println(str);
                        JSONArray exists = JsonPath.read(str, "$.[?(@['aid'])]['aid']");
                        
                        Object obj5 = exists.get(0);
                        
                        if(obj5.toString().equals("[]"))
                            System.out.println("Erro, não foi possivel enviar uma mensagem para o anúncio selecionado, verifique os valores que "
                                    + "foram introduzidos nos campos de preenchimento.");
                        else{
                            url = "http://www.localhost:8090/message/addMessage?" + "message=" + message + "&emissor=" + remetente + "&aid=" + num_men;
                            client.sendPost(url);
                            System.out.println(client.sendPost(url));
                            System.out.println("Mensagem enviada para o anúncio " + num_men + ".");
                        }

                        System.out.println("#################################################################");
                        break;
                    case "cm":
                        System.out.print("Id do anúncio (ativo)-> ");
                        num = Integer.parseInt(input.readLine());
                        
                        ArrayList<String> lista_mensagens = new ArrayList<>();
                        
                        url = "http://www.localhost:8090/message/getAllMessagesByAid?" + "aid=" + 2;

                        str = client.sendGet(url);

                        JSONArray get_m = JsonPath.read(str, "$.[?(@['message'])]['message']");
                        
                        
                        for (int i = 0; i < get_m.toArray().length; i++)
                        {
                            Object obj6 = get_m.get(i);
                           
                            if (!lista_mensagens.contains(obj6.toString()))
                                lista_mensagens.add(obj6.toString());
                        }
                        
                        if(lista_mensagens.isEmpty())
                            System.out.println("Não há mensagens para este anúncio neste momento.");
                        else{
                            System.out.println("Lista de mensagens:");
                            System.out.println(lista_mensagens);
                        }
      
                        System.out.println("#################################################################");
                        break;
                    case "s":
                        System.out.println("Sessão encerrada!");
                        System.exit(0);
                    default:
                        System.out.println("Comando inválido, tente novamente outro comando");
                        System.out.println("");
                        showMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}