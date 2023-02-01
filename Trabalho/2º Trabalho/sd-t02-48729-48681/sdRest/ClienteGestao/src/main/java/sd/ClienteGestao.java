package sd;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPut;

public class ClienteGestao {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void showMenu() {
        System.out.println(" _____________		        BEM VINDO ADMIN	                   _____________");
        System.out.println("|	Insere o comando que quer executar:                                     |");
        System.out.println("|	la - Listar anúncios por estado	                                        |");
        System.out.println("|	od - Obter detalhes de um anúncio                                       |");
        System.out.println("|	aa - Aprovar um anúncio                                                 |");
        System.out.println("|	ae - Alterar o estado de um anúncio                                     |");
        System.out.println("|	s - Sair                                                                |");
        System.out.println("|_______________________________________________________________________________|");
        System.out.println("");
    }

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

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            s = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public void sendPut (String url)
    {
        HttpPut put = new HttpPut(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(put)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {
            showMenu();

            String command_choice;
            int num;
            String url = "";
            ClienteGestao client = new ClienteGestao();
            String str = "";
            Object obj = new Object();

            while (true) {
                System.out.print("-> ");
                command_choice = input.readLine();
                switch (command_choice) {
                    case "la":
                        String url1,
                         url2;
                        String str1 = "";
                        String str2 = "";
                        ArrayList<String> lista_anuncios_ativos = new ArrayList<>();
                        url1 = "http://www.localhost:8090/anuncio/getAnunciosByEstado?" + "estado=ativo";
                        str1 = client.sendGet(url1);
                        JSONArray arr1 = JsonPath.read(str1, "$.[?(@['estado'] == 'ativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])].['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");

                        for (int i = 0; i < arr1.toArray().length; i++) {
                            obj = arr1.get(i);

                            if (!lista_anuncios_ativos.contains(obj.toString())) {
                                lista_anuncios_ativos.add(obj.toString());
                            }
                        }

                        if (lista_anuncios_ativos.isEmpty()) {
                            System.out.println("Não há anúncios ativos neste momento.");
                        } else {
                            System.out.println("Lista de anúncios ativos:");
                            System.out.println(lista_anuncios_ativos);
                        }

                        ArrayList<String> lista_anuncios_inativos = new ArrayList<>();
                        url2 = "http://www.localhost:8090/anuncio/getAnunciosByEstado?" + "estado=inativo";
                        str2 = client.sendGet(url2);
                        JSONArray arr2 = JsonPath.read(str2, "$.[?(@['estado'] == 'inativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])].['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");

                        for (int i = 0; i < arr2.toArray().length; i++) {
                            obj = arr2.get(i);

                            if (!lista_anuncios_inativos.contains(obj.toString())) {
                                lista_anuncios_inativos.add(obj.toString());
                            }
                        }

                        if (lista_anuncios_inativos.isEmpty()) {
                            System.out.println("Não há anúncios inativos neste momento.");
                        } else {
                            System.out.println("Lista de anúncios inativos:");
                            System.out.println(lista_anuncios_inativos);
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
                     case "aa":
                        System.out.print("Id do anúncio -> ");
                        num = Integer.parseInt(input.readLine());
                        
                        url = "http://www.localhost:8090/anuncio/getAnuncio?" + "aid=" + num;
                        
                        str = client.sendGet(url);
                        
                        JSONArray arr8 = JsonPath.read(str, "$.[?(@['estado'] == 'inativo' "
                                + "&& @['aid'] && @['localizacao'] && @['preco'] && @['genero'] && @['tipo_alojamento'])]"
                                + ".['aid', 'localizacao', 'preco', 'genero', 'tipo_alojamento']");
                        
                        
                        if(arr8.isEmpty())
                            System.out.println("Anúncio já está aprovado.");
                        else{
                            url = "http://www.localhost:8090/anuncio/changestate?" + "aid=" + num;
                            client.sendPut(url);
                            System.out.println("Anúncio foi aprovado.");
                            
                        }

                        System.out.println("#################################################################");
                        break;
                     case "ae":
                        System.out.print("Id do anúncio -> ");
                        num = Integer.parseInt(input.readLine());
                        
                        url = "http://www.localhost:8090/anuncio/changestate?" + "aid=" + num;
                        client.sendPut(url);
                        
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
