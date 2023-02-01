package sd.rmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ClienteGeral {

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

    public static void main(String[] args) {
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String regHost = "localhost";
            String regPort = "9000";  // porto do binder

            if (args.length != 2) {
                System.out.println("Utilizar o seguinte comando: java -classpath build/classes:resources/postgresql.jar sd.rmi.ClienteGeral registryHost registryPort");
                System.exit(1);
            }
            regHost = args[0];
            regPort = args[1];

            Anuncio anuncio
                    = (Anuncio) java.rmi.Naming.lookup("rmi://"
                            + regHost + ":" + regPort + "/Anuncio");

            showMenu();

            String command_choice, message, remetente;
            int num;
            while (true) {
                System.out.print("-> ");
                command_choice = input.readLine();

                switch (command_choice) {
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
                        
                        anuncio.regist_anuncio(localizacao, preco, genero, anunciante, tipo_alojamento, tipo_anuncio);
                        System.out.println("Anúncio inserido na base de dados com sucesso!");
                        System.out.println("#################################################################");
                        break;
                    case "lo":
                        LinkedList<Anuncio_Entity> list_o = anuncio.listarAnuncios_oferta();
                        if(list_o.isEmpty())
                            System.out.println("Não há anúncios de oferta ativos neste momento.");
                        else
                            System.out.println("Lista de anúncios de oferta ativos:");
                        for (int i = 0; i < list_o.size(); i++) {
                            System.out.println(anuncio.print_anuncio(list_o.get(i).getaid(), list_o.get(i).getlocalizacao(),
                                    list_o.get(i).getpreco(), list_o.get(i).getdata(), list_o.get(i).getgenero(), list_o.get(i).getanunciante(),
                                    list_o.get(i).gettipo_alojamento(), list_o.get(i).gettipo_anuncio(), list_o.get(i).getestado()));
                        }
                        System.out.println("#################################################################");
                        break;
                    case "lp":
                        LinkedList<Anuncio_Entity> list_a = anuncio.listarAnuncios_procura();
                        if(list_a.isEmpty())
                            System.out.println("Não há anúncios de procura ativos neste momento.");
                        else
                            System.out.println("Lista de anúncios de procura ativos:");
                        for (int i = 0; i < list_a.size(); i++) {
                            anuncio.print_anuncio(list_a.get(i).getaid(), list_a.get(i).getlocalizacao(),
                                    list_a.get(i).getpreco(), list_a.get(i).getdata(), list_a.get(i).getgenero(), list_a.get(i).getanunciante(),
                                    list_a.get(i).gettipo_alojamento(), list_a.get(i).gettipo_anuncio(), list_a.get(i).getestado());
                        }
                        System.out.println("#################################################################");
                        break;
                    case "la":
                        System.out.print("Nome do anunciante -> ");
                        message = input.readLine();
                        LinkedList<Anuncio_Entity> list = anuncio.listarAnuncios(message);
                        if(list.isEmpty())
                            System.out.println("Não há anúncios ativos do anunciante "+ message+ " neste momento.");
                        else
                            System.out.println("Lista de anúncios do anunciante "+message+":");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(anuncio.print_anuncio(list.get(i).getaid(), list.get(i).getlocalizacao(),
                                    list.get(i).getpreco(), list.get(i).getdata(), list.get(i).getgenero(), list.get(i).getanunciante(),
                                    list.get(i).gettipo_alojamento(), list.get(i).gettipo_anuncio(), list.get(i).getestado()));
                        }
                        System.out.println("#################################################################");
                        break;
                    case "od":
                        System.out.print("Id do anúncio -> ");
                        num = Integer.parseInt(input.readLine());
                        String detalhes = anuncio.get_details(num);
                        if (detalhes.length()==0) {
                            System.out.println("Anúncio não existente");
                        }
                        else
                            System.out.println(detalhes);
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
                        anuncio.send_message(num_men, message, remetente);
                        System.out.println("#################################################################");
                        break;
                    case "cm":
                        System.out.print("Id do anúncio (ativo)-> ");
                        num = Integer.parseInt(input.readLine());
                        LinkedList<String> lista_m = anuncio.get_messages(num);
                        if(lista_m.isEmpty())
                            System.out.println("Não existe nenhuma mensagem para este anúncio.");
                        else
                            System.out.println("Mensagens recebidas:");
                        for (int i = 0; i < lista_m.size(); i++) {
                            System.out.println(lista_m.get(i));
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
